package db.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

import db.DBConnection;
import entity.Item;
import external.GitHub.GitHubClient;
import external.GitHub.SearchQuery;
import org.bson.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MongoDBConnection extends DBConnection {

    private MongoClient mongoClient;
    private MongoDatabase db;

    public MongoDBConnection() {
        // Connects to local mongodb server.
        mongoClient = MongoClients.create();
        db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
    }


    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Override
    public void setFavoriteItems(String userId, List<String> itemIds) {
        if (db == null) {
            return;
        }
        db.getCollection("user").updateOne(eq("user_id", userId),
                new Document("$push", new Document("favorite", new Document("$each", itemIds))));
    }

    @Override
    public void unsetFavoriteItems(String userId, List<String> itemIds) {
        if (db == null) {
            return;
        }
        db.getCollection("users").updateOne(eq("user_id", userId),
                new Document("$pullAll", new Document("favorite", itemIds)));
    }

    @Override
    public Set<String> getFavoriteItemIds(String userId) {
        if (db == null) {
            return new HashSet<>();
        }

        Set<String> favoriteItems = new HashSet<>();
        FindIterable<Document> iterable = db.getCollection("users").find(eq("user_id", userId));

        if (iterable.first() != null && iterable.first().containsKey("favorite")) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) iterable.first().get("favorite");
            favoriteItems.addAll(list);
        }

        return favoriteItems;
    }

    @Override
    public Set<Item> getFavoriteItems(String userId) {
        if (db == null) {
            return new HashSet<>();
        }
        Set<Item> favoriteItems = new HashSet<>();

        Set<String> itemIds = getFavoriteItemIds(userId);
        for (String itemId : itemIds) {
            FindIterable<Document> iterable = db.getCollection("items").find(eq("item_id", itemId));
            if (iterable.first() != null) {
                Document doc = iterable.first();

                Item.ItemBuilder builder = new Item.ItemBuilder()
                        .setAddress(doc.getString("address"))
                        .setName(doc.getString("name"))
                        .setImageUrl(doc.getString("image_url"))
                        .setUrl(doc.getString("url"))
                        .setItemId(doc.getString("item_id"))
                        .setKeywords(getKeywords(itemId));

                favoriteItems.add(builder.build());
            }

        }

        return favoriteItems;
    }

    @Override
    public Set<String> getKeywords(String itemId) {
        if (db == null) {
            return new HashSet<>();
        }

        Set<String> keywords = new HashSet<>();
        FindIterable<Document> iterable = db.getCollection("items").find(eq("item_id", itemId));
        if (iterable.first() != null && iterable.first().containsKey("keywords")) {
            @SuppressWarnings("unchecked")
                    List<String> list = (List<String>) iterable.first().get("keywords");
            keywords.addAll(list);
        }
        return keywords;
    }

    @Override
    public List<Item> searchItems(SearchQuery query) {
        GitHubClient client = new GitHubClient();
        List<Item> items = client.search(query);

        for (Item item : items) {
            saveItem(item);
        }

        return items;
    }

    @Override
    public void saveItem(Item item) {
        if (db != null) {
            FindIterable<Document> iterable = db.getCollection("items").find(eq("item_id", item.getItemId()));
            Document document = new Document().append("item_id", item.getItemId())
                    .append("address", item.getAddress())
                    .append("name", item.getName())
                    .append("image_url", item.getImageUrl())
                    .append("url", item.getUrl())
                    .append("keywords", item.getKeywords());

            if (iterable.first() == null) {
                db.getCollection("items").insertOne(document);
            } else {
                db.getCollection("items").replaceOne(eq("item_id", item.getItemId()), document);
            }
        }
    }

    @Override
    public String getFullname(String userId) {
        FindIterable<Document> iterable =
                db.getCollection("users").find(new Document("user_id", userId));
        Document document = iterable.first();
        String firstName = document.getString("first_name");
        String lastName = document.getString("last_name");
        return firstName + " " + lastName;
    }

    @Override
    public boolean verifyLogin(String userId, String password) {
        FindIterable<Document> iterable =
                db.getCollection("users").find(new Document("user_id", userId));
        return iterable.first() != null && iterable.first().getString("password").equals(password);
    }

    @Override
    public boolean registerUser(String userId, String password, String firstname, String lastname) {
        FindIterable<Document> iterable = db.getCollection("users").find(eq("user_id", userId));

        if (iterable.first() == null) {
            Document document = new Document().append("user_id", userId).append("password", password)
                    .append("first_name", firstname).append("last_name", lastname);
            db.getCollection("users").insertOne(document);
            return true;
        }
        return false;
    }
}

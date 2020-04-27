package db.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class MongoDBTableCreation {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);

        db.getCollection("users").drop();
        db.getCollection("items").drop();

        IndexOptions indexOptions = new IndexOptions().unique(true);
        db.getCollection("users").createIndex(new Document("user_id", 1), indexOptions);
        db.getCollection("items").createIndex(new Document("item_id", 1), indexOptions);

        db.getCollection("users").insertOne(
                new Document().append("user_id", "1111").append("password", "3229c1097c00d497a0fd282d586be050")
                        .append("first_name", "John").append("last_name", "Smith"));

        mongoClient.close();
        System.out.println("Import is done successfully.");
    }
}

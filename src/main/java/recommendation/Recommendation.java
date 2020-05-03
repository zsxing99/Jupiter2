package recommendation;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.github.GitHubClient;
import external.github.SearchQuery;

import java.util.*;

public class Recommendation {

    public List<Item> recommendItems(String userId, String lat, String lon) {
        List<Item> items = new ArrayList<>();
        DBConnection connection = DBConnectionFactory.getConnection();
        Set<Item> favoriteItems = connection.getFavoriteItems(userId);

        Map<String, Integer> allKeywords = new HashMap<>();
        for (Item item : favoriteItems) {
            Set<String> keywords = connection.getKeywords(item.getItemId());
            for (String keyword : keywords) {
                allKeywords.put(keyword, allKeywords.getOrDefault(keyword, 0) + 1);
            }
        }
        connection.close();

        List<Map.Entry<String, Integer>> topKeywords = new ArrayList<>(allKeywords.entrySet());
        topKeywords.sort((Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) -> {
            return Integer.compare(e2.getValue(), e1.getValue());
        });

        if (topKeywords.size() > 3) {
            topKeywords = topKeywords.subList(0, 3);
        }

        Set<String> visitedItemIds = new HashSet<>();
        GitHubClient client = new GitHubClient();

        for (Map.Entry<String, Integer> keyword : topKeywords) {
            SearchQuery query = new SearchQuery();
            query.addParam(new SearchQuery.SearchQueryParam(
                    "keyword", keyword.getKey()
            ));
            query.addParam(new SearchQuery.SearchQueryParam("lat", lat));
            query.addParam(new SearchQuery.SearchQueryParam("long", lon));

            List<Item> result = client.search(query);

            for (Item item : result) {
                if (!favoriteItems.contains(item) && !visitedItemIds.contains(item.getItemId())) {
                    items.add(item);
                    visitedItemIds.add(item.getItemId());
                }
            }
        }
        return items;

    }
}

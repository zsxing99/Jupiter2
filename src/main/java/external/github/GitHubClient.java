package external.github;

import entity.Item;
import external.MonkeyLearnClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GitHubClient {

    public List<Item> search(SearchQuery query) {
        String url = query.toUrl();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(url));

            if (response.getStatusLine().getStatusCode() != 200) {
                return new ArrayList<>();
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return new ArrayList<>();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder builder = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return getItemList(new JSONArray(builder.toString()));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<Item> getItemList(JSONArray array) {
        List<Item> itemList = new ArrayList<>();
        List<String> descriptionList = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            // We need to extract keywords from description since GitHub API
            // doesn't return keywords.
            String description = getStringFieldOrEmpty(array.getJSONObject(i), "description");
            if (description.equals("") || description.equals("\n")) {
                descriptionList.add(getStringFieldOrEmpty(array.getJSONObject(i), "title"));
            } else {
                descriptionList.add(description);
            }
        }

        // We need to get keywords from multiple text in one request since
        // MonkeyLearnAPI has limitations on request per minute.
        List<List<String>> keywords = MonkeyLearnClient
                .extractKeywords(descriptionList.toArray(new String[descriptionList.size()]));
        for (int i = 0; i < array.length(); ++i) {
            JSONObject object = array.getJSONObject(i);
            Set<String> keyword = new HashSet<>(keywords.get(i));
            keyword.remove("li");

            Item item = new Item.ItemBuilder().setItemId(getStringFieldOrEmpty(object, "id"))
                    .setName(getStringFieldOrEmpty(object, "title"))
                    .setAddress(getStringFieldOrEmpty(object, "location"))
                    .setUrl(getStringFieldOrEmpty(object, "url"))
                    .setImageUrl(getStringFieldOrEmpty(object, "company_logo"))
                    .setKeywords(keyword)
                    .build();

            itemList.add(item);
        }

        return itemList;
    }

    private String getStringFieldOrEmpty(JSONObject obj, String field) {
        return obj.isNull(field) ? "" : obj.getString(field);
    }

}

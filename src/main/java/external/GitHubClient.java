package external;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GitHubClient {

    private static final String URL_TEMPLATE_GEO = "https://jobs.github.com/" +
            "positions.json?description=%s&lat=%s&long=%s&full_time=%s";
    private static final String URL_TEMPLATE_LOC = "https://jobs.github.com/" +
            "positions.json?description=%s&location=%s&full_time=%s";
    private static final String DEFAULT_DESCRIPTION = "developer";
    private static final String DEFAULT_TYPE = "true";

    public JSONArray searchGeo(double lat, double lon, String keyword, String isFullTime) {
        if (keyword == null) {
            keyword = DEFAULT_DESCRIPTION;
        }
        if (isFullTime == null) {
            isFullTime = DEFAULT_TYPE;
        }
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            isFullTime = URLEncoder.encode(isFullTime, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(URL_TEMPLATE_GEO, keyword, lat, lon, isFullTime);

        return searchUrl(url);
    }

    public JSONArray searchLocation(String location, String keyword, String isFullTime) {
        if (keyword == null) {
            keyword = DEFAULT_DESCRIPTION;
        }
        if (isFullTime == null) {
            isFullTime = DEFAULT_TYPE;
        }
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            isFullTime = URLEncoder.encode(isFullTime, "UTF-8");
            location = URLEncoder.encode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(URL_TEMPLATE_LOC, keyword, location, isFullTime);

        return searchUrl(url);
    }

    private JSONArray searchUrl(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(url));

            if (response.getStatusLine().getStatusCode() != 200) {
                return new JSONArray();
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return new JSONArray();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder builder = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return new JSONArray(builder.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }
}

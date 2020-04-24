package rpc;

import entity.Item;
import external.GitHubClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchItem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");

        if (method == null) {
            // method missing: bad request error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else if (method.equals("geo")) {
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lon = Double.parseDouble(request.getParameter("lon"));
            String keyword = request.getParameter("keyword");
            String isFullTime = request.getParameter("full_time");

            GitHubClient client = new GitHubClient();
            List<Item> items = client.searchGeo(lat, lon, keyword, isFullTime);
            JSONArray array = new JSONArray();
            for (Item item : items) {
                array.put(item.toJSONObject());
            }
            RpcHelper.writeJsonArray(response, array);
        } else if (method.equals("loc")){
            String location = request.getParameter("location");
            String keyword = request.getParameter("keyword");
            String isFullTime = request.getParameter("full_time");

            GitHubClient client = new GitHubClient();
            List<Item> items = client.searchLocation(location, keyword, isFullTime);
            JSONArray array = new JSONArray();
            for (Item item : items) {
                array.put(item.toJSONObject());
            }
            RpcHelper.writeJsonArray(response, array);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

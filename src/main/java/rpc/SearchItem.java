package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.GitHub.GitHubClient;
import external.GitHub.SearchQuery;
import org.json.JSONArray;

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
            String lat = request.getParameter("lat");
            String lon = request.getParameter("lon");
            String keyword = request.getParameter("keyword");
            String isFullTime = request.getParameter("full_time");

            SearchQuery query = new SearchQuery();
            query.addParam(new SearchQuery.SearchQueryParam("lat", lat));
            query.addParam(new SearchQuery.SearchQueryParam("long", lon));
            query.addParam(new SearchQuery.SearchQueryParam("keyword", keyword));
            query.addParam(new SearchQuery.SearchQueryParam("full_time", isFullTime));

            DBConnection connection = DBConnectionFactory.getConnection();
            List<Item> items = connection.searchItems(query);
            JSONArray array = new JSONArray();
            for (Item item : items) {
                array.put(item.toJSONObject());
            }
            RpcHelper.writeJsonArray(response, array);
        } else if (method.equals("loc")){
            String location = request.getParameter("location");
            String keyword = request.getParameter("keyword");
            String isFullTime = request.getParameter("full_time");

            SearchQuery query = new SearchQuery();
            query.addParam(new SearchQuery.SearchQueryParam("location", location));
            query.addParam(new SearchQuery.SearchQueryParam("keyword", keyword));
            query.addParam(new SearchQuery.SearchQueryParam("full_time", isFullTime));

            DBConnection connection = DBConnectionFactory.getConnection();
            List<Item> items = connection.searchItems(query);
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

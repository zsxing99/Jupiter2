package rpc;

import external.GitHubClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/search")
public class SearchItem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");

        if (method == null) {
            // method error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else if (method.equals("geo")) {
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lon = Double.parseDouble(request.getParameter("lon"));
            String keyword = request.getParameter("keyword");
            String isFullTime = request.getParameter("full_time");

            GitHubClient client = new GitHubClient();
            RpcHelper.writeJsonArray(response, client.searchGeo(lat, lon, keyword, isFullTime));
        } else if (method.equals("loc")){
            String location = request.getParameter("location");
            String keyword = request.getParameter("keyword");
            String isFullTime = request.getParameter("full_time");

            GitHubClient client = new GitHubClient();
            RpcHelper.writeJsonArray(response, client.searchLocation(location, keyword, isFullTime));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

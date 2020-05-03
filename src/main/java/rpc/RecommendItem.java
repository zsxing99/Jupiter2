package rpc;

import entity.Item;
import external.github.SearchQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import recommendation.Recommendation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/recommend")
public class RecommendItem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            return;
        }

        SearchQuery query = new SearchQuery();
        String userId = session.getAttribute("user_id").toString();
        query.addParam(new SearchQuery.SearchQueryParam("lat", request.getParameter("lat")));
        query.addParam(new SearchQuery.SearchQueryParam("long", request.getParameter("lon")));

        Recommendation recommendation = new Recommendation();
        List<Item> items = recommendation.recommendItems(userId, query);
        JSONArray array = new JSONArray();
        for (Item item : items) {
            array.put(item.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }
}

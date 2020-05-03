package rpc;

import entity.Item;
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
        String userId = session.getAttribute("user_id").toString();
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");

        Recommendation recommendation = new Recommendation();
        System.out.println(lat);
        List<Item> items = recommendation.recommendItems(userId, lat, lon);
        JSONArray array = new JSONArray();
        for (Item item : items) {
            array.put(item.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }
}

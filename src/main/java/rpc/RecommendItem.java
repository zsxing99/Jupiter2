package rpc;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/recommend")
public class RecommendItem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        array.put(new JSONObject().put("name", "abcd").put("address", "San Francisco").put("time", "01/01/2017"));
        array.put(new JSONObject().put("name", "1234").put("address", "San Jose").put("time", "01/01/2017"));
        RpcHelper.writeJsonArray(response, array);
    }
}

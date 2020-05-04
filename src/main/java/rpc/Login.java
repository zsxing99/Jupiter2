package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            HttpSession session = request.getSession(false);
            JSONObject obj = new JSONObject();
            if (session != null) {
                String userId = session.getAttribute("user_id").toString();
                obj.put("status", "OK").put("user_id", userId).put("name", connection.getFullname(userId));
            } else {
                obj.put("status", "Invalid Session");
                response.setStatus(401);
            }
            RpcHelper.writeJsonObject(response, obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            JSONObject input = RpcHelper.readJSONObject(request);
            String userId = input.getString("user_id");
            String password = input.getString("password");

            JSONObject obj = new JSONObject();
            if (connection.verifyLogin(userId, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", userId);
                session.setMaxInactiveInterval(600);
                obj.put("status", "OK").put("user_id", userId)
                        .put("name", connection.getFullname(userId))
                        .put("currentAuthority", "user");
            } else {
                obj.put("status", "User Doesn't Exist");
            }
            RpcHelper.writeJsonObject(response, obj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}

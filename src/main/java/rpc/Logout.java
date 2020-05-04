package rpc;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/logout")
public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        JSONObject obj = new JSONObject();
        obj.put("currentAuthority", "guest");
        if (session != null) {
            obj.put("status", "OK");
            session.invalidate();
        } else {
            obj.put("status", "Session doesn't Exist");
        }
        RpcHelper.writeJsonObject(response, obj);
    }
}

package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/register")
public class Register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            JSONObject input = RpcHelper.readJSONObject(request);
            String userId = input.getString("user_id");
            String password = input.getString("password");
            String firstName = input.getString("first_name");
            String lastName = input.getString("last_name");

            JSONObject object = new JSONObject();
            response.setContentType("application/json");
            if (connection.registerUser(userId, password, firstName, lastName)) {
                object.put("status", "OK");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                object.put("status", "User Already Exist");
                response.setStatus(HttpServletResponse.SC_OK);
            }
            RpcHelper.writeJsonObject(response, object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

}

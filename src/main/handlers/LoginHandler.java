package handlers;

import daos.DataAccess;
import dataAccess.DataAccessException;
import services.LoginService;
import spark.Request;
import com.google.gson.Gson;

/**
 * Handles the http conversion for login requests and connects to the corresponding service
 */
public class LoginHandler {

    /**
     * Convert http to java object server can work with
     * Call the 'login' service
     * @param request - the http request
     */
    public static String loginRequest(Request request, DataAccess dataManager) throws DataAccessException {
        Gson gson = new Gson();
        models.Request loginRequest = gson.fromJson(String.valueOf(request.body()), models.Request.class);
        LoginService service = new LoginService();
        models.Response response = service.login(loginRequest, dataManager);

        return gson.toJson(response);
    }
}

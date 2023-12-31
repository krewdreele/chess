package handlers;

import com.google.gson.Gson;
import daos.DataAccess;
import dataAccess.DataAccessException;
import services.LogoutService;
import spark.Request;

/**
 *  Handles the http conversion for logging out and connects to the corresponding service
 */
public class LogoutHandler {

    /**
     * Convert http request into java obj that service can use
     * Calls the 'logout' service
     * @param request - the http request
     */
    public static String logoutRequest(Request request, DataAccess dataManager) throws DataAccessException {
        Gson gson = new Gson();
        var logoutRequest = new models.Request();
        var authToken = request.headers("authorization");
        logoutRequest.setAuthToken(authToken);
        LogoutService service = new LogoutService();
        models.Response response = service.logout(logoutRequest, dataManager);
        return gson.toJson(response);
    }
}

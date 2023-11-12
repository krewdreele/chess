package handlers;

import com.google.gson.Gson;
import daos.DataAccess;
import dataAccess.DataAccessException;
import services.JoinGameService;
import spark.Request;

/**
 * Handles the http conversion for joining a game and connects to the corresponding service
 */
public class JoinGameHandler {

    /**
     * Converts the http request into java object
     * Calls the 'join game' service
     *
     * @param request     - the http request
     * @param dataManager gets the service access to the database
     */
    public static String JoinGameRequest(Request request, DataAccess dataManager) throws DataAccessException {
        Gson gson = new Gson();
        var authToken = request.headers("authorization");
        var joinRequest = gson.fromJson(request.body(), models.Request.class);
        joinRequest.setAuthToken(authToken);
        var service = new JoinGameService();
        var response = service.joinGame(joinRequest, dataManager);

        return gson.toJson(response);
    }
}

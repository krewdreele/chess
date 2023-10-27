package handlers;

import com.google.gson.Gson;
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
     * @param request - the http request
     */
    public static String JoinGameRequest(Request request) throws DataAccessException {
        Gson gson = new Gson();
        var authToken = request.headers("authorization");
        var joinRequest = gson.fromJson(request.body(), models.Request.class);
        joinRequest.setAuthToken(authToken);
        var service = new JoinGameService();
        var response = service.joinGame(joinRequest);

        return gson.toJson(response);
    }
}

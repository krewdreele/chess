package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.Response;
import services.CreateGameService;
import spark.Request;


/**
 * Handles the http conversion for creating a new game and connects to the corresponding service
 */
public class CreateGameHandler {

    /**
     * Converts the http request into an authentication object that can be used by the server
     * Calls the 'create game' service
     * @param request - the http request
     */
    public static String createGameRequest(Request request) throws DataAccessException {
        Gson gson = new Gson();
        var authToken = request.headers("authorization");
        var gameRequest = gson.fromJson(request.body(), models.Request.class);
        gameRequest.setAuthToken(authToken);
        var service = new CreateGameService();
        Response r = service.createGame(gameRequest);

        return gson.toJson(r);
    }
}

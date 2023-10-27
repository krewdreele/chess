package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.ListGamesService;
import spark.Request;

/**
 * Handles http conversion for getting a list of all games in the database and connects to the corresponding service
 */
public class ListGamesHandler {

    /**
     * Converts the request into the authentication object needed by the server
     * Calls the 'list games' service
     * @param request - the http request
     */
    public static String listGamesRequest(Request request) throws DataAccessException {
        Gson gson = new Gson();
        var listGamesRequest = new models.Request();
        var authToken = request.headers("authorization");
        listGamesRequest.setAuthToken(authToken);
        var service = new ListGamesService();
        var response = service.listGames(listGamesRequest);
        return gson.toJson(response);
    }
}

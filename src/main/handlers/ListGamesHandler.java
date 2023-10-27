package handlers;

import com.google.gson.Gson;
import models.AuthToken;
import models.GameData;
import services.ListGamesService;
import services.LogoutService;
import spark.Request;

import java.net.http.HttpRequest;
import java.util.List;

/**
 * Handles http conversion for getting a list of all games in the database and connects to the corresponding service
 */
public class ListGamesHandler {

    /**
     * Converts the request into the authentication object needed by the server
     * Calls the 'list games' service
     * @param request - the http request
     */
    public static String listGamesRequest(Request request){
        Gson gson = new Gson();
        var listGamesRequest = new models.Request();
        var authToken = request.headers("authorization");
        listGamesRequest.setAuthToken(authToken);
        var service = new ListGamesService();
        var response = service.listGames(listGamesRequest);
        return gson.toJson(response);
    }
}

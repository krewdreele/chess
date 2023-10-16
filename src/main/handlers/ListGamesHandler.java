package handlers;

import models.AuthToken;
import models.GameData;

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
    public void listGamesRequest(HttpRequest request){}
}

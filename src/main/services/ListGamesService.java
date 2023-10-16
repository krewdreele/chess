package services;

import models.Response;
import models.Request;

import java.util.List;

/**
 * Service for listing all the games in the database
 */
public class ListGamesService {

    /**
     * Authenticate the token
     * Find and return a list of all games in the database
     * First item in list will always be the response message
     * @param r - the user's authentication token in request obj
     * @return a list of all games in the database
     */
    public Response listGames(Request r){return null;}
}

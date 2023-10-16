package services;

import models.Response;
import models.Request;

/**
 * The service that creates a new game
 */
public class CreateGameService {

    /**
     * Validates the auth token
     * Creates a new game -> stores in database
     * Updates the game data (with success or error message)
     * @param r - request object with the auth token from the user
     * @return the new game data and message in response obj
     */
    public Response createGame(Request r){return null;}
}

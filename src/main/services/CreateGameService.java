package services;

import models.AuthToken;
import models.GameData;

/**
 * The service that creates a new game
 */
public class CreateGameService {

    /**
     * Validates the auth token
     * Creates a new game -> stores in database
     * Updates the game data (with success or error message)
     * @param token - the auth token from the user
     * @return the new game data
     */
    public GameData createGame(AuthToken token){return null;}
}

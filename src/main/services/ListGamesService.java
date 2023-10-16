package services;

import models.AuthToken;
import models.GameData;

import java.util.List;

/**
 * Service for listing all the games in the database
 */
public class ListGamesService {

    /**
     * Authenticate the token
     * Find and return a list of all games in the database
     * First item in list will always be the response message
     * @param token - the user's authentication token
     * @return a list of all games in the database
     */
    public List<Object> listGames(AuthToken token){return null;}
}

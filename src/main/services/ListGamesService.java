package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.Response;
import models.Request;

/**
 * Service for listing all the games in the database
 */
public class ListGamesService {

    /**
     * Authenticate the token
     * Find and return a list of all games in the database
     * @param r - the user's authentication token in request obj
     * @return a list of all games in the database
     */
    public Response listGames(Request r, DataAccess dataManager) throws DataAccessException{
        Response response = new Response();
        dataManager.getAuthAccess().find(r.getAuthToken());
        response.setGameList(dataManager.getGameAccess().findAll());

        return response;
    }
}

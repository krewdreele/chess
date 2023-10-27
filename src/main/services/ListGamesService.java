package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.GameData;
import models.Response;
import models.Request;

import java.util.ArrayList;
import java.util.List;

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
    public Response listGames(Request r){
        Response response = new Response();
        try {
            DataAccess.getInstance().getAuthAccess().find(r.getAuthToken());
            response.setGameList(DataAccess.getInstance().getGameAccess().findAll());
        }
        catch (DataAccessException e){
            response.setMessage(e.getMessage());
        }

        return response;
    }
}

package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.Response;
import models.Request;

/**
 * Service for adding a player or observer to a game
 */
public class JoinGameService {

    /**
     * Authenticate / get user from database
     * Add user to the game with corresponding color
     * @param r - the request obj containing the necessary info
     * @return the response obj with message
     */
    public Response joinGame(Request r) throws DataAccessException{
        Response response = new Response();
        var username = DataAccess.getInstance().getAuthAccess().find(r.getAuthToken());
        DataAccess.getInstance().getGameAccess().claimSpot(r.getGameID(), username, r.getPlayerColor());
        return response;
    }
}

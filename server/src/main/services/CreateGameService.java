package services;

import chess.ChessGameImpl;
import daos.DataAccess;
import dataAccess.DataAccessException;
import models.GameData;
import models.Response;
import models.Request;

/**
 * The service that creates a new game
 */
public class CreateGameService {
    /**
     * Validates the auth token
     * Creates a new game -> stores in database
     * Updates the response (with success or error message)
     * @param r - request object with the auth token from the user
     * @return the new game data and message in response obj
     */
    public Response createGame(Request r, DataAccess dataManager) throws DataAccessException{
        Response response = new Response();
        dataManager.getAuthAccess().find(r.getAuthToken());
        GameData game = new GameData(r.getGameName(), new ChessGameImpl());
        dataManager.getGameAccess().insert(game);
        response.setGameID(String.valueOf(game.getGameID()));
        return response;
    }
}

package daos;

import chess.ChessGame;
import dataAccess.DataAccessException;
import models.GameData;

import java.util.List;

/**
 * RAM implementation of the game data access
 */
public class GameDAO implements GameDataAccess{
    /**
     * Inserts a new game into the database
     *
     * @param game - the game to be inserted
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void insert(GameData game) throws DataAccessException {

    }

    /**
     * Returns a game from the database
     *
     * @param gameID - the ID of the game looking for
     * @return the game data object
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public GameData find(int gameID) throws DataAccessException {
        return null;
    }

    /**
     * Finds all the games in the database
     *
     * @return a list of all games
     * @throws DataAccessException - unauthorized, bad request, server error
     */
    @Override
    public List<GameData> findAll() throws DataAccessException {
        return null;
    }

    /**
     * Used to claim a spot on a game
     *
     * @param gameID   - the game being claimed
     * @param username - the username of the player claiming it
     * @param color    - whether they are entering as black or white
     * @throws DataAccessException - spot is taken, unauthorized, bad request, server error
     */
    @Override
    public void claimSpot(int gameID, String username, ChessGame.TeamColor color) throws DataAccessException {

    }

    /**
     * Replaces a game with a new game
     *
     * @param gameID  - the game ID of the game to be replaced
     * @param newGame - the new game
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void updateGame(int gameID, ChessGame newGame) throws DataAccessException {

    }

    /**
     * Removes a game from the database
     *
     * @param gameID - ID of game to be removed
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void remove(int gameID) throws DataAccessException {

    }
}

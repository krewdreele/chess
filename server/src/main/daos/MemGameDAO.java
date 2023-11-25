package daos;

import chess.ChessGame;
import dataAccess.DataAccessException;
import models.GameData;

import java.util.*;

/**
 * RAM implementation of the game data access
 */
public class MemGameDAO implements GameDataAccess{
    HashMap<Integer, GameData> games;

    public MemGameDAO(){
        games = new HashMap<>();
    }
    /**
     * Inserts a new game into the database
     *
     * @param game - the game to be inserted
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void insert(GameData game) throws DataAccessException {
        if(game == null){
            throw new DataAccessException("400: bad request");
        }
        games.put(game.getGameID(), game);
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
     */
    @Override
    public List<GameData> findAll(){
        List<GameData> gameList = new ArrayList<>();
        for (var key : games.keySet()){
            gameList.add(games.get(key));
        }

        return gameList;
    }

    /**
     * Used to claim a spot on a game
     *
     * @param gameID   - the game being claimed
     * @param username - the username of the player claiming it
     * @param color    - whether they are entering as black or white
     * @return
     * @throws DataAccessException - spot is taken, unauthorized, bad request, server error
     */
    @Override
    public GameData claimSpot(int gameID, String username, ChessGame.TeamColor color) throws DataAccessException {
        GameData game = games.get(gameID);
        if(game == null){
            throw new DataAccessException("400: bad request");
        }

        if(color == ChessGame.TeamColor.WHITE){
            if(game.getWhiteUsername() == null) {
                game.setWhiteUsername(username);
            }
            else{
                throw new DataAccessException("403: already taken");
            }
        }
        else if(color == ChessGame.TeamColor.BLACK){
            if(game.getBlackUsername() == null) {
                game.setBlackUsername(username);
            }
            else{
                throw new DataAccessException("403: already taken");
            }
        }
        else{
            game.addObserver(username);
        }
        return game;
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
        GameData game = games.get(gameID);
        if(game == null || newGame == null){
            throw new DataAccessException("400: bad request");
        }
    }

    /**
     * Removes a game from the database
     *
     * @param gameID - ID of game to be removed
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void remove(int gameID) throws DataAccessException {
        GameData game = games.get(gameID);
        if(game == null){
            throw new DataAccessException("400: bad request");
        }

        games.remove(gameID);
    }

    /**
     * clears all games from the database
     */
    @Override
    public void clear() {
        games.clear();
    }
}

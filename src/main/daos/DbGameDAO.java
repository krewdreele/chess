package daos;

import Deserializers.*;
import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import dataAccess.Database;
import models.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbGameDAO implements GameDataAccess{
    private final Database db;
    private final Gson builder;
    public DbGameDAO(Database db){
        this.db = db;
        builder = new GsonBuilder()
                .registerTypeAdapter(ChessGame.class,
                        new ChessGameDS())
                .registerTypeAdapter(ChessBoard.class,
                        new ChessBoardDS())
                .registerTypeAdapter(ChessPiece.class,
                        new ChessPieceInterfaceDS())
                .registerTypeAdapter(ChessPieceImpl.class,
                        new ChessPieceSerializationManager())
                .registerTypeAdapter(ChessPosition.class,
                        new ChessPositionDS()).create();
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

        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameID, gameName, game) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, String.valueOf(game.getGameID()));
            preparedStatement.setString(2, game.getGameName());
            preparedStatement.setString(3, builder.toJson(game.getGame()));

            preparedStatement.executeUpdate();

        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }
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
        var conn = db.getConnection();
        var gameName = "";
        var chessGame = "";
        String blackUsername = null;
        String whiteUsername = null;
        GameData game;
        try(var preparedStatemtent = conn.prepareStatement("SELECT * FROM game WHERE gameID=?")){
            preparedStatemtent.setString(1, String.valueOf(gameID));
            try(var rs = preparedStatemtent.executeQuery()){
                while (rs.next()){
                    gameName = rs.getString("gameName");
                    chessGame = rs.getString("game");
                    whiteUsername = rs.getString("whiteUsername");
                    blackUsername = rs.getString("blackUsername");
                }
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }

        if(gameName.isEmpty()){
            throw new DataAccessException("400: bad request");
        }
        else {
            game = new GameData(gameID, gameName, builder.fromJson(chessGame, ChessGame.class));
            game.setWhiteUsername(whiteUsername);
            game.setBlackUsername(blackUsername);
        }
        return game;
    }

    /**
     * Finds all the games in the database
     *
     * @return a list of all games
     * @throws DataAccessException - unauthorized, bad request, server error
     */
    @Override
    public List<GameData> findAll() throws DataAccessException {
        var conn = db.getConnection();
        var games = new ArrayList<GameData>();
        try(var preparedStatemtent = conn.prepareStatement("SELECT * FROM game")){
            try(var rs = preparedStatemtent.executeQuery()){
                while (rs.next()){
                    var gameID = rs.getString("gameID");
                    var whiteUser = rs.getString("whiteUsername");
                    var blackUser = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    var chessGame = rs.getString("game");

                    var game = new GameData(Integer.parseInt(gameID), gameName, builder.fromJson(chessGame, ChessGame.class));
                    game.setBlackUsername(blackUser);
                    game.setWhiteUsername(whiteUser);
                    games.add(game);
                }
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }

        return games;
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
        GameData game = find(gameID);
        if(color == null){
            game.addObserver(username);
            return;
        }
        var conn = db.getConnection();
        switch (color) {
            case WHITE:
                if(game.getWhiteUsername() != null){
                    throw new DataAccessException("403: already taken");
                }
                try (var preparedStatement = conn.prepareStatement("UPDATE game SET whiteUsername=? WHERE gameID=?")) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, String.valueOf(gameID));
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
                finally {
                    db.returnConnection(conn);
                }
                break;
            case BLACK:
                if(game.getBlackUsername() != null){
                    throw new DataAccessException("403: already taken");
                }
                try (var preparedStatement = conn.prepareStatement("UPDATE game SET blackUsername=? WHERE gameID=?")) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, String.valueOf(gameID));
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
                finally {
                    db.returnConnection(conn);
                }
                break;
        }

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
        find(gameID);
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET game=? WHERE gameID=?")) {
            preparedStatement.setString(1, builder.toJson(newGame));
            preparedStatement.setString(2, String.valueOf(gameID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
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
        find(gameID);
        var conn = db.getConnection();
        try(var preparedStatement = conn.prepareStatement("DELETE FROM game WHERE gameID=?")){
            preparedStatement.setString(1, String.valueOf(gameID));
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }
    }

    /**
     * clears all games from the database
     */
    @Override
    public void clear() throws DataAccessException{
        var conn = db.getConnection();
        try(var preparedStatement = conn.prepareStatement("TRUNCATE game")){
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally{
            db.returnConnection(conn);
        }
    }
}

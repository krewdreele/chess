package passoffTests.serverTests;

import chess.ChessGame;
import chess.ChessGameImpl;
import daos.DataAccess;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DAOTests {

    private static DataAccess db;
    @BeforeAll
    public static void setup() throws DataAccessException {
        db = new DataAccess();
    }
    @AfterEach
    public void clearDB() throws DataAccessException {
        db.getUserAccess().clear();
        db.getGameAccess().clear();
        db.getAuthAccess().clear();
    }
    @Test
    public void writeReadUser() throws DataAccessException{
        UserData newUser = new UserData("bob", "password", "bobrox@hotmail.com");

        assert(db.getUserAccess().find("bob").isEmpty());
        db.getUserAccess().insert(newUser);
        assert (db.getUserAccess().find("bob").equals(newUser.getPassword()));
    }

    @Test
    public void writeReadAuthToken() throws DataAccessException{
        AuthToken token = new AuthToken("1234", "bob");

        assertThrows(DataAccessException.class, () -> db.getAuthAccess().find("bob"));
        db.getAuthAccess().insert(token);
        assert (db.getAuthAccess().find("1234").equals("bob"));
    }

    @Test
    public void writeReadGame() throws DataAccessException{
        GameData game = new GameData("bob's game", new ChessGameImpl());

        assertThrows(DataAccessException.class, () -> db.getGameAccess().find(1234));
        db.getGameAccess().insert(game);
        assert (db.getGameAccess().find(game.getGameID()).equals(game));
    }

    @Test
    public void deleteAuthToken() throws DataAccessException{
        AuthToken token = new AuthToken("1234", "bob");
        db.getAuthAccess().insert(token);
        db.getAuthAccess().delete(token.getAuthToken());
        assertThrows(DataAccessException.class, () -> db.getAuthAccess().find(token.getAuthToken()));
    }

    @Test
    public void deleteAuthTokenFail(){
        assertThrows(DataAccessException.class, () -> db.getAuthAccess().delete("123"));
    }

    @Test
    public void findAllGames() throws DataAccessException {
        assert (db.getGameAccess().findAll().isEmpty());

        ArrayList<GameData> games = new ArrayList<>();
        games.add(new GameData("game 1", new ChessGameImpl()));
        games.add(new GameData("game 2", new ChessGameImpl()));
        games.add(new GameData("game 3", new ChessGameImpl()));

        for(var game : games){
            db.getGameAccess().insert(game);
        }

        List<GameData> dbGames = db.getGameAccess().findAll();

        assertEquals(dbGames, games);
    }

    @Test
    public void claimSpot() throws DataAccessException{
        var game = new GameData("game", new ChessGameImpl());
        db.getGameAccess().insert(game);
        db.getGameAccess().claimSpot(game.getGameID(), "bob", ChessGame.TeamColor.WHITE);
        assert (db.getGameAccess().find(game.getGameID()).getWhiteUsername().equals("bob"));
        assertThrows(DataAccessException.class, () -> db.getGameAccess().claimSpot(game.getGameID(), "chel", ChessGame.TeamColor.WHITE));
    }

    @Test
    public void updateGame() throws DataAccessException{
        ChessGameImpl chessGame = new ChessGameImpl();
        GameData game = new GameData("bob's game", chessGame);
        db.getGameAccess().insert(game);
        chessGame.setTeamTurn(ChessGame.TeamColor.BLACK);
        assertThrows(DataAccessException.class, () -> db.getGameAccess().updateGame(1378932, new ChessGameImpl()));
        db.getGameAccess().updateGame(game.getGameID(), chessGame);
        assert (db.getGameAccess().find(game.getGameID()).getGame().getTeamTurn() == ChessGame.TeamColor.BLACK);
    }

    @Test
    public void removeGame() throws DataAccessException{
        GameData game1 = new GameData("game", new ChessGameImpl());
        db.getGameAccess().insert(game1);
        assertThrows(DataAccessException.class, () -> db.getGameAccess().remove(0));
        db.getGameAccess().remove(game1.getGameID());
        assert (db.getGameAccess().findAll().isEmpty());
    }

    @Test
    public void clearGames() throws DataAccessException{
        GameData game1 = new GameData("game", new ChessGameImpl());
        db.getGameAccess().insert(game1);
        db.getGameAccess().clear();
        assert (db.getGameAccess().findAll().isEmpty());
    }

    @Test
    public void clearTokens() throws DataAccessException{
        AuthToken token = new AuthToken("123", "bob");
        db.getAuthAccess().insert(token);
        db.getAuthAccess().clear();
        assertThrows(DataAccessException.class, () -> db.getAuthAccess().find("bob"));
    }

    @Test
    public void clearUsers() throws DataAccessException{
        UserData user = new UserData("bob", "password", "email");
        db.getUserAccess().insert(user);
        db.getUserAccess().clear();
        assert db.getUserAccess().find("bob").isEmpty();
    }

}

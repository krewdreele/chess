package passoffTests.serverTests;

import chess.ChessGame;
import chess.ChessGameImpl;
import daos.MemAuthDAO;
import daos.MemGameDAO;
import daos.MemUserDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DAOTests {

    @Test
    public void writeReadUser() throws DataAccessException{
        MemUserDAO db = new MemUserDAO();
        UserData newUser = new UserData("bob", "password", "bobrox@hotmail.com");

        assertThrows(DataAccessException.class, () -> db.find("bob"));
        db.insert(newUser);
        assert (db.find("bob") == newUser);
    }

    @Test
    public void writeReadAuthToken() throws DataAccessException{
        MemAuthDAO db = new MemAuthDAO();
        AuthToken token = new AuthToken("1234", "bob");

        assertThrows(DataAccessException.class, () -> db.find("bob"));
        db.insert(token);
        assert (db.find("bob").equals("1234"));
    }

    @Test
    public void writeReadGame() throws DataAccessException{
        MemGameDAO db = new MemGameDAO();
        GameData game = new GameData(1234, "bob's game", new ChessGameImpl());

        assertThrows(DataAccessException.class, () -> db.find(1234));
        db.insert(game);
        assert (db.find(1234) == game);
    }

    @Test
    public void findAllGames() throws DataAccessException {
        MemGameDAO db = new MemGameDAO();
        assert (db.findAll().isEmpty());

        ArrayList<GameData> games = new ArrayList<>();
        games.add(new GameData(123, "game 1", new ChessGameImpl()));
        games.add(new GameData(124, "game 2", new ChessGameImpl()));
        games.add(new GameData(125, "game 3", new ChessGameImpl()));

        for(var game : games){
            db.insert(game);
        }

        List<GameData> dbGames = db.findAll();

        for(int i=0; i<games.size(); i++){
            assert (dbGames.get(i) == games.get(i));
        }
    }

    @Test
    public void claimSpot() throws DataAccessException{
        MemGameDAO db = new MemGameDAO();
        db.insert(new GameData(123, "game", new ChessGameImpl()));
        db.claimSpot(123, "bob", ChessGame.TeamColor.WHITE);
        assert (db.find(123).getWhiteUsername().equals("bob"));
        assertThrows(DataAccessException.class, () -> db.claimSpot(123, "chel", ChessGame.TeamColor.WHITE));
    }

    @Test
    public void updateGame() throws DataAccessException{
        MemGameDAO db = new MemGameDAO();
        GameData game1 = new GameData(123, "game", new ChessGameImpl());
        db.insert(game1);
        GameData game2 = new GameData(123, "game 2", new ChessGameImpl());
        assertThrows(DataAccessException.class, () -> db.updateGame(0, game2));
        db.updateGame(123, game2);
        assert (db.find(123) == game2);
    }

    @Test
    public void removeGame() throws DataAccessException{
        MemGameDAO db = new MemGameDAO();
        GameData game1 = new GameData(123, "game", new ChessGameImpl());
        db.insert(game1);
        assertThrows(DataAccessException.class, () -> db.remove(0));
        db.remove(123);
        assert (db.findAll().isEmpty());
    }

    @Test
    public void clearGames() throws DataAccessException{
        MemGameDAO db = new MemGameDAO();
        GameData game1 = new GameData(123, "game", new ChessGameImpl());
        db.insert(game1);
        db.clear();
        assert (db.findAll().isEmpty());
    }

    @Test
    public void clearTokens() throws DataAccessException{
        MemAuthDAO db = new MemAuthDAO();
        AuthToken token = new AuthToken("123", "bob");
        db.insert(token);
        db.clear();
        assertThrows(DataAccessException.class, () -> db.find("bob"));
    }

    @Test
    public void clearUsers() throws DataAccessException{
        MemUserDAO db = new MemUserDAO();
        UserData user = new UserData("bob", "password", "email");
        db.insert(user);
        db.clear();
        assertThrows(DataAccessException.class, () -> db.find("bob"));
    }
}

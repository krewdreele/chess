package passoffTests.serverTests;

import chess.ChessGame;
import daos.DataAccess;
import dataAccess.DataAccessException;
import models.GameData;
import models.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests {
    private static String token;
    private static int gameID;

    @BeforeEach
    public void setup() throws DataAccessException{
        var service = new RegisterService();
        var req = new Request();
        req.setUsername("jerry");
        req.setPassword("jerry'spassword");
        req.setEmail("jerry@hotmail.com");
        token = service.register(req).getToken();

        var gameService = new CreateGameService();
        var req2 = new Request();
        req2.setAuthToken(token);
        req2.setGameName("jerry's game");
        gameID = Integer.parseInt(gameService.createGame(req2).getGameID());
    }

    @AfterEach
    public void reset(){
        new ClearAppService().clearApplication();
    }
    @Test
    public void registerService() throws DataAccessException{
        var req = new Request();
        req.setUsername("bob");
        req.setPassword("password");
        req.setEmail("bob@gmail.com");

        var service = new RegisterService();
        service.register(req);
        var user = DataAccess.getInstance().getUserAccess().find("bob");
        assert(user != null);
    }
    @Test
    public void registerServiceFail(){
        var req = new Request();
        req.setUsername("bob");
        req.setEmail("bob@gmail.com");

        var service = new RegisterService();
        assertThrows(DataAccessException.class, () -> service.register(req));
    }
    @Test
    public void logoutServiceFail(){
        var req = new Request();
        var service = new LogoutService();
        req.setAuthToken("wrongtoken");
        assertThrows(DataAccessException.class, () -> service.logout(req));
    }
    @Test
    public void logoutService() throws DataAccessException{
        var req = new Request();
        var service = new LogoutService();
        req.setAuthToken(token);
        service.logout(req);
        assertThrows(DataAccessException.class, () -> DataAccess.getInstance().getAuthAccess().find(token));
    }
    @Test
    public void loginServiceFail(){
        var service = new LoginService();
        var req = new Request();
        req.setUsername("jerry");
        req.setPassword("wrongpassword");
        assertThrows(DataAccessException.class, () -> service.login(req));
    }
    @Test
    public void loginService() throws DataAccessException{
        var service = new LoginService();
        var req = new Request();
        req.setUsername("jerry");
        req.setPassword("jerry'spassword");
        var response = service.login(req);
        assert(response.getToken() != null);
    }

    @Test
    public void listGamesService() throws DataAccessException{
        var service = new ListGamesService();
        var req = new Request();

        req.setAuthToken(token);
        var games = service.listGames(req).getGameList();
        assert(games.get(0) != null);
    }

    @Test
    public void listGamesServiceFail(){
        var service = new ListGamesService();
        var req = new Request();

        req.setAuthToken("wrong token");
        assertThrows(DataAccessException.class, () -> service.listGames(req));
    }
    @Test
    public void joinGameService() throws DataAccessException{
        var service = new JoinGameService();
        var req = new Request();

        req.setAuthToken(token);
        req.setGameID(gameID);
        req.setPlayerColor(ChessGame.TeamColor.WHITE);

        service.joinGame(req);

        assert DataAccess.getInstance().getGameAccess().find(gameID).getWhiteUsername().equals("jerry");
    }

    @Test
    public void joinGameServiceFail(){
        var service = new JoinGameService();
        var req = new Request();

        req.setAuthToken(token);
        req.setGameID(7423765);
        req.setPlayerColor(ChessGame.TeamColor.WHITE);

        assertThrows(DataAccessException.class, () -> service.joinGame(req));
    }

    @Test
    public void createGameService() throws DataAccessException{
        var gameService = new CreateGameService();
        var req = new Request();
        req.setAuthToken(token);
        req.setGameName("bob's game");
        gameID = Integer.parseInt(gameService.createGame(req).getGameID());
        GameData game = DataAccess.getInstance().getGameAccess().find(gameID);
        assert game != null;
    }

    @Test
    public void createGameServiceFail(){
        var gameService = new CreateGameService();
        var req = new Request();
        req.setAuthToken("wrong token");
        req.setGameName("game name");
        assertThrows(DataAccessException.class, () -> gameService.createGame(req));
    }

    @Test
    public void clearApplicationService(){
        new ClearAppService().clearApplication();
        assert DataAccess.getInstance().getGameAccess().findAll().isEmpty();
    }

}

package api;

import chess.ChessGame;
import connect.ServerFacade;
import exception.ResponseException;
import models.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ui.Client;
import ui.Repl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerFacadeTests {
    private static ServerFacade server;
    private static String token;

    @BeforeAll
    public static void init() throws ResponseException {
        var repl = new Repl("http://localhost:8080");
        server = new ServerFacade("http://localhost:8080", new Client("http://localhost:8080", repl));
        var req = new Request();
        req.setUsername("mr.ski");
        req.setPassword("9979");
        req.setEmail("email");
        try {
            token = server.login(req).getToken();
        }
        catch(Exception e){
            token = server.register(req).getToken();
        }
    }

    @AfterEach
    public void reset() throws ResponseException {
        server.clear(new Request());
        var req = new Request();
        req.setUsername("mr.ski");
        req.setPassword("9979");
        req.setEmail("email");
        token = server.register(req).getToken();
    }
    @Test
    void goodRegister() throws ResponseException {
        var request = new Request();
        request.setUsername("bob");
        request.setPassword("password");
        request.setEmail("bob@gmail.com");

        var response = server.register(request);
        assert response.getToken() != null;
    }

    @Test
    void badRegister(){
        var request = new Request();
        request.setUsername("bob");
        request.setPassword("password");

        assertThrows(Exception.class, () -> server.register(request));
    }

    @Test
    void goodLogin() throws ResponseException {
        var request = new Request();
        request.setAuthToken(token);
        server.logout(request);

        request = new Request();
        request.setUsername("mr.ski");
        request.setPassword("9979");
        var response = server.login(request);
        assert response.getToken() != null;
    }

    @Test
    void badLogin() throws ResponseException {
        var request = new Request();
        request.setAuthToken(token);
        server.logout(request);

        request = new Request();
        request.setUsername("mr.ski");
        request.setPassword("wrong password");
        Request finalRequest = request;
        assertThrows(Exception.class, () -> server.login(finalRequest));
    }

    @Test
    void goodLogout() throws ResponseException {
        var request = new Request();
        request.setAuthToken(token);
        server.logout(request);

        request.setGameName("game name");
        assertThrows(Exception.class, () -> server.create(request));
    }

    @Test
    void badLogout(){
        var request = new Request();
        request.setAuthToken("wrong token");
        assertThrows(Exception.class, () -> server.logout(request));
    }

    @Test
    void goodList() throws ResponseException{
        var request = new Request();
        request.setAuthToken(token);
        request.setGameName("game");
        server.create(request);
        request.setAuthToken(token);
        var response = server.list(request);
        assert !response.getGameList().isEmpty();
    }

    @Test
    void badList(){
        var request = new Request();
        request.setAuthToken("wrong token");
        assertThrows(Exception.class, () -> server.list(request));
    }

    @Test
    void goodCreate() throws ResponseException {
        var request = new Request();
        request.setAuthToken(token);
        request.setGameName("game");
        var response = server.create(request);
        assert response.getGameID() != null;
    }

    @Test
    void badCreate() {
        var request = new Request();
        request.setAuthToken(token);
        //don't set a game name
        assertThrows(Exception.class, () -> server.create(request));
    }

    @Test
    void goodJoin() throws ResponseException, IOException {
        var request = new Request();
        request.setAuthToken(token);
        request.setGameName("game");
        int gameID = Integer.parseInt(server.create(request).getGameID());

        request = new Request();
        request.setAuthToken(token);
        request.setGameID(gameID);
        request.setPlayerColor(ChessGame.TeamColor.BLACK);
        server.join(request);

        request = new Request();
        request.setAuthToken(token);
        var response = server.list(request);
        assert response.getGameList().get(0).getBlackUsername().equals("mr.ski");
    }

    @Test
    void badJoin() {
        var request = new Request();
        request.setAuthToken(token);
        request.setGameID(0);
        request.setPlayerColor(ChessGame.TeamColor.BLACK);
        assertThrows(Exception.class, () -> server.join(request));
    }
}

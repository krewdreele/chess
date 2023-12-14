package connect;

import chess.*;
import com.google.gson.GsonBuilder;
import exception.ResponseException;
import models.Request;
import models.Response;
import serializers.*;

import java.io.IOException;

public class ServerFacade {
    private final HttpCommunicator http;
    private final WebSocketCommunicator ws;
    private final String url;
    public ServerFacade(String url, NotificationHandler observer){
        var builder = new GsonBuilder()
                .registerTypeAdapter(ChessGame.class,
                        new ChessGameSerialManager())
                .registerTypeAdapter(ChessBoard.class,
                        new ChessBoardSerialManager())
                .registerTypeAdapter(ChessPiece.class,
                        new ChessPieceInterfaceManager())
                .registerTypeAdapter(ChessPieceImpl.class,
                        new ChessPieceSerialManager())
                .registerTypeAdapter(ChessPosition.class,
                        new ChessPositionSerialManager())
                .registerTypeAdapter(ChessMove.class, new ChessMoveSerialManager()).create();

        http = new HttpCommunicator(url, builder);
        ws = new WebSocketCommunicator(observer, builder);
        this.url = url;
    }

    public Response clear(Request req) throws ResponseException{
        var path = "/db";
        return http.makeRequest("DELETE", path, req);
    }
    public Response register(Request req) throws ResponseException{
        var path = "/user";
        return http.makeRequest("POST", path, req);
    }
    public Response login(Request req) throws ResponseException{
        var path = "/session";
        return http.makeRequest("POST", path, req);
    }
    public Response logout(Request req) throws ResponseException{
        var path = "/session";
        return http.makeRequest("DELETE", path, req);
    }
    public Response list(Request req) throws ResponseException{
        var path = "/game";
        return http.makeRequest("GET", path, req);
    }
    public Response create(Request req) throws ResponseException{
        var path = "/game";
        return http.makeRequest("POST", path, req);
    }
    public void join(Request req) throws ResponseException, IOException {
        http.makeRequest("PUT", "/game", req);
        ws.makeConnection(url);
        if (req.getPlayerColor() == null) {
            ws.observe(req.getAuthToken(), req.getGameID());
        } else {
            ws.join(req.getAuthToken(), req.getGameID(), req.getPlayerColor());
        }
    }

    public void leaveGame(String token) throws IOException {
        ws.leaveGame(token);
    }

    public void makeMove(String token, ChessMove move) throws IOException {
        ws.makeMove(token, move);
    }

    public void resign(String token) throws IOException {
        ws.resign(token);
    }
}

package server;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.Objects;

public class Connection {
    public int gameID;

    public ChessGame.TeamColor color;
    public Session session;

    public Connection(int gameID, Session session, ChessGame.TeamColor color) {
        this.gameID = gameID;
        this.session = session;
        this.color = color;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
    public void setColorAndSend(ServerMessage msg, Gson builder) throws IOException {
        msg.setColor(Objects.requireNonNullElse(color, ChessGame.TeamColor.WHITE));
        send(builder.toJson(msg));
    }
}
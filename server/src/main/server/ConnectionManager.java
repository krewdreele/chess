package server;
import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, int gameID, Session session, ChessGame.TeamColor color) {
        var connection = new Connection(gameID, session, color);
        connections.put(authToken, connection);
    }

    public void remove(String authToken){
        connections.remove(authToken);
    }
    public void send(String authToken, String message) throws IOException {
        connections.get(authToken).send(message);
    }
    public void broadcast(String excludeAuthToken, String notification) throws IOException {
        var removeList = new ArrayList<String>();
        for (var key : connections.keySet()) {
            var c = connections.get(key);
            if (c.session.isOpen()) {
                if (!key.equals(excludeAuthToken)) {
                    c.send(notification);
                }
            } else {
                removeList.add(key);
            }
        }

        // Clean up any connections that were left open.
        for (var key : removeList) {
            connections.remove(key);
        }
    }

    public void broadcast(int gameID, ServerMessage message, Gson builder) throws IOException {
        var removeList = new ArrayList<String>();
        for(var key : connections.keySet()){
            var c = connections.get(key);
            if(c.session.isOpen()){
                if(c.gameID == gameID){
                    if(message.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                        c.setColorAndSend(message, builder);
                    }
                    else{
                        c.send(builder.toJson(message));
                    }
                }
            }
            else{
                removeList.add(key);
            }
        }

        for(var key : removeList){
            connections.remove(key);
        }
    }
}

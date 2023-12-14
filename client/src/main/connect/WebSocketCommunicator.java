package connect;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exception.ResponseException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketCommunicator extends Endpoint {
    private Session session;
    private final NotificationHandler notificationHandler;
    private final Gson builder;
    public WebSocketCommunicator(NotificationHandler notificationHandler, Gson builder){
        this.builder = builder;
        this.notificationHandler = notificationHandler;
    }

    public void makeConnection(String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = builder.fromJson(message, ServerMessage.class);
                    notificationHandler.notify(notification);
                }
                @OnWebSocketError
                public void onError(String message){
                    ServerMessage notification = builder.fromJson(message, ServerMessage.class);
                    notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    public void join(String authToken, int gameID, ChessGame.TeamColor color) throws IOException {
        UserGameCommand command = new UserGameCommand(authToken, UserGameCommand.CommandType.JOIN_PLAYER);
        command.setGameID(gameID);
        command.setColor(color);
        this.session.getBasicRemote().sendText(builder.toJson(command));
    }

    public void observe(String authToken, int gameID) throws IOException {
        UserGameCommand command = new UserGameCommand(authToken, UserGameCommand.CommandType.JOIN_OBSERVER);
        command.setGameID(gameID);
        this.session.getBasicRemote().sendText(builder.toJson(command));
    }

    public void leaveGame(String authToken) throws IOException {
        var command = new UserGameCommand(authToken, UserGameCommand.CommandType.LEAVE);
        this.session.getBasicRemote().sendText(builder.toJson(command));
    }

    public void makeMove(String authToken, ChessMove move) throws IOException {
        var command = new UserGameCommand(authToken, UserGameCommand.CommandType.MAKE_MOVE);
        command.setMove(move);
        this.session.getBasicRemote().sendText(builder.toJson(command));
    }

    public void resign(String token) throws IOException {
        var command = new UserGameCommand(token, UserGameCommand.CommandType.RESIGN);
        this.session.getBasicRemote().sendText(builder.toJson(command));
    }
}
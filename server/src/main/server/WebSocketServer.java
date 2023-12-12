package server;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.DataAccess;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import serializers.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketServer {
    private final ConnectionManager connectionManager;
    private final Gson builder;
    private final DataAccess dataManager;

    public WebSocketServer(DataAccess dataManager){
        builder = new GsonBuilder()
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
                .registerTypeAdapter(ChessMove.class,
                        new ChessMoveSerialManager()).create();
        this.dataManager = dataManager;
        connectionManager = new ConnectionManager();
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException{
        var command = builder.fromJson(message, UserGameCommand.class);
        try {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> joinPlayer(session, command);
                case JOIN_OBSERVER -> joinObserver(session, command);
                case MAKE_MOVE -> makeMove(command);
                case LEAVE -> leave(command);
            }
        }
        catch(DataAccessException | InvalidMoveException e){
            var msg = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            msg.setErrorMessage(e.getMessage());
            session.getRemote().sendString(builder.toJson(msg));
        }
    }

    private void leave(UserGameCommand command) throws DataAccessException {
        connectionManager.remove(command.getAuthString());
    }

    private void makeMove(UserGameCommand command) throws DataAccessException, InvalidMoveException, IOException {
        var game = dataManager.getGameAccess().find(command.getGameID()).getChessGame();
        var pieceType = game.getBoard().getPiece(command.getMove().getStartPosition()).getPieceType();
        var color = connectionManager.connections.get(command.getAuthString()).color;

        if(color == null){
            throw new InvalidMoveException("Can't move pieces as an observer!");
        }
        if(color != game.getBoard().getPiece(command.getMove().getStartPosition()).getTeamColor()){
            throw new InvalidMoveException("Can't move the other team's pieces!");
        }

        //make move and load board for everyone in the game
        game.makeMove(command.getMove());
        dataManager.getGameAccess().updateGame(command.getGameID(), game);
        connectionManager.broadcast(command.getGameID(), loadGameMessage(command.getGameID(), null), builder);

        //notify everyone else of the move
        var notifyMove = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        int row = command.getMove().getEndPosition().getRow();
        int column = command.getMove().getEndPosition().getColumn();
        char letter = (char) ((8 - column) + 'a');
        notifyMove.setMessage(color + " " + pieceType + " to " + letter + row);
        connectionManager.broadcast(command.getAuthString(), builder.toJson(notifyMove));

        //check to see if opponent is in checkmate and broadcast if that's the case
        var opponentColor = color == ChessGame.TeamColor.WHITE ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
        if(game.isInCheckmate(opponentColor)){
            var msg = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            msg.setMessage(opponentColor + " is in checkmate, " + color + " wins!");
            connectionManager.broadcast(command.getGameID(), msg, builder);
        }
    }

    private void joinPlayer(Session session, UserGameCommand command) throws DataAccessException, IOException {
        var username = dataManager.getAuthAccess().find(command.getAuthString());
        dataManager.getGameAccess().claimSpot(command.getGameID(), username, command.getColor());

        connectionManager.add(command.getAuthString(), command.getGameID(), session, command.getColor());

        connectionManager.send(command.getAuthString(), builder.toJson(loadGameMessage(command.getGameID(), command.getColor())));

        var notify = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notify.setMessage(username + " joined as " + command.getColor());
        connectionManager.broadcast(command.getAuthString(), builder.toJson(notify));
    }
    private void joinObserver(Session session, UserGameCommand command) throws DataAccessException, IOException {
        connectionManager.add(command.getAuthString(),command.getGameID(), session, null);

        connectionManager.send(command.getAuthString(), builder.toJson(loadGameMessage(command.getGameID(), ChessGame.TeamColor.WHITE)));

        var notify = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        var username = dataManager.getAuthAccess().find(command.getAuthString());
        notify.setMessage(username + " started observing");
        connectionManager.broadcast(command.getAuthString(), builder.toJson(notify));
    }

    private ServerMessage loadGameMessage(int gameID, ChessGame.TeamColor color) throws DataAccessException {
        var loadMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        var game = dataManager.getGameAccess().find(gameID);
        loadMessage.setGame(game);
        loadMessage.setColor(color);
        return loadMessage;
    }
}



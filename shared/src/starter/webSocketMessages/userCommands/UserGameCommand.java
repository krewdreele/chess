package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(String authToken, CommandType command) {
        this.authToken = authToken;
        this.commandType = command;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;

    private int gameID;
    private ChessGame.TeamColor playerColor;
    private ChessMove move;
    public int getGameID() {
        return gameID;
    }
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    public ChessGame.TeamColor getColor() {
        return playerColor;
    }
    public void setColor(ChessGame.TeamColor color) {
        this.playerColor = color;
    }
    public ChessMove getMove() {
        return move;
    }
    public void setMove(ChessMove move) {
        this.move = move;
    }

    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}

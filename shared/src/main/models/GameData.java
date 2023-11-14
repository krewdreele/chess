package models;

import chess.ChessGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Holds all the game data and response message (if necessary)
 */
public class GameData{
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private final String gameName;
    private ChessGame game;
    private final List<String> observers;

    public GameData(String gameName, ChessGame game) {
        this.gameName = gameName;
        this.game = game;
        observers = new ArrayList<>();
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public void addObserver(String username){
        observers.add(username);
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return gameID == gameData.gameID &&
                Objects.equals(whiteUsername, gameData.whiteUsername) &&
                Objects.equals(blackUsername, gameData.blackUsername) &&
                Objects.equals(gameName, gameData.gameName) &&
                Objects.equals(observers, gameData.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game, observers);
    }

    public void setGameID(int id) {
        gameID = id;
    }
}

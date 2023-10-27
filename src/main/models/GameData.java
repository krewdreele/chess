package models;

import chess.ChessGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all the game data and response message (if necessary)
 */
public class GameData{
    private final int gameID;
    private String whiteUsername;
    private String blackUsername;
    private final String gameName;
    private ChessGame game;
    private List<String> observers;

    public GameData(int gameID, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.game = game;
        whiteUsername = "";
        blackUsername = "";
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

    public String getGameName() {
        return gameName;
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
}

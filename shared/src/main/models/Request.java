package models;

import chess.ChessGame;

/**
 * Holds information about the request coming in depending on what kind of request
 */
public class Request {
    private String message;
    private String username;
    private String password;
    private String authToken;
    private String email;
    private String gameName;
    private ChessGame.TeamColor playerColor;
    private int gameID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmail() {
        return email;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String name){
        this.gameName = name;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(ChessGame.TeamColor color){
        this.playerColor = color;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
}

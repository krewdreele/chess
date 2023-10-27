package models;

import java.util.List;

/**
 * Holds information about the response from the server
 */
public class Response {
    private String message;
    private String username;
    private String gameID;
    private String token;
    private List<GameData> gameList;

    public Response() {
    }

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

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setGameList(List<GameData> gameList) {
        this.gameList = gameList;
    }
}

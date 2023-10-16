package models;

import java.util.List;

/**
 * Holds information about the response from the server
 */
public class Response {
    public String message;
    private GameData game;
    private AuthToken token;
    private List<GameData> gameList;

    public Response(String message) {
        this.message = message;
    }

    public GameData getGame() {
        return game;
    }

    public void setGame(GameData game) {
        this.game = game;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public List<GameData> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameData> gameList) {
        this.gameList = gameList;
    }
}

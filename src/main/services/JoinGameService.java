package services;

import chess.ChessGame;
import models.AuthToken;
import models.GameData;

/**
 * Service for adding a player or observer to a game
 */
public class JoinGameService {

    /**
     * Authenticate / get user from database
     * Add user to the game with corresponding color
     * @param game - game user is being added to
     * @param token - the authentication of the user
     * @param color - the color the user wishes to enter as (white, black, null(observer))
     * @return the response message
     */
    public String joinGame(GameData game, AuthToken token, ChessGame.TeamColor color){return "";}
}

package connect;

import chess.ChessGame;
import exception.ResponseException;
import models.GameData;
import models.Request;
import ui.Repl;
import ui.State;

import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private final ServerFacade server;
    private State state = State.LOGGEDOUT;
    private final Repl r;
    private String authToken;
    private ArrayList<GameData> games;

    public Client(String serverUrl, Repl r){
        server = new ServerFacade(serverUrl);
        this.r = r;
        games = new ArrayList<>();
    }
    public String eval(String input){
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            if (state == State.LOGGEDOUT) {
                return switch (cmd) {
                    case "login" -> login(params);
                    case "register" -> register(params);
                    case "quit" -> "See ya! :(";
                    default -> help();
                };
            } else {
                return switch (cmd) {
                    case "logout" -> logout();
                    case "create" -> newGame(params);
                    case "list" -> listGames();
                    case "join" -> joinGame(params);
                    case "observe" -> observe(params);
                    case "quit" -> "See ya! :(";
                    default -> help();
                };
            }
        }
        catch (Exception e){
            r.notify(e.getMessage());
        }
        return "";
    }

    private String observe(String... params) {
        return "";
    }

    private String joinGame(String... params) throws ResponseException {
        var request = new Request();
        if(params.length != 2){
            throw new ResponseException(400, "bad request");
        }
        if(games.isEmpty()){
            listGames();
            if(games.isEmpty()){
                return "no games!";
            }
        }
        request.setAuthToken(authToken);
        if(params[0].equals("WHITE") || params[0].equals("white") || params[0].equals("White")){
            request.setPlayerColor(ChessGame.TeamColor.WHITE);
        }
        else if(params[0].equals("BLACK") || params[0].equals("black") || params[0].equals("Black")){
            request.setPlayerColor(ChessGame.TeamColor.BLACK);
        }
        var index = Integer.parseInt(params[1]) - 1;
        request.setGameID(games.get(index).getGameID());
        server.join(request);
        return "Success!";
    }

    private String listGames() throws ResponseException {
        var request = new Request();
        request.setAuthToken(authToken);
        var response = server.list(request);
        var sb = new StringBuilder();
        for(int i=0; i<response.getGameList().size(); i++){
            var game = response.getGameList().get(i);
            var white = " ~ player 1(white): " + game.getWhiteUsername();
            var black = " ~ player 2(black): " + game.getBlackUsername();
            var name = game.getGameName();
            sb.append(i+1).append(". ").append(name).append(white).append(black).append("\n");
        }
        games = new ArrayList<>(response.getGameList());
        if(games.isEmpty()){
            return "No games!";
        }
        return sb.toString();
    }

    private String newGame(String... params) throws ResponseException {
        var request = new Request();
        request.setAuthToken(authToken);
        if(params.length != 1){
            throw new ResponseException(400, "bad request");
        }
        request.setGameName(params[0]);
        var response = server.create(request);
        return "Game created!";
    }

    private String logout() throws ResponseException {
        var request = new Request();
        request.setAuthToken(authToken);
        server.logout(request);
        state = State.LOGGEDOUT;
        authToken = "";
        return help();
    }

    private String login(String... params) throws ResponseException {
        var request = new Request();
        if(params.length != 2) {
            throw new ResponseException(400, "bad request");
        }
        request.setUsername(params[0]);
        request.setPassword(params[1]);
        var response = server.login(request);
        state = State.LOGGEDIN;
        authToken = response.getToken();
        return "You've logged in as " + response.getUsername() + "\n" + help();
    }

    private String register(String... params) throws ResponseException{
        var request = new Request();
        if(params.length != 3) {
            throw new ResponseException(400, "bad request");
        }
        request.setUsername(params[0]);
        request.setPassword(params[1]);
        request.setEmail(params[2]);
        var response = server.register(request);
        state = State.LOGGEDIN;
        authToken = response.getToken();
        return "You've registered as " + response.getUsername() + ". Congrats!\n" + help();
    }

    public String help() {
        if (state == State.LOGGEDOUT) {
            return """
                    - login <username> <password>
                    - register <username> <password> <email>
                    - quit
                    """;
        }
        return """
                - logout
                - create <game name>
                - list
                - join <color: WHITE/BLACK> <game ID>
                - observe <game ID>
                - quit
                """;
    }
}

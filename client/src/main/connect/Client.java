package connect;

import exception.ResponseException;
import models.Request;
import ui.Repl;
import ui.State;

import java.util.Arrays;

public class Client {

    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.LOGGEDOUT;
    private Repl r;
    private String authToken;

    public Client(String serverUrl, Repl r){
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.r = r;
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
                    case "quit" -> "quit";
                    default -> help();
                };
            } else {
                return switch (cmd) {
                    case "logout" -> logout();
                    case "create" -> newGame(params);
                    case "list" -> listGames();
                    case "join" -> joinGame(params);
                    case "observe" -> observe(params);
                    case "quit" -> "quit";
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

    private String joinGame(String... params) {
        return "";
    }

    private String listGames() {
        return "";
    }

    private String newGame(String... params) throws ResponseException {
        var request = new Request();
        if(params.length != 1){
            throw new ResponseException(400, "bad request");
        }
        request.setGameName(params[0]);
        var response = server.create(request);
        return "Game created! Game ID: " + response.getGameID();
    }

    private String logout() throws ResponseException {
        var request = new Request();
        request.setAuthToken(authToken);
        server.logout(request);
        state = State.LOGGEDOUT;
        return "See ya!";
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
        return "You've logged in as " + response.getUsername();
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
        return "You've registered as " + response.getUsername() + ". Congrats!";
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

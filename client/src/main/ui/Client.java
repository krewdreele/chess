package ui;

import chess.*;
import connect.ServerFacade;
import exception.ResponseException;
import models.GameData;
import models.Request;

import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private final ServerFacade server;
    private State state = State.LOGGEDOUT;
    private final Repl repl;
    private String authToken;
    private ArrayList<GameData> games;
    private Permission permission;

    public Client(String serverUrl, Repl repl){
        server = new ServerFacade(serverUrl);
        this.repl = repl;
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
                    case "clear" -> clear();
                    case "quit" -> "See ya! :(";
                    default -> help();
                };
            }
        }
        catch (Exception e){
            repl.notify(e.getMessage());
        }
        return "";
    }

    private String clear() throws ResponseException {
        if(permission != Permission.ADMIN){
            return "You do not have permission to do this";
        }

        server.clear(new Request());
        var request = new Request();
        request.setUsername("mr.ski");
        request.setPassword("9979");
        request.setEmail("email");
        server.register(request);
        return "Done";
    }

    private String observe(String... params) throws ResponseException {
        var request = new Request();
        if (games.isEmpty()) {
            listGames();
            if (games.isEmpty()) {
                return "No games!";
            }
        }
        try{
            var index = Integer.parseInt(params[0]) - 1;
            request.setGameID(games.get(index).getGameID());
        }
        catch (Exception e){
            return "Please enter a valid game number";
        }
        request.setAuthToken(authToken);
        var response = server.join(request);
        var board = new ChessBoardImpl();
        board.resetBoard();
        return drawBoard(board, ChessGame.TeamColor.WHITE) + "\n\n" + drawBoard(board, ChessGame.TeamColor.BLACK);
    }

    private String joinGame(String... params) throws ResponseException {
        var request = new Request();
        if (params.length != 2) {
            return "You need to enter a color and game number!";
        }
        if (games.isEmpty()) {
            listGames();
            if (games.isEmpty()) {
                return "No games!";
            }
        }
        request.setAuthToken(authToken);
        if (params[1].equals("WHITE") || params[1].equals("white") || params[1].equals("White")) {
            request.setPlayerColor(ChessGame.TeamColor.WHITE);
        } else if (params[1].equals("BLACK") || params[1].equals("black") || params[1].equals("Black")) {
            request.setPlayerColor(ChessGame.TeamColor.BLACK);
        }

        int index = Integer.parseInt(params[0]) - 1;
        try {
            request.setGameID(games.get(index).getGameID());
        } catch (Exception e) {
            return "Not a valid game number! Use the 'list' command";
        }
        var response = server.join(request);
        var board = new ChessBoardImpl();
        board.resetBoard();
        return drawBoard(board, ChessGame.TeamColor.WHITE) + "\n\n\n" + drawBoard(board, ChessGame.TeamColor.BLACK);
    }

    private String listGames() throws ResponseException {
        var request = new Request();
        request.setAuthToken(authToken);
        var response = server.list(request);
        var sb = new StringBuilder();
        for(int i=0; i<response.getGameList().size(); i++){
            var game = response.getGameList().get(i);
            var white = " ~ player 1 (white): " + game.getWhiteUsername();
            var black = " ~ player 2 (black): " + game.getBlackUsername();
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
        listGames();
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
        if(params[0].equals("mr.ski") && params[1].equals("9979")){
            permission = Permission.ADMIN;
        }
        else{
            permission = Permission.PLAYER;
        }
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
                - join <game ID> <color: WHITE/BLACK>
                - observe <game ID>
                - quit
                """;
    }

    private String drawBoard(ChessBoard board, ChessGame.TeamColor color){
        var sb = new StringBuilder();
        int num_squares = 0;

        //Top row letters
        appendLetters(sb, color);
        sb.append("\n");

        for(int i=8; i>0; i--){
            for(int j=8; j>0; j--){
                int row = color == ChessGame.TeamColor.BLACK ? 9 - i : i;
                int column = color == ChessGame.TeamColor.BLACK ? 9 - j : j;
                //left side numbers
                if((color == ChessGame.TeamColor.WHITE && column == 8) || (color == ChessGame.TeamColor.BLACK && column == 1)){
                    sb.append(EscapeSequences.SET_BG_COLOR_BLUE).append(EscapeSequences.SET_TEXT_COLOR_WHITE).append(" ").append(row).append(" ");
                }

                //pieces
                ChessPosition position = new ChessPositionImpl(row, column);
                ChessPiece piece = board.getPiece(position);
                sb.append(num_squares % 2 == 0 ? EscapeSequences.SET_BG_COLOR_LIGHT_GREY : EscapeSequences.SET_BG_COLOR_DARK_GREY);
                if(piece == null){
                    sb.append(EscapeSequences.EMPTY);
                    num_squares += 1;
                }
                else if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                    num_squares += 1;
                    sb.append(EscapeSequences.SET_TEXT_COLOR_WHITE);
                    appendPiece(sb, board, position);
                }
                else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    num_squares += 1;
                    sb.append(EscapeSequences.SET_TEXT_COLOR_BLACK);
                    appendPiece(sb, board, position);
                }
                //right side numbers
                if((color == ChessGame.TeamColor.BLACK && column == 8) || (color == ChessGame.TeamColor.WHITE && column == 1)){
                    sb.append(EscapeSequences.SET_BG_COLOR_BLUE)
                            .append(EscapeSequences.SET_TEXT_COLOR_WHITE)
                            .append(" ")
                            .append(row)
                            .append(" ")
                            .append("\u001b[;49m")
                            .append("\n");

                    num_squares -= 1;
                }
            }
        }
        //bottom row letters
        appendLetters(sb, color);
        return sb.toString();
    }

    private void appendLetters(StringBuilder sb, ChessGame.TeamColor color){
        sb.append(EscapeSequences.SET_BG_COLOR_BLUE).append(EscapeSequences.SET_TEXT_COLOR_WHITE);
        sb.append("\u2003");
        for(int i=0; i<8;i++){
            sb.append(" \u2003");
            if(color == ChessGame.TeamColor.BLACK){
                sb.append((char) ('h' - i));
            }
            else {
                sb.append((char) ('a' + i));
            }
        }
        sb.append("    ");
        sb.append("\u001b[;49m");
    }

    private void appendPiece(StringBuilder sb, ChessBoard board, ChessPosition position){
        sb.append(switch (board.getPiece(position).getPieceType()) {
            case KING -> EscapeSequences.BLACK_KING;
            case QUEEN -> EscapeSequences.BLACK_QUEEN;
            case BISHOP -> EscapeSequences.BLACK_BISHOP;
            case KNIGHT -> EscapeSequences.BLACK_KNIGHT;
            case ROOK -> EscapeSequences.BLACK_ROOK;
            case PAWN -> EscapeSequences.BLACK_PAWN;
        });
    }
}

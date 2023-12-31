package server;

import com.google.gson.Gson;
import daos.DataAccess;
import dataAccess.DataAccessException;
import handlers.*;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

/**
 * Class that starts the Spark server and calls the corresponding handler for each http request
 */
public class ServerMain {

    private DataAccess dataManager;
    private WebSocketServer wsServer;
    public static void main(String[] args){
        new ServerMain().run();
    }

    private void run(){
        try {
            dataManager = new DataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        wsServer = new WebSocketServer(dataManager);

        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");

        Spark.webSocket("/connect", wsServer);

        Spark.post("/user", (request, response) -> RegisterHandler.registerRequest(request, dataManager));

        Spark.post("/session", (request, response) -> LoginHandler.loginRequest(request, dataManager));

        Spark.delete("/db", (request, response) -> ClearAppHandler.clearApplicationRequest(dataManager));

        Spark.delete("/session", (request, response) -> LogoutHandler.logoutRequest(request, dataManager));

        Spark.post("/game", (request, response) -> CreateGameHandler.createGameRequest(request, dataManager));

        Spark.get("/game", (request, response) -> ListGamesHandler.listGamesRequest(request, dataManager));

        Spark.put("/game", ((request, response) -> JoinGameHandler.JoinGameRequest(request, dataManager)));

        Spark.exception(DataAccessException.class, this::errorHandler);
    }

    public Object errorHandler(DataAccessException e, Request req, Response res){
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage().substring(5))));
        res.type("application/json");
        res.status(Integer.parseInt(e.getMessage().substring(0, 3)));
        res.body(body);
        return body;
    }
}

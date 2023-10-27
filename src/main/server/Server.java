package server;

import handlers.*;
import spark.Request;
import spark.Spark;


/**
 * Class that starts the Spark server and calls the corresponding handler for each http request
 */
public class Server {

    /**
     * starts the server
     */
    public static void main(String[] args){
        new Server().run();
    }

    private void run(){
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("public");

        Spark.post("/user", (request, response) -> RegisterHandler.registerRequest(request));

        Spark.post("/session", (request, response) -> LoginHandler.loginRequest(request));

        Spark.delete("/db", (request, response) -> ClearAppHandler.clearApplicationRequest());

        Spark.delete("/session", (request, response) -> LogoutHandler.logoutRequest(request));

        Spark.post("/game", (request, response) -> CreateGameHandler.createGameRequest(request));

        Spark.get("/game", (request, response) -> ListGamesHandler.listGamesRequest(request));

        Spark.put("/game", ((request, response) -> JoinGameHandler.JoinGameRequest(request)));
    }
}

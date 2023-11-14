package connect;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.ResponseException;
import models.Request;
import models.Response;
import serializers.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;

    private static Gson builder;

    public ServerFacade(String url) {
        serverUrl = url;
        builder = new GsonBuilder()
                .registerTypeAdapter(ChessGame.class,
                        new ChessGameDS())
                .registerTypeAdapter(ChessBoard.class,
                        new ChessBoardDS())
                .registerTypeAdapter(ChessPiece.class,
                        new ChessPieceInterfaceDS())
                .registerTypeAdapter(ChessPieceImpl.class,
                        new ChessPieceSerializationManager())
                .registerTypeAdapter(ChessPosition.class,
                        new ChessPositionDS()).create();
    }

    public Response register(Request req) throws ResponseException{
        var path = "/user";
        return makeRequest("POST", path, req);
    }
    public Response login(Request req) throws ResponseException{
        var path = "/session";
        return makeRequest("POST", path, req);
    }
    public Response logout(Request req) throws ResponseException{
        var path = "/session";
        return makeRequest("DELETE", path, req);
    }
    public Response list(Request req) throws ResponseException{
        var path = "/game";
        return makeRequest("GET", path, req);
    }
    public Response create(Request req) throws ResponseException{
        var path = "/game";
        return makeRequest("POST", path, req);
    }
    public Response join(Request req) throws ResponseException{
        var path = "/game";
        return makeRequest("PUT", path, req);
    }

    private Response makeRequest(String method, String path, Request request) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            http.setRequestProperty("Authorization", request.getAuthToken());
            request.setAuthToken(null);
            if(!method.equals("GET")) writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    private static void writeBody(Request request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = builder.toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static Response readBody(HttpURLConnection http) throws IOException {
        Response response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                response = builder.fromJson(reader, Response.class);
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}

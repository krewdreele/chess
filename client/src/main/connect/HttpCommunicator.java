package connect;

import com.google.gson.Gson;
import exception.ResponseException;
import models.Request;
import models.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpCommunicator {

    private final String serverUrl;
    private final Gson builder;
    public HttpCommunicator(String serverUrl, Gson builder){
        this.serverUrl = serverUrl;
        this.builder = builder;
    }
    Response makeRequest(String method, String path, Request request) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            http.setRequestProperty("Authorization", request.getAuthToken());
            if(!method.equals("GET")) writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    private void writeBody(Request request, HttpURLConnection http) throws IOException {
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

    private Response readBody(HttpURLConnection http) throws IOException {
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

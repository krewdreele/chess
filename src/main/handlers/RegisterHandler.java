package handlers;
import models.AuthToken;
import models.UserData;

import java.net.http.HttpRequest;

/**
 * handles the http conversion for registering a new user and connects to the corresponding service
 */
public class RegisterHandler {

    /**
     * Convert request to an object the service can use
     * Call the 'register user' service
     * @param request - the http request to be converted
     */
    public void registerRequest(HttpRequest request){}
}

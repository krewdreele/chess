package handlers;

import models.AuthToken;
import models.UserData;

import java.net.http.HttpRequest;

/**
 * Handles the http conversion for login requests and connects to the corresponding service
 */
public class LoginHandler {

    /**
     * Convert http to java object server can work with
     * Call the 'login' service
     * @param request - the http request
     */
    public void loginRequest(HttpRequest request){
    }

}

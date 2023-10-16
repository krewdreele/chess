package handlers;

import models.UserData;

import java.net.http.HttpRequest;

/**
 *  Handles the http conversion for logging out and connects to the corresponding service
 */
public class LogoutHandler {

    /**
     * Convert http request into java obj that service can use
     * Calls the 'logout' service
     * @param request - the http request
     */
    public void logoutRequest(HttpRequest request){}
}

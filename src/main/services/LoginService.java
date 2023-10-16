package services;

import models.Request;
import models.Response;
import models.UserData;

/**
 * The service that logs in a user
 */
public class LoginService {

    /**
     * Finds user in the database
     * Inserts new auth token in the database
     * Sets the auth token data object's message
     * @param r - the request object containing the user info
     * @return - the authentication token object
     */
    public Response login(Request r){return null;}
}

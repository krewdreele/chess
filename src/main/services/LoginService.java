package services;

import models.AuthToken;
import models.UserData;

/**
 * The service that logs in a user
 */
public class LoginService {

    /**
     * Finds user in the database
     * Inserts new auth token in the database
     * Sets the auth token data object's message
     * @param user - the user wishing to log in
     * @return - the authentication token object
     */
    public AuthToken login(UserData user){return null;}
}

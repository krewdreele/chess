package services;

import models.AuthToken;
import models.UserData;

/**
 * Service for registering a new user with the database
 */
public class RegisterService {

    /**
     * Searches for user in the database
     * Inserts user if no matching username is found
     * Creates an authentication token for the user
     * Sets the auth token message with response
     * @param user - the user wishing to register
     * @return the new authentication token
     */
    public AuthToken register(UserData user){return null;}
}

package services;

import models.Response;
import models.Request;

/**
 * Service for registering a new user with the database
 */
public class RegisterService {

    /**
     * Searches for user in the database
     * Inserts user if no matching username is found
     * Creates an authentication token for the user
     * Sets the auth token message with response
     * @param r - the user wishing to register inside request obj
     * @return the new authentication token
     */
    public Response register(Request r){return null;}
}

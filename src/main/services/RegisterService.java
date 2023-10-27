package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.Response;
import models.Request;
import models.UserData;

import java.util.UUID;

/**
 * Service for registering a new user with the database
 */
public class RegisterService{

    /**
     * Searches for user in the database
     * Inserts user if no matching username is found
     * Creates an authentication token for the user
     * Puts the new authentication token and username in response
     * @param r - the user wishing to register inside request obj
     * @return - the response
     */
    public Response register(Request r){
        Response response = new Response();
        try{
            var user = new UserData(r.getUsername(), r.getPassword(), r.getEmail());
            DataAccess.getInstance().getUserAccess().insert(user);
            var token = new AuthToken(UUID.randomUUID().toString(), r.getUsername());
            DataAccess.getInstance().getAuthAccess().insert(token);
            response.setToken(token.getAuthToken());
            response.setUsername(r.getUsername());
        }
        catch(DataAccessException e){
            response.setMessage(e.getMessage());
        }

        return response;
    }
}

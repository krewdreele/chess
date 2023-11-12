package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.Request;
import models.Response;

import java.util.UUID;

/**
 * The service that logs in a user
 */
public class LoginService{

    /**
     * Finds user in the database
     * Inserts new auth token in the database
     * Creates response object
     * @param r - the request object containing the user info
     * @return - the response object
     */
    public Response login(Request r, DataAccess dataManager) throws DataAccessException{
        Response response = new Response();
        String password = dataManager.getUserAccess().find(r.getUsername());
        if (!password.equals(r.getPassword())) {
            throw new DataAccessException("401: unauthorized");
        }
        AuthToken newToken = new AuthToken(UUID.randomUUID().toString(), r.getUsername());
        dataManager.getAuthAccess().insert(newToken);
        var token = newToken.getAuthToken();
        response.setUsername(r.getUsername());
        response.setToken(token);
        return response;
    }
}

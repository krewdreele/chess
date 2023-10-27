package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.Request;
import models.Response;
import models.UserData;

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
    public Response login(Request r) throws DataAccessException{
        Response response = new Response();
        UserData user = DataAccess.getInstance().getUserAccess().find(r.getUsername());
        if (!user.getPassword().equals(r.getPassword())) {
            throw new DataAccessException("401: unauthorized");
        }
        AuthToken newToken = new AuthToken(UUID.randomUUID().toString(), user.getUsername());
        DataAccess.getInstance().getAuthAccess().insert(newToken);
        var token = newToken.getAuthToken();
        response.setUsername(r.getUsername());
        response.setToken(token);
        return response;
    }
}

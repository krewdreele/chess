package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.Response;
import models.Request;

/**
 * Service for logging a user out
 */
public class LogoutService {

    /**
     * Find / authenticate user in the database
     * Remove their authentication
     * @param r - the auth token to be removed inside request obj
     * @return the response message
     */
    public Response logout(Request r) throws DataAccessException{
        Response response = new Response();
        DataAccess.getInstance().getAuthAccess().delete(r.getAuthToken());
        return response;
    }
}

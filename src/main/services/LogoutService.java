package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
import models.Response;
import models.Request;
import models.UserData;

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
    public Response logout(Request r){
        Response response = new Response();
        try{
            String username = DataAccess.getInstance().getAuthAccess().find(r.getAuthToken());
            DataAccess.getInstance().getAuthAccess().delete(username);
        }

        catch (DataAccessException e){
            response.setMessage(e.getMessage());
        }
        return response;
    }
}

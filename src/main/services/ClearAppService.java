package services;

import daos.DataAccess;
import models.Response;

/**
 * Service that clears the database of all user, game and authentication info
 */
public class ClearAppService {

    /**
     * Clears the database
     * (Should require authentication...)
     * @return the response message
     */
    public Response clearApplication() {
        Response r = new Response();
        DataAccess.getInstance().getAuthAccess().clear();
        DataAccess.getInstance().getUserAccess().clear();
        DataAccess.getInstance().getGameAccess().clear();
        return r;
    }
}

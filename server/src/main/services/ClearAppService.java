package services;

import daos.DataAccess;
import dataAccess.DataAccessException;
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
    public Response clearApplication(DataAccess dataManager) throws DataAccessException {
        Response r = new Response();
        dataManager.getAuthAccess().clear();
        dataManager.getUserAccess().clear();
        dataManager.getGameAccess().clear();
        return r;
    }
}

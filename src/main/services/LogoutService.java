package services;

import models.AuthToken;

/**
 * Service for logging a user out
 */
public class LogoutService {

    /**
     * Find / authenticate user in the database
     * Remove their authentication
     * @param token - the auth token to be removed
     * @return the response message
     */
    public String logout(AuthToken token){return "";}
}

package daos;

import dataAccess.DataAccessException;
import models.AuthToken;

/**
 * RAM implementation of the authentication token data access
 */
public class AuthDAO implements AuthTokenAccess{
    /**
     * Inserts a new authentication token into the database
     *
     * @param token - the token being inserted
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void insert(AuthToken token) throws DataAccessException {

    }

    /**
     * Find the authentication token associated with a user
     *
     * @param username - the username of the user
     * @return the authentication token
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public AuthToken find(String username) throws DataAccessException {
        return null;
    }
}

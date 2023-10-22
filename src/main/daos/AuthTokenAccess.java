package daos;

import dataAccess.DataAccessException;
import models.AuthToken;

/**
 * A basic structure for a game data access object (can be implemented with a RAM or database class)
 */
public interface AuthTokenAccess {

    /**
     * Inserts a new authentication token into the database
     * @param token - the token being inserted
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    void insert(AuthToken token) throws DataAccessException;

    /**
     * Find the authentication token associated with a user
     * @param username - the username of the user
     * @return the authentication token
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    String find(String username) throws DataAccessException;

    /**
     * clears all tokens from the database
     */
    void clear();
}

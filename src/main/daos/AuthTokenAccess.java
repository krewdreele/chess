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
     * Find the username associated with the token
     * @param token - the token being looked up
     * @return the user
     */
    String find(String token) throws DataAccessException;

    /**
     * clears all tokens from the database
     */
    void clear();

    void delete(String token) throws DataAccessException;
}

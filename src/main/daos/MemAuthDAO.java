package daos;

import dataAccess.DataAccessException;
import models.AuthToken;

import java.util.HashMap;

/**
 * RAM implementation of the authentication token data access
 */
public class MemAuthDAO implements AuthTokenAccess{
    private final HashMap<String, String> tokens;

    public MemAuthDAO(){
        tokens = new HashMap<>();
    }
    /**
     * Inserts a new authentication token into the database
     *
     * @param token - the token being inserted
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void insert(AuthToken token) throws DataAccessException {
        if(token == null){
            throw new DataAccessException("400: bad request");
        }
        tokens.put(token.getUsername(), token.getAuthToken());
    }

    /**
     * Find the authentication token associated with a user
     *
     * @param username - the username of the user
     * @return the authentication token
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public String find(String username) throws DataAccessException {
        String token = tokens.get(username);
        if(token == null){
            throw new DataAccessException("400: bad request");
        }
        return tokens.get(username);
    }

    /**
     * clears all tokens from the database
     */
    @Override
    public void clear() {
        tokens.clear();
    }
}

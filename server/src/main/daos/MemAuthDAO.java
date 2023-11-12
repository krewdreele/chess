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
        tokens.put(token.getAuthToken(), token.getUsername());
    }

    /**
     * Find the user associated with the token
     *
     * @param token - the token looking for
     * @return the user
     */
    @Override
    public String find(String token) throws DataAccessException{
        if(token == null){
            throw new DataAccessException("400: bad request");
        }
        String username = tokens.get(token);
        if(username == null) {
            throw new DataAccessException("401: unauthorized");
        }
        return username;
    }
    /**
     * clears all tokens from the database
     */
    @Override
    public void clear() {
        tokens.clear();
    }

    @Override
    public void delete(String token) throws DataAccessException {
        find(token);
        tokens.remove(token);
    }
}

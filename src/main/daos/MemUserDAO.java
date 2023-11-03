package daos;

import dataAccess.DataAccessException;
import models.UserData;

import java.util.HashMap;

/**
 * RAM implementation for the user data access
 */
public class MemUserDAO implements UserDataAccess{
    private final HashMap<String, UserData> users;

    public MemUserDAO(){
        users = new HashMap<>();
    }
    /**
     * Inserts a new user into the database
     *
     * @param user - the new user
     * @throws DataAccessException - username already taken
     */
    @Override
    public void insert(UserData user) throws DataAccessException {
        if(user == null){
            throw new DataAccessException("400: bad request");
        }
        if(users.get(user.getUsername()) != null){
            throw new DataAccessException("403: already taken");
        }
        users.put(user.getUsername(), user);
    }

    /**
     * Finds the user object associated with the username
     *
     * @param username - the user's username
     * @return the user data object
     * @throws DataAccessException - user cannot be found, not authorized
     */
    @Override
    public String find(String username) throws DataAccessException {
        if(username == null){
            throw new DataAccessException("400: bad request");
        }
        var user = users.get(username);
        if(user == null){
            throw new DataAccessException("401: unauthorized");
        }
        return user.getPassword();
    }

    /**
     * clears all users from the database
     */
    @Override
    public void clear() {
        users.clear();
    }
}

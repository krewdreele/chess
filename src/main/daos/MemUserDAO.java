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
    public UserData find(String username) throws DataAccessException {
        UserData user = users.get(username);
        if(user == null){
            throw new DataAccessException("400: bad request");
        }
        return user;
    }

    /**
     * clears all users from the database
     */
    @Override
    public void clear() {
        users.clear();
    }
}

package daos;

import dataAccess.DataAccessException;
import models.UserData;

/**
 * RAM implementation for the user data access
 */
public class UserDAO implements UserDataAccess{
    /**
     * Inserts a new user into the database
     *
     * @param user - the new user
     * @throws DataAccessException - username already taken
     */
    @Override
    public void insert(UserData user) throws DataAccessException {

    }

    /**
     * Finds the user object associated with the username
     *
     * @param username - the user's username
     * @return the user data object
     * @throws DataAccessException - user cannot be found, not authorized
     */
    @Override
    public UserData findUser(String username) throws DataAccessException {
        return null;
    }
}

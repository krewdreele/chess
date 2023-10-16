package daos;

import dataAccess.DataAccessException;
import models.GameData;
import models.UserData;

import java.util.List;

/**
 * A basic structure for a user data access object (can be implemented with a RAM or database class)
 */
public interface UserDataAccess {

    /**
     * Inserts a new user into the database
     * @param user - the new user
     * @throws DataAccessException - username already taken
     */
    void insert(UserData user) throws DataAccessException;

    /**
     * Finds the user object associated with the username
     * @param username - the user's username
     * @return the user data object
     * @throws DataAccessException - user cannot be found, not authorized
     */
    UserData findUser(String username) throws DataAccessException;

}

package daos;

import dataAccess.DataAccessException;
import dataAccess.Database;
import models.UserData;

import java.sql.SQLException;

public class DbUserDAO implements UserDataAccess{
    private final Database db;
    public DbUserDAO(Database db){
        this.db = db;
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
        if(!find(user.getUsername()).isEmpty()){
            throw new DataAccessException("403: already taken");
        }
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.executeUpdate();

        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }
    }

    /**
     * Finds the password associated with the username
     *
     * @param username - the user's username
     * @return the user's password
     * @throws DataAccessException - user cannot be found
     */
    @Override
    public String find(String username) throws DataAccessException {
        if(username == null){
            throw new DataAccessException("400: bad request");
        }
        var password = "";
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT password FROM user WHERE username=?")) {
            preparedStatement.setString(1, username);

            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    password = rs.getString("password");
                }
            }

        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }
        return password;
    }

    /**
     * clears all users from the database
     */
    @Override
    public void clear() throws DataAccessException{
        var conn = db.getConnection();
        try(var preparedStatement = conn.prepareStatement("TRUNCATE user")){
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally{
            db.returnConnection(conn);
        }
    }
}

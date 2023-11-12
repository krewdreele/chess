package daos;

import dataAccess.DataAccessException;
import dataAccess.Database;
import models.AuthToken;

import java.sql.SQLException;

public class DbAuthDAO implements AuthTokenAccess{
    private final Database db;
    public DbAuthDAO(Database db){
        this.db = db;
    }
    /**
     * Inserts a new authentication token into the database
     *
     * @param token - the token being inserted
     * @throws DataAccessException - bad request, unauthorized, server error
     */
    @Override
    public void insert(AuthToken token) throws DataAccessException
    {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (token, username) VALUES(?, ?)")) {
            preparedStatement.setString(1, token.getAuthToken());
            preparedStatement.setString(2, token.getUsername());

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
     * Find the username associated with the token
     *
     * @param token - the token being looked up
     * @return the user
     */
    @Override
    public String find(String token) throws DataAccessException {
        if(token == null){
            throw new DataAccessException("400: bad request");
        }
        var username = "";
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE token=?")) {
            preparedStatement.setString(1, token);

            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    username = rs.getString("username");
                }
            }
        }
        catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }
        if(username.isEmpty()){
            throw new DataAccessException("401: unauthorized");
        }
        return username;
    }

    /**
     * clears all tokens from the database
     */
    @Override
    public void clear() throws DataAccessException{
        var conn = db.getConnection();
        try(var preparedStatement = conn.prepareStatement("TRUNCATE auth")){
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally{
            db.returnConnection(conn);
        }
    }

    /**
     * Deletes a token
     *
     * @param token the token being deleted
     * @throws DataAccessException when throwing SQL exception
     */
    @Override
    public void delete(String token) throws DataAccessException {
        find(token);
        var conn = db.getConnection();
        try(var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE token=?")){
            preparedStatement.setString(1, token);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        finally {
            db.returnConnection(conn);
        }
    }
}

package daos;

import dataAccess.DataAccessException;
import dataAccess.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccess {
    private final DbUserDAO userDAO;
    private final DbGameDAO gameDAO;
    private final DbAuthDAO authDAO;
    private final Database db;

    public DataAccess() throws DataAccessException {
        db = new Database();
        configureDatabase();
        authDAO = new DbAuthDAO(db);
        userDAO = new DbUserDAO(db);
        gameDAO = new DbGameDAO(db);
    }
    public DbUserDAO getUserAccess() {
        return userDAO;
    }

    public DbGameDAO getGameAccess() {
        return gameDAO;
    }

    public DbAuthDAO getAuthAccess() {
        return authDAO;
    }

    public Database getDatabase() {return db;}

    private void configureDatabase() throws DataAccessException {
        try(var conn = getOriginalConnection()) {
            var createDB = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDB.executeUpdate();
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }

        var createAuthTable =
                """
                    CREATE TABLE IF NOT EXISTS auth (
                        id INT NOT NULL AUTO_INCREMENT,
                        token VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL,
                        PRIMARY KEY(id))
                                                           """;
        executeStatement(createAuthTable);

        var createUserTable =
                """
                    CREATE TABLE IF NOT EXISTS user (
                        id INT NOT NULL AUTO_INCREMENT,
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        PRIMARY KEY(id))
                                                        """;
        executeStatement(createUserTable);

        var createGameTable =
                """
                    CREATE TABLE IF NOT EXISTS game (
                        gameID INT NOT NULL,
                        gameName VARCHAR(255) NOT NULL,
                        whiteUsername VARCHAR(255),
                        blackUsername VARCHAR(255),
                        game TEXT NOT NULL,
                        PRIMARY KEY(gameID))
                                                        """;
        executeStatement(createGameTable);
    }

    Connection getOriginalConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "FCbarca3739");
    }
    private void executeStatement(String statement) throws DataAccessException{
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    }
}

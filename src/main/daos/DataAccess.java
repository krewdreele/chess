package daos;

public class DataAccess {
    private static DataAccess singleton;
    private MemUserDAO userDAO;
    private MemGameDAO gameDAO;
    private MemAuthDAO authDAO;

    public DataAccess(){
        userDAO = new MemUserDAO();
        gameDAO = new MemGameDAO();
        authDAO = new MemAuthDAO();
    }
    public static synchronized DataAccess getInstance(){
        if(singleton == null){
            singleton = new DataAccess();
        }
        return singleton;
    }
    public MemUserDAO getUserAccess() {
        return userDAO;
    }

    public MemGameDAO getGameAccess() {
        return gameDAO;
    }

    public MemAuthDAO getAuthAccess() {
        return authDAO;
    }
}

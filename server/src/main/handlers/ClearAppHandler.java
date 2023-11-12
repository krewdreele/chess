package handlers;

import com.google.gson.Gson;
import daos.DataAccess;
import dataAccess.DataAccessException;
import services.ClearAppService;

/**
 * Handles the http conversion for clearing the database and connects to the corresponding service
 */
public class ClearAppHandler {

    /**
     * Calls the 'clear application' service
     */
    public static String clearApplicationRequest(DataAccess dataManager) throws DataAccessException {
        Gson gson = new Gson();
        ClearAppService service = new ClearAppService();
        models.Response response = service.clearApplication(dataManager);

        return gson.toJson(response);
    }
}

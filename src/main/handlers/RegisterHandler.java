package handlers;
import com.google.gson.Gson;
import daos.DataAccess;
import dataAccess.DataAccessException;
import services.RegisterService;
import spark.Request;

/**
 * handles the http conversion for registering a new user and connects to the corresponding service
 */
public class RegisterHandler {

    /**
     * Convert request to an object the service can use
     * Call the 'register user' service
     * @param request - the http request to be converted
     */
    public static String registerRequest(Request request, DataAccess dataManager) throws DataAccessException {
        Gson gson = new Gson();
        var registerRequest = gson.fromJson(String.valueOf(request.body()), models.Request.class);
        var service = new RegisterService();
        var response = service.register(registerRequest, dataManager);

        return gson.toJson(response);
    }
}

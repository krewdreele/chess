package handlers;

import com.google.gson.Gson;
import services.ClearAppService;
import services.LoginService;

import java.net.http.HttpRequest;

/**
 * Handles the http conversion for clearing the database and connects to the corresponding service
 */
public class ClearAppHandler {

    /**
     * Calls the 'clear application' service
     */
    public static String clearApplicationRequest(){
        Gson gson = new Gson();
        ClearAppService service = new ClearAppService();
        models.Response response = service.clearApplication();

        return gson.toJson(response);
    }
}

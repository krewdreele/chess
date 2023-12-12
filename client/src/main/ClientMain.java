import exception.ResponseException;
import ui.Repl;

public class ClientMain {
    public static void main(String[] args){
        var serverUrl = "http://localhost:8080";
        try{
            new Repl(serverUrl).run();
        }
        catch (Exception e){
            System.out.println("Could not connect to server: " + e.getMessage());
        }
    }
}

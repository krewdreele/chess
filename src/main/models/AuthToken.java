package models;

/**
 * Holds the authorization token for specified user
 * Holds the response message (if necessary)
 */
public class AuthToken {
    private String message;
    private String authToken;
    private String username;

    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
        this.message = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package models;

/**
 * Holds the authorization token for specified user
 * Holds the response message (if necessary)
 */
public class AuthToken {
    private String authToken;
    private String username;

    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

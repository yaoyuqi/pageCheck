package Ywk.Api;

public class LoginHeader {

    private static LoginHeader instance;

    private String headerMark;

    private String accessToken;

    private String identity;

    private LoginHeader(String mark, String accessToken) {
        this.accessToken = accessToken;
        this.headerMark = mark;
    }

    public static LoginHeader getInstance() {
        if (instance == null) {
            instance = getInstance("");
        }
        return instance;
    }

    public static LoginHeader getInstance(String token) {
        if (instance == null) {
            instance = new LoginHeader("Authorization", token);
        } else {
            instance.accessToken = token;
        }
        return instance;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getHeaderMark() {
        return headerMark;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

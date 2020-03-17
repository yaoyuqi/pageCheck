package Ywk.Api;

class LoginHeader {

    private static LoginHeader instance;

    private String headerMark;

    private String accessToken;

    private LoginHeader(String mark, String accessToken) {
        this.accessToken = accessToken;
        this.headerMark = mark;
    }

    static LoginHeader getInstance() {
        if (instance == null) {
            instance = getInstance("");
        }
        return instance;
    }

    static LoginHeader getInstance(String token) {
        if (instance == null) {
            instance = new LoginHeader("Authorization", token);
        } else {
            instance.accessToken = token;
        }
        return instance;
    }


    String getHeaderMark() {
        return headerMark;
    }

    String getAccessToken() {
        return accessToken;
    }
}

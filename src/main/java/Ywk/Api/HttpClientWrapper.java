package Ywk.Api;

import okhttp3.OkHttpClient;

public class HttpClientWrapper {
    private static final OkHttpClient client = (new OkHttpClient.Builder()).build();

    public static OkHttpClient getClient() {
        return client;
    }
}

package Ywk.Data;

import okhttp3.OkHttpClient;

public class ApiWriter extends XMLWriter {
    private OkHttpClient client;

    public ApiWriter(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public synchronized void flush(int type) {


    }
}

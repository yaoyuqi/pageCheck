package Ywk.Client.Interceptor;

import okhttp3.Request;

import java.net.URI;

public interface PageEncrypt {
    void parse(String content);

    Request process(Request origin);

    boolean canHandle(URI url);
}

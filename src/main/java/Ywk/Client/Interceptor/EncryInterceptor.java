package Ywk.Client.Interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EncryInterceptor implements Interceptor {

    private static EncryInterceptor instance;
    private List<PageEncrypt> list = Collections.singletonList(new BaiduInterceptor());

    private EncryInterceptor() {
    }

    public static EncryInterceptor getInstance() {
        if (instance == null) {
            instance = new EncryInterceptor();
        }
        return instance;
    }

    public List<PageEncrypt> getList() {
        return list;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request newRequest;

        Optional<PageEncrypt> encrypt = list.stream().filter(pageEncrypt -> pageEncrypt.canHandle(originalRequest.url().uri()))
                .findAny();


        if (encrypt.isPresent()) {
            newRequest = encrypt.get().process(originalRequest);
        } else {
            newRequest = originalRequest;
        }

        return chain.proceed(newRequest);

    }
}

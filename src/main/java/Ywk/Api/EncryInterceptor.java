package Ywk.Api;

import Ywk.PageCheck.Capture.AdapterWrapper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EncryInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String cookie = originalRequest.header("Cookie");

        if (cookie != null) {
            String key = AdapterWrapper.getInstance().key();

//
//            System.out.println("********************");
//            System.out.println(key);
//            System.out.println("oldCookie********************");
//            System.out.println(cookie);
            if (!key.isEmpty()) {
                List<String> cookies = Arrays.stream(cookie.split(";"))
                        .filter(item -> !item.contains("H_PS_645EC"))
                        .collect(Collectors.toList());

                cookies.add(" H_PS_645EC=" + URLEncoder.encode(key, "UTF-8"));
//                cookies.add(" H_PS_645EC=" + key);


                Request encrypedRequest = originalRequest.newBuilder()
                        .header("Cookie", String.join(";", cookies))
                        .build();

                return chain.proceed(encrypedRequest);
//                if (cookie.contains("H_PS_645EC")) {
//                    String newCookie = "";
//                    Pattern pattern = Pattern.compile("H_PS_645EC=\\s*(\\S+)\\s*;");
//                    Matcher matcher = pattern.matcher(cookie);
//                    while (matcher.find()) {
//                        newCookie = cookie.replace(matcher.group(1), key);
//                    }
//
//                    if (newCookie.isEmpty()) {
//                        newCookie = cookie;
//                    }
//
//
//                    System.out.println("newCookie********************");
//                    System.out.println(newCookie);
//                    Request encrypedRequest = originalRequest.newBuilder()
//                            .header("Cookie", newCookie)
//                            .build();
//                    return chain.proceed(encrypedRequest);
//                }
//                else {
//                    Request encrypedRequest = originalRequest.newBuilder()
//                            .header("Cookie", cookie + "; H_PS_645EC=" + key)
//                            .build();
//
//                    System.out.println("newCookie********************");
//                    System.out.println(cookie + "; H_PS_645EC=" + key);
//                    return chain.proceed(encrypedRequest);
//                }
            }

        }

        return chain.proceed(originalRequest);

//        Request request = chain.request();
//        Response response = chain.proceed(request);
    }
}

package Ywk.Client;

import Ywk.Client.Interceptor.EncryInterceptor;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HttpClientWrapper {
    private static final OkHttpClient client;

    static {
        ConcurrentHashMap<String, List<Cookie>> cookies = CookiesStore.getCookieStore();
        client = (new OkHttpClient.Builder())
//                .readTimeout(30, TimeUnit.SECONDS)
//                .proxySelector(new MyProxySelector())
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
//                        System.out.println("save To+++++++++++++++");
//
////                        List<Cookie> originals = cookies.get(httpUrl.host());
////                        if (originals != null && !originals.isEmpty()) {
////                            Optional<Cookie> bd = list.stream().filter(item->item.name().equals("H_PS_645EC")).findAny();
////                            if (bd.isPresent()) {
////                                list.add(bd.get());
////                            }
////
////                        }
//
//                        cookies.put(httpUrl.host(), list);
//                        System.out.println(list.toString());
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
//                        List<Cookie> list = cookies.get(httpUrl.host());
//                        System.out.println("load from --------------");
//                        System.out.println(list == null ? "" : list.toString());
//
//                        return list != null ? list : new ArrayList<>();
//                    }
//                })
                .addInterceptor(EncryInterceptor.getInstance())
                .build();


    }

    public static OkHttpClient getClient() {
        return client;
    }


}

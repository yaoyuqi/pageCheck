package Ywk.Api;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HttpClientWrapper {
    private static final OkHttpClient client;

    static {
        ConcurrentHashMap<String, List<Cookie>> cookies = CookiesStore.getCookieStore();
        client = (new OkHttpClient.Builder())
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        System.out.println("save To+++++++++++++++");

//                        List<Cookie> originals = cookies.get(httpUrl.host());
//                        if (originals != null && !originals.isEmpty()) {
//                            Optional<Cookie> bd = list.stream().filter(item->item.name().equals("H_PS_645EC")).findAny();
//                            if (bd.isPresent()) {
//                                list.add(bd.get());
//                            }
//
//                        }

                        cookies.put(httpUrl.host(), list);
                        System.out.println(list.toString());
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> list = cookies.get(httpUrl.host());
                        System.out.println("load from --------------");
                        System.out.println(list == null ? "" : list.toString());

                        return list != null ? list : new ArrayList<>();
                    }
                })
                .addInterceptor(new EncryInterceptor())
                .build();


    }

    public static OkHttpClient getClient() {
        return client;
    }


}

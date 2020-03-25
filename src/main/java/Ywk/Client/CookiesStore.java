package Ywk.Client;

import okhttp3.Cookie;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CookiesStore {

    private static final ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, List<Cookie>> getCookieStore() {
        return cookieStore;
    }


}


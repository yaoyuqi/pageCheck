package Ywk.Data;

//public enum SearchPlatform {
//    BAIDU(1, "百度PC", "https://www.baidu.com/s?ie=utf-8&wd=", "div.result.c-container", false),
//    BAIDU_MOBILE(2, "百度移动", "https://m.baidu.com/s?ie=utf-8&wd=", "#results div.result.c-result", true),
//    SHENMA(3, "神马搜索", "https://m.sm.cn/s?q=", "div.sc.c-container", true),
//    TOUTIAO(4, "头条搜索", "https://m.toutiao.com/search?keyword=", "#results.result-content", true);
//
//    private int id;
//    private String name;
//    private String url;
//    private String pattern;
//    private boolean isMobile;
//
//    SearchPlatform(int id, String name, String url, String pattern, boolean isMobile) {
//
//        this.id = id;
//        this.name = name;
//        this.url = url;
//        this.pattern = pattern;
//        this.isMobile = isMobile;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public String getPattern() {
//        return pattern;
//    }
//
//    public boolean isMobile() {
//        return isMobile;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String nextPageUrl(String key, int page) {
//        if (page > 1) {
//            if (id == 3) {
//                return url + key + "&page=" + page;
//            }
//            else if (id == 4) {
//                return url + key + "&offset=" + (page - 1) * 10 + "&start_index" + (page - 1) * 10;
//            }
//            else {
//                return url + key + "&p=" + page;
//            }
//        }
//        return this.url + key;
//    }
//}

import java.util.List;

public class SearchPlatform {
    private static final String keyPattern = "[#keyword]";
    private int id;
    private String name;
    private List<String> urls;
    private String pattern;
    private boolean isMobile;

    SearchPlatform(int id, String name, List<String> url, String pattern, boolean isMobile) {

        this.id = id;
        this.name = name;
        this.urls = url;
        this.pattern = pattern;
        this.isMobile = isMobile;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public int getId() {
        return id;
    }

    public String nextPageUrl(String key, int page) {
        if (urls.size() < page) {
            return "";
        }

        String url = urls.get(page);
        return url.replace(keyPattern, key);
    }

}
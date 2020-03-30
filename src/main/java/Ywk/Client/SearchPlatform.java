package Ywk.Client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class SearchPlatform {
    private static final String keyPattern = "[#keyword]";
    private int id;
    private String name;
    private List<String> urls;
    private List<String> browseUrls;
    private String pattern;
    private boolean isMobile;
    private String host;

    public SearchPlatform(int id, String name, List<String> url, List<String> browses, String pattern, boolean isMobile) throws URISyntaxException {

        this.id = id;
        this.name = name;
        this.urls = url;
        this.pattern = pattern;
        this.isMobile = isMobile;
        this.browseUrls = browses;
        URI uri = new URI(urls.get(0));

        this.host = uri.getHost();

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
        int realPage = page - 1;
        if (urls.size() < realPage) {
            return "";
        }

        String url = urls.get(realPage);
        return url.replace(keyPattern, key);
    }

    public String nextPageUrlBrowse(String key, int page) {
        int realPage = page - 1;
        if (browseUrls.size() < realPage) {
            return "";
        }

        String url = browseUrls.get(realPage);
        return url.replace(keyPattern, key);
    }

    public boolean isSameHost(String url) throws URISyntaxException {
        URI uri = new URI(url);

        return uri.getHost().equals(host);

    }

}
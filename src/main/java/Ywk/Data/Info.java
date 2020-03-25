package Ywk.Data;


import Ywk.Client.SearchPlatform;

public class Info {
    private String keyword;
    private SearchPlatform platform;
    private String identity;
    private String[] loc;
    private String time;

    private int page = 1;

    public int total() {
        return loc == null ? 0 : loc.length;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public SearchPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(SearchPlatform platform) {
        this.platform = platform;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String[] getLoc() {
        return loc;
    }

    public void setLoc(String[] loc) {
        this.loc = loc;
    }

    public String getLocString() {
        return String.join(",", loc);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

package Ywk.Data;


public class Info {
    public static int TYPE_PC = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_BOTH = 3;

    private String keyword;
    private int type;

    private int total = 0;

    //    private String url;
    private String[] loc;
    //    private String searchUrl;
    private String time;

    private int page = 1;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

//    public String getUrl() {
//        return url;
//    }


    public String[] getLoc() {
        return loc;
    }

    public void setLoc(String[] loc) {
        this.loc = loc;
        total = loc.length;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

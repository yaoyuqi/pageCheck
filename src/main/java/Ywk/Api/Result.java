package Ywk.Api;

import Ywk.Data.Info;
import Ywk.Data.SearchPlatform;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String check_time;
    private String mark;
    private int part;
    private int total;
    private List<Data> data;

    public Result(List<Info> infoList, int part, String mark, String checkTime) {
        this.check_time = checkTime;
        this.mark = mark;
        this.part = part;
        this.data = new ArrayList<>();

        for (Info info :
                infoList) {
            Data item = new Data();
            item.setLoc(info.getLocString());
            item.setTime(info.getTime());
            item.setWord(info.getKeyword());
            item.setPage(info.getPage());
            item.setPlatform(info.getPlatform());
            data.add(item);
        }
        this.total = this.data.size();
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String loc;
        private String word;
        private String time;
        private SearchPlatform platform;
        private int page;

        public SearchPlatform getPlatform() {
            return platform;
        }

        public void setPlatform(SearchPlatform platform) {
            this.platform = platform;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

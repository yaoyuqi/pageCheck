package Ywk.Api;

import Ywk.Data.Info;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String mark;
    private int part;
    private List<Data> data;

    public Result(List<Info> infoList, int part, String mark) {
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
            item.setProduct(info.getIdentity());
            item.setSearch_engine_id(info.getPlatform().getId());
            data.add(item);
        }
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
        private String product;
        private int search_engine_id;
        private int page;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public int getSearch_engine_id() {
            return search_engine_id;
        }

        public void setSearch_engine_id(int search_engine_id) {
            this.search_engine_id = search_engine_id;
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

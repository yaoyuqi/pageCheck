package Ywk.UserInterface.Model;

import Ywk.Data.Info;
import Ywk.PageCheck.BaiduCapture;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InfoModel {

    private final StringProperty keyword;
    private final StringProperty loc;
    private final StringProperty time;
    private final int type;

    private final int page;

    public InfoModel(Info info) {
        keyword = new SimpleStringProperty(info.getKeyword());
        loc = new SimpleStringProperty(String.join(",", info.getLoc()));
        time = new SimpleStringProperty(info.getTime());
        type = info.getType();
        page = info.getPage();
    }

    public String getUrl() {
        return BaiduCapture.makeUrl(type, getKeyword(), page);
    }

    public String getKeyword() {
        return keyword.get();
    }

    public StringProperty keywordProperty() {
        return keyword;
    }

    public String getLoc() {
        return loc.get();
    }

    public StringProperty locProperty() {
        return loc;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public int getPage() {
        return page;
    }

    public StringProperty pageProperty() {
        String mark = "";
        if (page == 1) {
            mark = "首页";
        } else {
            mark = "第" + page + "页";
        }
        return new SimpleStringProperty(mark);
    }
}

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

    public InfoModel(Info info) {
        keyword = new SimpleStringProperty(info.getKeyword());
        loc = new SimpleStringProperty(String.join(",", info.getLoc()));
        time = new SimpleStringProperty(info.getTime());
        type = info.getType();
    }

    public String getUrl() {
        return BaiduCapture.makeUrl(type, getKeyword());
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
}

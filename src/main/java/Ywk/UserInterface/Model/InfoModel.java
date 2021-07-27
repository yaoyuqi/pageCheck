package Ywk.UserInterface.Model;

import Ywk.Data.IdentityWrapper;
import Ywk.Data.Info;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InfoModel {
    private final StringProperty keyword;
    private final StringProperty loc;
    private final StringProperty time;
    private final StringProperty product;
    private final Info info;

    public InfoModel(Info info) {
        keyword = new SimpleStringProperty(info.getKeyword());
        loc = new SimpleStringProperty(String.join(",", info.getLoc()));
        time = new SimpleStringProperty(info.getTime());
        product = new SimpleStringProperty(IdentityWrapper.getInstance().name(info.getIdentity()));
        this.info = info;
    }

    public Info getInfo() {
        return info;
    }

    public String getUrl() {
        return info.getPlatform().nextPageUrl(getKeyword(), info.getPage());
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
        return info.getPage();
    }

    public StringProperty pageProperty() {
        String mark;
        if (info.getPage() == 1) {
            mark = "首页";
        } else {
            mark = "第" + info.getPage() + "页";
        }
        return new SimpleStringProperty(mark);
    }

    public StringProperty productProperty() {
        return product;
    }

    public String getProduct() {
        return product.get();
    }
}

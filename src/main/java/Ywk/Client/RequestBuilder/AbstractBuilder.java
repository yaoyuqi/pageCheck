package Ywk.Client.RequestBuilder;

import Ywk.Client.SearchPlatform;
import okhttp3.Request;

import java.net.URISyntaxException;

public abstract class AbstractBuilder {
    private SearchPlatform platform;

    public SearchPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(SearchPlatform platform) {
        this.platform = platform;
    }

    public abstract String host();

    public abstract Request build(String url);

    public boolean canBuild(String url) {
        try {
            return platform.isSameHost(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
}

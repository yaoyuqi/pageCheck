package Ywk.Client.RequestBuilder;

import Ywk.Client.SearchPlatform;
import okhttp3.Request;

public interface Builder {
    Request build(String url);

    String host();

    void setPlatform(SearchPlatform platform);

    boolean canBuild(String url);
}

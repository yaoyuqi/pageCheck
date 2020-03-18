package Ywk.Data;

import Ywk.Api.ApiInstance;
import Ywk.Api.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public class PlatformWrapper implements ApiInstance {

    private static PlatformWrapper instance;
    private ApiStatus apiStatus = ApiStatus.WAITING;
    private List<SearchPlatform> list = new ArrayList<>();

    private int pageDepthMax = 0;

    private PlatformWrapper() {
    }

    public static PlatformWrapper getInstance() {
        if (instance == null) {
            instance = new PlatformWrapper();
        }
        return instance;
    }

    public void init(List<SearchPlatform> list, int pageDepthMax) {
        this.list = list;
        this.pageDepthMax = pageDepthMax;
        apiStatus = ApiStatus.SUCESS;
    }

    public int getPageDepthMax() {
        return pageDepthMax;
    }

    @Override
    public ApiStatus inited() {
        return apiStatus;
    }

    @Override
    public void initFailed() {
        apiStatus = ApiStatus.FAILED;
    }
}

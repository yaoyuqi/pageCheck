package Ywk.Client.Proxy;

import Ywk.Api.HltApi;

import java.util.TimerTask;

public class ProxyFetchTask extends TimerTask {

    private ProxyPool pool = ProxyPool.getInstance();


    @Override
    public void run() {
        HltApi api = HltApi.getInstance();
        api.proxy();

    }
}

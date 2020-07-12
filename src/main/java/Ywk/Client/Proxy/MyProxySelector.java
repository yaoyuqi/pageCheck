package Ywk.Client.Proxy;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyProxySelector extends ProxySelector {
    @Override
    public List<Proxy> select(URI uri) {
        if (!uri.getHost().contains("www.baidu.com")
                && !uri.getHost().contains("m.sm.cn")
                && !uri.getHost().contains("m.toutiao.com")
                && !uri.getHost().contains("www.sogou.com")
                && !uri.getHost().contains("m.sogou.com")
                && !uri.getHost().contains("www.so.com")
                && !uri.getHost().contains("m.so.com")
        ) {
            return Collections.singletonList(Proxy.NO_PROXY);
        } else {
            ProxyPool pool = ProxyPool.getInstance();
            if (pool.getProxies().isEmpty()) {
                return Collections.singletonList(Proxy.NO_PROXY);
            } else {

                Random rand = new Random();
                int size = pool.getProxies().size();
                if (size > 3) {
                    List<Proxy> proxies = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        int randomIndex = rand.nextInt(size);
                        proxies.add(pool.getProxies().get(randomIndex));
                    }
                    return proxies;
                } else {
                    return pool.getProxies();
                }
            }
        }
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

    }
}

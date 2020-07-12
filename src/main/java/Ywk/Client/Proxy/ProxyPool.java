package Ywk.Client.Proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProxyPool {
    private static ProxyPool instance;
    private final List<Proxy> pools = Collections.synchronizedList(new ArrayList<>());

    private ProxyPool() {
    }

    public static ProxyPool getInstance() {
        if (instance == null) {
            instance = new ProxyPool();
        }
        return instance;
    }

    public List<Proxy> getProxies() {
        return pools;
    }

    public void freshProxy(List<String> ips) {
        pools.clear();
        ips.forEach(str -> {
            String[] info = str.split(":");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(info[0], Integer.parseInt(info[1])));
            pools.add(proxy);
        });
    }

}

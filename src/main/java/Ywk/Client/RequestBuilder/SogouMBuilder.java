package Ywk.Client.RequestBuilder;

import Ywk.Client.UserAgents;
import okhttp3.Request;

public class SogouMBuilder extends AbstractBuilder {
    @Override
    public String host() {
        return "https://m.sogou.com";
    }

    @Override
    public Request build(String url) {
        return new Request.Builder()
                .get()
                .url(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
//                .header("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .header("User-Agent", UserAgents.randomUserAgent(true))
                .build();


    }
}

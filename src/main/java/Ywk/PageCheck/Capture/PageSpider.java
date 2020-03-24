package Ywk.PageCheck.Capture;

import Ywk.Data.SearchPlatform;
import Ywk.PageCheck.ContentChecker;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Build request client
 */
public class PageSpider {

    private OkHttpClient client;
    private ContentChecker checker;
    private PageChecked listener;

    private static final String[] pcUserAgents = {
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 OPR/26.0.1656.60",
            "Opera/8.0 (Windows NT 5.1; U; en)",
            "Mozilla/5.0 (Windows NT 5.1; U; en; rv:1.8.1) Gecko/20061208 Firefox/2.0.0 Opera 9.50",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; en) Opera 9.50",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0",
            "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER) ",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.3.4000 Chrome/30.0.1599.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 UBrowser/4.0.3214.0 Safari/537.36",
    };

    private static final String[] mobileUserAgents = {
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
            "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; HTC_Wildfire_A3333 Build/FRG83D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/build-1107180945; U; en-GB) Presto/2.8.149 Version/11.10",
            "Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; en) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.337 Mobile Safari/534.1+",
            "Mozilla/5.0 (hp-tablet; Linux; hpwOS/3.0.0; U; en-US) AppleWebKit/534.6 (KHTML, like Gecko) wOSBrowser/233.70 Safari/534.6 TouchPad/1.0",
            "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; HTC; Titan)",
    };

    public PageSpider(OkHttpClient client, ContentChecker checker) {
        this.client = client;
        this.checker = checker;
    }

    //    public PageSpider(int maxPerHost, int maxRequest) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        client = builder.build();
//        Dispatcher dispatcher = client.dispatcher();
//        dispatcher.setMaxRequests(maxRequest);
//        dispatcher.setMaxRequestsPerHost(maxPerHost);
//    }

    void setPageCheckedListener(PageChecked listener) {
        this.listener = listener;
    }

    void setIdleCallback(Runnable callback) {
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setIdleCallback(callback);
    }


    void run(String url, String keyword, int page) {
        if (url == null || url.isEmpty() || keyword == null || keyword.isEmpty()) {
            return;
        }

        Request request;
        if (checker.getPlatform().isMobile()) {
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")

//                    .header("Cookie", "BAIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; MSA_WH=375_667; BD_UPN=123253; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; H_WISE_SIDS=142080_142058_142113_100806_139964_143377_142209_143860_143879_141876_139057_141747_143161_143423_143448_141899_142780_131247_137746_138165_138883_141941_127969_142871_140066_142908_140593_143059_143491_140350_138425_141009_143469_143276_141930_131423_125695_107316_138595_143093_142803_143477_142427_142911_136753_110085; H_PS_PSSID=30975_1443_31117_21091_30903_30823_31086_22157; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=1; COOKIE_SESSION=4_0_9_9_0_0_0_0_9_0_0_0_0_0_0_0_0_0_1584687340%7C9%238805509_15_1582089706%7C6; H_PS_645EC=312cJsJK2%2BOxKryoApolU78xsFsv2Suf0nr%2B%2BO6wZCoHU%2Fd%2B6LSlmhgo%2FrI")
                    .header("User-Agent:", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
//                    .addHeader("User-Agent:", randomUserAgent(true))
                    .build();
        } else {
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .header("Cookie", "BAIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; MSA_WH=375_667; BD_UPN=123253; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; H_WISE_SIDS=142080_142058_142113_100806_139964_143377_142209_143860_143879_141876_139057_141747_143161_143423_143448_141899_142780_131247_137746_138165_138883_141941_127969_142871_140066_142908_140593_143059_143491_140350_138425_141009_143469_143276_141930_131423_125695_107316_138595_143093_142803_143477_142427_142911_136753_110085; H_PS_PSSID=30975_1443_31117_21091_30903_30823_31086_22157; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=1; H_PS_645EC=25c2xT4EdDqVS9weTiheh3jDLv33l9FYqfjdFTLm2UqJ6%2BfOcOju7qYHT24; COOKIE_SESSION=5_0_9_9_0_3_0_1_9_1_2_2_0_0_0_0_0_0_1584687345%7C9%238805509_15_1582089706%7C6")
//                    .header("Cookie", "BAIDUID=935CCBCC6795725A58C6492405276B46:FG=1; BIDUPSID=08AE00A3E1D3AF68FA6146FA5C584239; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; PSTM=1584691632; delPer=0; BD_CK_SAM=1; PSINO=6; H_PS_PSSID=30971_1425_31044_21100_30823_26350_30717; BD_UPN=12314353;  BDSVRTM=101;H_PS_645EC=37703IRlbHLUx/vx4tjWQXuYIICzB9xdiHjXFWsfQ9c6UVY1aUl4VW5IXyE")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
                    .header("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
//                    .addHeader("User-Agent:", randomUserAgent(false))
                    .build();

        }

//        System.out.println("Search[" + url + "]");
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                listener.updateRunningInfo();
//                checker.checkFail(keyword, url);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                checker.check(response.body().string(), keyword, page, url);
//                listener.updateRunningInfo();
//                response.close();
//            }
//        });
        try (Response response = client.newCall(request).execute()) {

            System.out.println("Request cookie+++++++++++++++");
            System.out.println(response.request().headers().get("Cookie"));
//
//            System.out.println("Response set-cookie+++++++++++++++");
//            System.out.println(response.headers().toString());
            checker.check(response.body().string(), keyword, page, url);
                listener.updateRunningInfo();
        } catch (IOException | URISyntaxException e) {
            listener.updateRunningInfo();
            checker.checkFail(keyword, url);
        }

    }

    public SearchPlatform getPlatform() {
        return checker.getPlatform();
    }


    void stopAll() {
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.cancelAll();
    }

    void setSpeed(int speed) {
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setMaxRequestsPerHost(speed);
        dispatcher.setMaxRequests(speed);
    }

    private String randomUserAgent(boolean isMobile) {
        if (isMobile) {
            return mobileUserAgents[(int) (Math.random() * mobileUserAgents.length)];
        } else {
            return pcUserAgents[(int) (Math.random() * pcUserAgents.length)];

        }
    }
    /**
     * Listener for each page checked
     */
    public interface PageChecked {
        void updateRunningInfo();
    }

}

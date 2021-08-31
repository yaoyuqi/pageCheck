package Ywk.PageCheck;


import Ywk.Client.Interceptor.PageEncrypt;
import Ywk.Client.RequestBuilder.RequestBuilder;
import Ywk.Client.SearchPlatform;
import okhttp3.*;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;

import java.io.IOException;
import java.util.List;


/**
 * Build request client
 */
public class PageSpider {

    private OkHttpClient client;
    private Checker checker;
    private PageChecked listener;
    private RequestBuilder builder;
    private List<PageEncrypt> encrypts;


    public PageSpider(OkHttpClient client,
                      Checker checker,
                      RequestBuilder builder,
                      List<PageEncrypt> encrypts
    ) {
        this.client = client;
        this.checker = checker;
        this.builder = builder;
        this.encrypts = encrypts;
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

//        Request request;
//        if (checker.getPlatform().isMobile()) {
//            request = new Request.Builder()
//                    .get()
//                    .url(url)
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
//
////                    .header("Cookie", "BAIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; MSA_WH=375_667; BD_UPN=123253; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; H_WISE_SIDS=142080_142058_142113_100806_139964_143377_142209_143860_143879_141876_139057_141747_143161_143423_143448_141899_142780_131247_137746_138165_138883_141941_127969_142871_140066_142908_140593_143059_143491_140350_138425_141009_143469_143276_141930_131423_125695_107316_138595_143093_142803_143477_142427_142911_136753_110085; H_PS_PSSID=30975_1443_31117_21091_30903_30823_31086_22157; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=1; COOKIE_SESSION=4_0_9_9_0_0_0_0_9_0_0_0_0_0_0_0_0_0_1584687340%7C9%238805509_15_1582089706%7C6; H_PS_645EC=312cJsJK2%2BOxKryoApolU78xsFsv2Suf0nr%2B%2BO6wZCoHU%2Fd%2B6LSlmhgo%2FrI")
//                    .header("User-Agent:", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
////                    .addHeader("User-Agent:", randomUserAgent(true))
//                    .build();
//        } else {
//            request = new Request.Builder()
//                    .get()
//                    .url(url)
//                    .header("Cookie", "BAIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; MSA_WH=375_667; BD_UPN=123253; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; H_WISE_SIDS=142080_142058_142113_100806_139964_143377_142209_143860_143879_141876_139057_141747_143161_143423_143448_141899_142780_131247_137746_138165_138883_141941_127969_142871_140066_142908_140593_143059_143491_140350_138425_141009_143469_143276_141930_131423_125695_107316_138595_143093_142803_143477_142427_142911_136753_110085; H_PS_PSSID=30975_1443_31117_21091_30903_30823_31086_22157; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=1; H_PS_645EC=25c2xT4EdDqVS9weTiheh3jDLv33l9FYqfjdFTLm2UqJ6%2BfOcOju7qYHT24; COOKIE_SESSION=5_0_9_9_0_3_0_1_9_1_2_2_0_0_0_0_0_0_1584687345%7C9%238805509_15_1582089706%7C6")
////                    .header("Cookie", "BAIDUID=935CCBCC6795725A58C6492405276B46:FG=1; BIDUPSID=08AE00A3E1D3AF68FA6146FA5C584239; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; PSTM=1584691632; delPer=0; BD_CK_SAM=1; PSINO=6; H_PS_PSSID=30971_1425_31044_21100_30823_26350_30717; BD_UPN=12314353;  BDSVRTM=101;H_PS_645EC=37703IRlbHLUx/vx4tjWQXuYIICzB9xdiHjXFWsfQ9c6UVY1aUl4VW5IXyE")
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
////                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
//                    .header("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
////                    .addHeader("User-Agent:", randomUserAgent(false))
//                    .build();
//
//        }
        String urlNew;
        if (url.contains("baidu")) {
            urlNew = url + "&rsv_spt=1&rsv_iqid=0xd3486227000e553c&issp=1&f=3&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_dl=ts_0&rsv_sug3=4&rsv_sug1=2&rsv_sug7=100&rsv_sug2=1&rsv_btype=i&prefixsug=dsf&rsp=0&inputT=1930&rsv_sug4=2608&rsv_jmp=fail";
        } else {
            urlNew = url;
        }

        System.out.println("Search[" + urlNew + "]");
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

        Request request = builder.build(urlNew);
        if (request != null) {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.updateRunningInfo();
                    checker.checkFail(keyword, urlNew);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        var tResponse = unzip(response);
                        String content = tResponse.body().string();
//                    System.out.println("contentType is " + tResponse.header("Content-Type"));
//                    System.out.println(content);
                        encrypts.stream().filter(pageEncrypt -> pageEncrypt.canHandle(tResponse.request().url().uri()))
                                .forEach(pageEncrypt -> pageEncrypt.parse(content));

                        checker.check(content, keyword, page);
                        listener.updateRunningInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
//            try (Response response = client.newCall(request).execute()) {
//
//                System.out.println("Request cookie+++++++++++++++");
//                System.out.println(response.request().headers().get("Cookie"));
////
////            System.out.println("Response set-cookie+++++++++++++++");
////            System.out.println(response.headers().toString());
//                String content = response.body().string();
//                checker.check(content, keyword, page, url);
//                listener.updateRunningInfo();
//            } catch (IOException | URISyntaxException e) {
//                listener.updateRunningInfo();
//                checker.checkFail(keyword, url);
//            }
        }


    }

    private Response unzip(final Response response) {
        var contentEncoding = response.headers().get("Content-Encoding");
        if (contentEncoding != null && contentEncoding.equals("gzip")) {
            var contentLength = response.body().contentLength();
            var responseBody = new GzipSource(response.body().source());
            var strippedHeaders = response.headers().newBuilder().build();
            return response.newBuilder().headers(strippedHeaders)
                    .body(new RealResponseBody(response.body().contentType().toString(), contentLength, Okio.buffer(responseBody)))
                    .build();
        }
        return response;
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


    /**
     * Listener for each page checked
     */
    public interface PageChecked {
        void updateRunningInfo();
    }

}

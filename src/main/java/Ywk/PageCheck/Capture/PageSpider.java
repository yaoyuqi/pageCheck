package Ywk.PageCheck.Capture;

import Ywk.Data.SearchPlatform;
import Ywk.PageCheck.ContentChecker;
import okhttp3.*;

import java.io.IOException;


/**
 * Build request client
 */
public class PageSpider {

    private OkHttpClient client;
    private ContentChecker checker;
    private PageChecked listener;

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
                    .addHeader("User-Agent:", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                    .build();
        } else {
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .addHeader("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                    .build();

        }

//        System.out.println("Search[" + url + "]");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.updateRunningInfo();
                checker.checkFail(keyword, url);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                checker.check(response.body().string(), keyword, page);
                listener.updateRunningInfo();
                response.close();
            }
        });

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

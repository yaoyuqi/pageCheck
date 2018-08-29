package Ywk.PageCheck;

import okhttp3.*;

import java.io.IOException;

public class PageCapture {

    private OkHttpClient client;

    private BaiduChecker checker;

    private RunningUpdateListener listener;

    public PageCapture(int maxPerHost, int maxRequest) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        client = builder.build();
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setMaxRequests(maxRequest);
        dispatcher.setMaxRequestsPerHost(maxPerHost);
    }

    public PageCapture(OkHttpClient client) {
        this.client = client;
    }

    public void setListener(RunningUpdateListener listener) {
        this.listener = listener;
    }

    public void setIdleCallback(Runnable callback) {
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setIdleCallback(callback);
    }

    public void setChecker(BaiduChecker checker) {
        this.checker = checker;
    }


    public void pc(String url, String keyword, int page) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                listener.updateRunningInfo();
                checker.checkPcFail(keyword);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                checker.checkPC(response.body().string(), keyword, page);
                listener.updateRunningInfo();
                response.close();
            }
        });


//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                checker.checkPC(response.body().string(), keyword, url);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    public void mobile(String url, String keyword, int page) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("User-Agent:", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                checker.checkMobileFail(keyword);
                listener.updateRunningInfo();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                checker.checkMobile(response.body().string(), keyword, page);
                listener.updateRunningInfo();
                response.close();

            }
        });
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                checker.checkMobile(response.body().string(), keyword, url);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void stopAll() {
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.cancelAll();
    }

    public void setSpeed(int speed) {
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setMaxRequestsPerHost(speed);
        dispatcher.setMaxRequests(speed);
    }

    public interface RunningUpdateListener {
        public void updateRunningInfo();
    }

}

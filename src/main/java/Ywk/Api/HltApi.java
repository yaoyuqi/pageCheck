package Ywk.Api;

import Ywk.Client.PlatformWrapper;
import Ywk.Client.Proxy.ProxyPool;
import Ywk.Client.SearchPlatform;
import Ywk.Data.*;
import Ywk.UserInterface.Controller.LoginController;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors;

public class HltApi {

    private static int currentVersion = 3;
    private static final String key = "1typhdp9zbo2Lbz9YkdfTLE9Tm4jW7nCSKssakFj";
    private static final String host = "http://member.91huoke.com/";
    private static HltApi api;
    private OkHttpClient client;

    private HltApi(OkHttpClient client) {
        this.client = client;
    }

    public static HltApi getInstance(OkHttpClient client) {
        if (api == null) {
            api = new HltApi(client);
        }
        return api;
    }

    public static HltApi getInstance() {
        return HltApi.getInstance(new OkHttpClient.Builder().build());
    }

    private Callback loginCallback(LoginController controller) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                controller.loginResult(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String content = response.body().string();

                    TokenData loginOut = new Gson().fromJson(content, TokenData.class);
                    if (loginOut.getAccess_token() == null || loginOut.getAccess_token().isEmpty()) {
                        controller.loginResult(false);
                    } else {
                        LoginHeader.getInstance(loginOut.getToken_type() + " " + loginOut.getAccess_token());
                        System.out.println(LoginHeader.getInstance().getAccessToken());

                        controller.loginResult(true);
                    }
                } catch (Exception e) {
                    controller.loginResult(false);
                } finally {
                    response.close();
                }
            }
        };
    }

    public void login(String account, String password, LoginController controller) {
        String url = host + "oauth/token";

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", "9")
                .add("client_secret", key)
                .add("username", account)
                .add("password", password)
                .add("scope", "*")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(loginCallback(controller));

    }

    /**
     * @param identity
     * @param controller
     * @deprecated
     */
    public void login(String identity, LoginController controller) {
        String url = host + "api/hltapp/login";

        RequestBody body = new FormBody.Builder()
                .add("identity", identity)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                controller.loginResult(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    LoginData loginOut = new Gson().fromJson(response.body().string(), LoginData.class);
                    if (loginOut.getData().getAccess_token() == null || loginOut.getData().getAccess_token().isEmpty()) {
                        controller.loginResult(false);
                    } else {
                        LoginHeader.getInstance(loginOut.getData().getToken_type() + " " + loginOut.getData().getAccess_token());
                        controller.loginResult(true);
                    }
                } catch (Exception e) {
                    controller.loginResult(false);
                } finally {
                    response.close();
                }


            }
        });
    }

    public void identity(LoginController controller) {
        String url = host + "api/desktop/identities";

        LoginHeader header = LoginHeader.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                IdentityWrapper wrapper = IdentityWrapper.getInstance();
                wrapper.initFailed();
                controller.vitalError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());

                IdentityData identityData = new Gson().fromJson(response.body().string(), IdentityData.class);
                IdentityWrapper wrapper = IdentityWrapper.getInstance();
                if (identityData.getStatus() != 200) {
                    response.close();
                    wrapper.inited();
                    controller.vitalError();
                } else {
//                    //TODO Test
//                    String[] marks = {"Ydvqmc",
//                            "2goenm",
//                            "Ngrqxz",
//                            "K6aykb",
//                            "Tvwbby",
//                            "2jtjhm",
//                            "3aqf9i",
//                            "Eka49r",
//                            "R5y4gn",
//                            "Olmdc5",
//                            "A7vc5e",
//                            "Xm9rge",
//                            "8nr8uq",
//                            "R6jerq",
//                            "Tknu3w",
//                            "Omtcby",
//                            "Npkpdm",
//                            "Ygwd9a",
//                            "Am1nxu",
//                            "U3s4ai",
//                            "Ergell",
//                            "0ylmly",
//                            "Olhkxu",
//                            "Yuvpqa",
//                            "3okzm6",
//                            "Bufjiy",
//                            "Jlgsxc"};
//
//                    IdentityWrapper identityWrapper = IdentityWrapper.getInstance();
//
//                    identityWrapper.initList(Arrays.stream(marks).map(item -> {
//                        IdentityData.DataBean bean = new IdentityData.DataBean();
//                        bean.setIdentity(item);
//                        bean.setName("产品" + item);
//                        return bean;
//                    }).collect(Collectors.toList()));
//

                    wrapper.initList(identityData.getData());
                    controller.apiInitFinished();
                }

                response.close();
            }
        });
    }


    public void words(LoginController controller) throws ApiErrorException {
        String url = host + "api/desktop/words";

        LoginHeader header = LoginHeader.getInstance();

        Request request = new Request.Builder()
                .url(url)

                .get()
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                KeywordGenerator generator = KeywordGenerator.getInstance();
                generator.initFailed();
                controller.vitalError();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());
                KeywordGenerator generator = KeywordGenerator.getInstance();

                try {
                    String content = response.body().string();
                    WordData wordData = new Gson().fromJson(content, WordData.class);

                    if (wordData.getStatus() != 200) {
                        generator.initFailed();
                        controller.vitalError();
                    } else {
                        generator.setWords(wordData.getData().getPrefix().toArray(new String[]{}),
                                wordData.getData().getMain().toArray(new String[]{}),
                                wordData.getData().getSuffix().toArray(new String[]{}),
                                wordData.getData().getHead().toArray(new String[]{}));
                        controller.apiInitFinished();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    generator.initFailed();
                    controller.vitalError();
                } finally {
                    response.close();

                }

            }

        });
    }

    public void proxy() {
        String url = host + "api/desktop/proxy";

        LoginHeader header = LoginHeader.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String content = response.body().string();
            ProxyData proxyData = new Gson().fromJson(content, ProxyData.class);
            if (proxyData.getStatus() != 200) {
//                        generator.initFailed();
            } else {
                ProxyPool pool = ProxyPool.getInstance();
                pool.freshProxy(proxyData.getData().getProxy());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

////        client.newCall(request).enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, IOException e) {
////                e.printStackTrace();
////                KeywordGenerator generator = KeywordGenerator.getInstance();
////                generator.initFailed();
////            }
////
////            @Override
////            public void onResponse(Call call, Response response) throws IOException {
//////                System.out.println(response.body().string());
//////                KeywordGenerator generator = KeywordGenerator.getInstance();
////                try {
////                    String content = response.body().string();
////                    ProxyData proxyData = new Gson().fromJson(content, ProxyData.class);
////                    if (proxyData.getStatus() != 200) {
//////                        generator.initFailed();
////                    } else {
////                        ProxyPool pool = ProxyPool.getInstance();
////                        pool.freshProxy(proxyData.getData().getProxy());
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                } finally {
////                    response.close();
////
////                }
////
////            }
//
//        });
    }

    public void config(LoginController controller) throws ApiErrorException {
        String url = host + "api/desktop/config";

        LoginHeader header = LoginHeader.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PlatformWrapper wrapper = PlatformWrapper.getInstance();
                wrapper.initFailed();
                controller.vitalError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());

                Config config = new Gson().fromJson(response.body().string(), Config.class);
                PlatformWrapper wrapper = PlatformWrapper.getInstance();

                if (config.getStatus() != 200) {
                    wrapper.initFailed();
                    controller.vitalError();
                } else {
                    if (currentVersion < config.getData().getVersion()) {
                        wrapper.initFailed();
                        controller.newVersionFound();
                    } else {


                        wrapper.init(
                                config.getData().getPlatform().stream().map(item -> {
                                            try {
                                                return new SearchPlatform(
                                                        item.getId(),
                                                        item.getName(),
                                                        item.getUrls(),
                                                        item.getBrowseUrls(),
                                                        item.getPatter(),
                                                        item.isIsMobile());
                                            } catch (URISyntaxException e) {
                                                e.printStackTrace();
                                                return null;
                                            }
                                        }
                                )
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList()),
                                config.getData().getPageMax());
                        controller.apiInitFinished();

                    }
                }
                response.close();
            }

        });
    }

    public void upload(Result result, ApiWriter handler) {

        String url = host + "api/desktop/results";
        LoginHeader header = LoginHeader.getInstance();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(result));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.handleResult(result.getPart(), false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//
                try {
                    String responseText = response.body().string();
                    UploadResult uploadResult = new Gson().fromJson(responseText, UploadResult.class);
                    if (uploadResult.getStatus() != 200) {
                        handler.handleResult(result.getPart(), false);
                    } else {
                        handler.handleResult(result.getPart(), true);

                    }
                } catch (Exception e) {
                    handler.handleResult(result.getPart(), false);
                } finally {
                    response.close();
                }

            }
        });

    }
}


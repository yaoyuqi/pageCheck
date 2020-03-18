package Ywk.Api;

import Ywk.Data.*;
import Ywk.UserInterface.Controller.LoginController;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.stream.Collectors;

public class HltApi {
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
                    TokenData loginOut = new Gson().fromJson(response.body().string(), TokenData.class);
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
                KeywordGenerator generator = KeywordGenerator.getInstance();
                generator.initFailed();
                controller.vitalError();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());

                WordData wordData = new Gson().fromJson(response.body().string(), WordData.class);
                KeywordGenerator generator = KeywordGenerator.getInstance();

                if (wordData.getStatus() != 200) {
                    generator.initFailed();
                    controller.vitalError();
                } else {
                    generator.setWords(wordData.getData().getPrefix().toArray(new String[]{}),
                            wordData.getData().getMain().toArray(new String[]{}),
                            wordData.getData().getSuffix().toArray(new String[]{}));
                    controller.apiInitFinished();

                }
                response.close();
            }

        });
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
                    wrapper.init(
                            config.getData().getPlatform().stream().map(item ->
                                    new SearchPlatform(item.getId(), item.getName(), item.getUrls(), item.getPatter(), item.isIsMobile()))
                                    .collect(Collectors.toList()),
                            config.getData().getPageMax());
                    controller.apiInitFinished();

                }
                response.close();
            }

        });
    }

    public void upload(Result result, ApiWriter handler) {

        String url = host + "api/hltapp/results";
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
//                String responseText = response.body().toString();
                handler.handleResult(result.getPart(), true);
                response.close();
            }
        });

    }
}


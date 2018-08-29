package Ywk.Api;

import Ywk.Data.ApiWriter;
import Ywk.UserInterface.Controller.HomeController;
import Ywk.UserInterface.Controller.LoginController;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class HltApi {
    private static final String key = "33ic8HIz52vDFogcFgA3rwGx7Nke9dArqjyevN3O";
    private static final String host = "http://hlt.neozhan.com/";
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
        if (api == null) {
            api = new HltApi((new OkHttpClient.Builder()).build());
        }
        return api;
    }

    public void login(String account, String password, LoginController controller) {
        String url = host + "oauth/token";

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", "2")
                .add("client_secret", key)
                .add("username", account)
                .add("password", password)
                .add("scope", "")
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
//                LoginData loginOut = new Gson().fromJson(response.body().string(), LoginData.class);
//                if (loginOut.getAccess_token() == null || loginOut.getAccess_token().isEmpty()) {
//                    controller.loginResult(false);
//                } else {
//                    LoginHeader.getInstance(loginOut.getToken_type() + " " + loginOut.getAccess_token());
//                    controller.loginResult(true);
//                }

            }
        });

//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                LoginData loginOut = new Gson().fromJson(response.body().string(), LoginData.class);
//
//                LoginHeader.getInstance(loginOut.getToken_type() + " " + loginOut.getAccess_token());
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

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
                LoginData loginOut = new Gson().fromJson(response.body().string(), LoginData.class);
                if (loginOut.getData().getAccess_token() == null || loginOut.getData().getAccess_token().isEmpty()) {
                    controller.loginResult(false);
                } else {
                    LoginHeader.getInstance(loginOut.getData().getToken_type() + " " + loginOut.getData().getAccess_token());
                    controller.loginResult(true);
                }

            }
        });

//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                LoginData loginOut = new Gson().fromJson(response.body().string(), LoginData.class);
//
//                LoginHeader.getInstance(loginOut.getToken_type() + " " + loginOut.getAccess_token());
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void identity(HomeController controller) {
        String url = host + "api/hltapp/identity";

        LoginHeader header = LoginHeader.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                IdentityData identityData = new Gson().fromJson(response.body().string(), IdentityData.class);
                header.setIdentity(identityData.getData().getIdentity());
                controller.setIdentity(header.getIdentity());
            }
        });

//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                IdentityData identityData = new Gson().fromJson(response.body().string(), IdentityData.class);
//                header.setIdentity(identityData.getData().getIdentity());
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public WordData words() {
        String url = host + "api/hltapp/words";

        LoginHeader header = LoginHeader.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(header.getHeaderMark(), header.getAccessToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
//                System.out.println(response.body().string());
                WordData wordData = new Gson().fromJson(response.body().string(), WordData.class);
                return wordData;
//                header.setIdentity(identityData.getData().getIdentity());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().toString();
                handler.handleResult(result.getType(), result.getPart(), true);
            }
        });

    }
}


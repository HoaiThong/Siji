package net.siji.login;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    public OkHttpUtil() {

    }

    public String excuteLogInPost(String url, String json) {
        String credential = Credentials.basic("jesse", "password145");
        System.out.println(credential);
        RequestBody body = RequestBody.create(JSON, json);
        client = new OkHttpClient();
        String data = "{\"success\":\"0\",\"message\":\"Null\"}";
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(url)
                .post(body).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            data=response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }


    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

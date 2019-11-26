package net.siji.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler extends AsyncTask<String, Void, Boolean> {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    String url = "http://192.168.0.110/KindleServer/view/signin.php";
    OkHttpClient client = new OkHttpClient();
    Activity mActivity;
    JSONObject obj;
    String TAG_MESSAGE = "message";
    String result = "{\"success\":\"0\",\"message\":\"Null\"}";
    private static int i = 0;
    private String message = "Loading...";

    public OkHttpHandler(Activity mActivity) {
        super.onPreExecute();
//        this.mActivity = mActivity;
//        myProgressDialog = new MyProgressDialog(mActivity);
//        myProgressDialog.setMessage(message);
//        myProgressDialog.setCancelable(false);
//        myProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String data = params[0];
        boolean bool = false;
        while (i <= 1) {
            bool = executePost(url, data);
            i++;
            if (bool) return bool;
        }
        return bool;
    }

//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
////        myProgressDialog.dismiss();
//    }

    public boolean executePost(String url, String data) {
        Log.d("url :", url);
        setMessage("Loading(" + i + "/3)...");
//        myProgressDialog.setMessage();
        String credential = Credentials.basic("kindlevn", "ZnSr3t9Ub8J0XV9v");
        System.out.println(credential);
        RequestBody body = RequestBody.create(JSON, data);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("request", data)
                .build();

        client=new OkHttpClient();
//        client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
        i++;
//        credential = "Basic a2luZGxldm46Wm53I1NyM3Q5VWI4SjBYVjl2QA==";
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(url)
                .post(body).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
            Log.d("respose data :", result);
            obj = new JSONObject(result);
            String msg = obj.getString(TAG_MESSAGE);
            if (msg.equals("SignIn Success!")) return true;

        } catch (IOException e) {
            Log.d("ioe :", e.toString());
            return false;
        } catch (JSONException e) {
            Log.d("joe :", e.toString());
//                return excutePost(url, data);
            return false;
        }

        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

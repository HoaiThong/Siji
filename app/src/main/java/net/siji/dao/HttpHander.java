package net.siji.dao;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;

public class HttpHander {
    static InputStream is = null;
    static JSONObject jObj = null;
    String json = "{\"success\":\"0\",\"message\":\"Null\"}";
    String result = "{\"success\":\"0\",\"message\":\"Null\"}";
    OutputStream os = null;
    HttpURLConnection conn = null;
    static JSONArray jarr = null;

    // constructor
    public HttpHander() {

    }

    // function get json from url
// by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {
        Log.d("Line :", "test");

        // Making HTTP request
        try {

            // check for request method
            if (method == "POST") {
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                json = EntityUtils.toString(httpEntity);
//                Log.d("Line :",json);
//                is = httpEntity.getContent();


            } else if (method == "GET") {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                json = EntityUtils.toString(httpEntity);
//                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    is, "iso-8859-1"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            is.close();
//            json = sb.toString();
//        } catch (Exception e) {
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//        }
//
        Log.e("JSON printf", "json " + json.toString());

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            try {
                jObj=new JSONObject(result);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

}

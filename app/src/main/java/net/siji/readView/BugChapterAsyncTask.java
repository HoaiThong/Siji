package net.siji.readView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.ChapterUtils;
import net.siji.dao.HttpHander;
import net.siji.model.Chapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BugChapterAsyncTask extends AsyncTask<String, String, String> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Chapter c;
    private String table;
    private String idCustomer;
    private String idComic;
    private String msg;
    private String chapter;
    private  String msgResponse="";

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * getting All mangas from url
     *
     * @return
     */
    protected String doInBackground(String... args) {
        idCustomer = args[0];
        idComic = args[2];
        chapter = args[3];
        msg = args[4];
        String API_URL = args[5];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idCustomer", idCustomer));
        params.add(new BasicNameValuePair("idComic", idComic));
        params.add(new BasicNameValuePair("chapter", chapter));
        params.add(new BasicNameValuePair("msg", msg));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            msgResponse = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
            }
            return msgResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msgResponse;
    }


}

package net.siji.readView;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.ChapterUtils;
import net.siji.dao.HttpHander;
import net.siji.dialog.RewardDialog;
import net.siji.model.Chapter;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadChapterAsyncTask extends AsyncTask<String, String, List<Chapter>> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Chapter c;
    private ArrayList<Chapter> arrayList;
    private String table;
    private String idCustomer;
    private String idComic;
    private String condition;
    private String fcmtoken;
    String success;
    String msgResponse;

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
    protected List<Chapter> doInBackground(String... args) {
        arrayList = new ArrayList<>();
        idCustomer = args[0];
        fcmtoken = args[1];
        idComic = args[2];
        condition = args[3];
        String API_URL = args[4];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idCustomer", idCustomer));
        params.add(new BasicNameValuePair("fcmtoken", fcmtoken));
        params.add(new BasicNameValuePair("idComic", idComic));
        params.add(new BasicNameValuePair("condition", condition));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            ChapterUtils chapterUtils = new ChapterUtils();
            success = jsonObject.getString(TAG_SUCCESS);
            msgResponse = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(condition);
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = new Chapter();
                    JSONObject o = jsonArray.getJSONObject(i);
                    c = chapterUtils.convertFromJSONObject(o);
                    // adding HashList to ArrayList
                    arrayList.add(c);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}

package net.siji.inforView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.HttpHander;
import net.siji.model.Chapter;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadListChapterAsyncTask extends AsyncTask<String, String, List<Chapter>> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_DISTINCT_CHAPTERS = "distinctChapters";

    Chapter c;
    private ArrayList<Chapter> arrayList;
    private String table;
    private String idCustomer;
    private String idComic;


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
        idComic = args[1];
        table = args[2];
        String startAt = args[3];
        String API_URL = args[4];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("table", table));
        params.add(new BasicNameValuePair("idCustomer", idCustomer));
        params.add(new BasicNameValuePair("idComic", idComic));
        params.add(new BasicNameValuePair("startAt", startAt));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_DISTINCT_CHAPTERS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    c = new Chapter();
                    JSONObject o = jsonArray.getJSONObject(i);
                    Log.e("ALL THE STUFF", o.toString());
                    c.setChapter((float) o.getDouble("chapter"));
                    if (o.isNull("chapterComic")) {
                        c.setPrice((float) o.getDouble("realprice"));
                    } else c.setPrice(0);
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

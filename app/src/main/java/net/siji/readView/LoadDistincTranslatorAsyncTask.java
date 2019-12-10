package net.siji.readView;

import android.os.AsyncTask;

import net.siji.dao.HttpHander;
import net.siji.model.Chapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadDistincTranslatorAsyncTask extends AsyncTask<String, String, List<String>> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_TRANSLATOR = "translator";

    private ArrayList<String> arrayList;
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
    protected List<String> doInBackground(String... args) {
        arrayList = new ArrayList<>();
        idComic = args[0];
        String API_URL = args[1];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idComic", idComic));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_TRANSLATOR);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    String trans=o.getString(TAG_TRANSLATOR).trim();
                    // adding HashList to ArrayList
                    arrayList.add(trans);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}

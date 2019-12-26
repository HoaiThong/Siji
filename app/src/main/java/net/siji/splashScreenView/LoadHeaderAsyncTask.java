package net.siji.splashScreenView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.HttpHander;
import net.siji.model.Comic;
import net.siji.model.Header;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadHeaderAsyncTask extends AsyncTask<String, String, ArrayList<Header>> {
    ArrayList<Header> arrayList = new ArrayList<Header>();
    Header header;
    Comic m;
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ERROR = "error";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_HEADER = "header";
    String API_URL;

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
    protected ArrayList<Header> doInBackground(String... args) {
        API_URL = args[0];
//        Log.e("API", API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_HEADER);
                for (int i = 0; i < jsonArray.length(); i++) {
                    header = new Header();
                    m = new Comic();
                    JSONObject c = jsonArray.getJSONObject(i);
//                    Log.e("ALL THE STUFF", c.toString());
                    m.setId(c.getInt(m.TAG_PID));
                    m.setName(c.getString(m.TAG_NAME));
                    m.setSummary(c.getString(m.TAG_SUMMARY));
                    m.setIconUrl(c.getString(m.TAG_ICON_URL));

                    header.setId(c.getInt(m.TAG_PID));
                    header.setBannerUrl(c.getString(header.TAG_BANNER_URL));
                    header.setComic(m);
                    // adding HashList to ArrayList
                    arrayList.add(header);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<Header> execute() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_HEADER);
                for (int i = 0; i < jsonArray.length(); i++) {
                    header = new Header();
                    m = new Comic();
                    JSONObject c = jsonArray.getJSONObject(i);
                    Log.e("ALL THE STUFF", c.toString());
                    m.setId(c.getInt(m.TAG_PID));
                    m.setName(c.getString(m.TAG_NAME));
                    m.setIconUrl(c.getString(m.TAG_ICON_URL));
                    header.setBannerUrl(c.getString(header.TAG_BANNER_URL));
                    header.setComic(m);
                    // adding HashList to ArrayList
                    arrayList.add(header);
                }
            } else execute();
        } catch (JSONException e) {
            execute();
            e.printStackTrace();
        }
        return arrayList;
    }

}

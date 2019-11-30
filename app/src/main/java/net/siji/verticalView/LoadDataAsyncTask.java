package net.siji.verticalView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.HttpHander;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadDataAsyncTask extends AsyncTask<String, String, ArrayList<Comic>> {
    ArrayList<Comic> arrayList = new ArrayList<Comic>();
    Comic m;
    HttpHander httpHander=new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMIC = "comic";

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * getting All mangas from url
     * @return
     */
    protected ArrayList<Comic> doInBackground(String... args) {
        String startAt=args[0];
        String API_URL=args[1];
        Log.e("start At", startAt);
        Log.e("API", API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("startAt", startAt));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success=jsonObject.getString(TAG_SUCCESS);
            String message=jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)){
                JSONArray jsonArray=jsonObject.getJSONArray(TAG_COMIC);
                for (int i = 0; i < jsonArray.length(); i++) {
                    m = new Comic();
                    JSONObject c = jsonArray.getJSONObject(i);
                    Log.e("ALL THE STUFF", c.toString());
                    Log.e("obj", c.get("name").toString().trim());
                    m.setId(c.getInt(m.TAG_PID));
                    m.setName(c.getString(m.TAG_NAME).trim());
                    m.setNewChapter(c.getString(m.TAG_NEW_CHAP).trim());
                    m.setIconUrl(c.getString(m.TAG_ICON_URL.trim()));
                    m.setAuthor(c.getString(m.TAG_AUTHOR.trim()));
                    m.setViewSum(c.getInt(m.TAG_VIEW_SUM));
//                    System.out.println(m.getId());
//                    System.out.println(m.getName());
//                    System.out.println(m.getNewChapter());
//                    System.out.println(m.getIconUrl());
//                    System.out.println(c.get("author").toString());
//                    System.out.println(m.getViewSum());
                    // adding HashList to ArrayList
                    arrayList.add(m);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}

package net.siji.categorysView;

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

public class LoadCategorysAsyncTask extends AsyncTask<String, String, ArrayList<String>> {
    HttpHander httpHander=new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CATEGORY = "categorys";
    private static final String TAG_ENG = "nameEng";
    ArrayList<String> arrayList=new ArrayList<>();

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
    protected ArrayList<String> doInBackground(String... args) {
        String API_URL=args[0];
        Log.e("API", API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success=jsonObject.getString(TAG_SUCCESS);
            String message=jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)){
                JSONArray jsonArray=jsonObject.getJSONArray(TAG_CATEGORY);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    Log.e("ALL THE STUFF", c.toString());
                    String categoryName=c.getString(TAG_ENG).trim();
//                    System.out.println(m.getId());
//                    System.out.println(m.getName());
//                    System.out.println(m.getNewChapter());
//                    System.out.println(m.getIconUrl());
//                    System.out.println(c.get("author").toString());
//                    System.out.println(m.getViewSum());
                    // adding HashList to ArrayList
                    arrayList.add(categoryName);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}

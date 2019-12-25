package net.siji.homeView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.AdvertiseUtils;
import net.siji.dao.HttpHander;
import net.siji.model.Advertise;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAdvertiseAsyncTask extends AsyncTask<String, String, ArrayList<Advertise>> {
    ArrayList<Advertise> arrayList = new ArrayList<>();
    Advertise advertise;
    HttpHander httpHander=new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static final String TAG_ADVERTISE = "advertise";
    private AdvertiseUtils advertiseUtils;

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * getting All mangas from url
     */
    protected ArrayList<Advertise> doInBackground(String... args) {
        String API_URL=args[0];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            advertiseUtils=new AdvertiseUtils();
            String success=jsonObject.getString(TAG_SUCCESS);
            String message=jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)){
                JSONArray jsonArray=jsonObject.getJSONArray(TAG_ADVERTISE);
                for (int i = 0; i < jsonArray.length(); i++) {
                    advertise = new Advertise();
                    JSONObject c = jsonArray.getJSONObject(i);
//                    Log.e("ALL THE STUFF", c.toString());
                    advertise=advertiseUtils.createFromJSONObject(c);
                    // adding HashList to ArrayList
                    arrayList.add(advertise);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}

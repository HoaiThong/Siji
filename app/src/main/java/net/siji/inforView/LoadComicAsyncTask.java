package net.siji.inforView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.ComicUtils;
import net.siji.dao.HttpHander;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadComicAsyncTask extends AsyncTask<String, String, Comic> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMIC = "comic";
    private Comic comic;

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
    protected Comic doInBackground(String... args) {
        String idComic = args[0];
        String API_URL = args[1];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idComic", idComic));
        return execute(params, API_URL);
    }

    protected Comic execute(List<NameValuePair> params, String API_URL) {
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        comic = new Comic();
        ComicUtils comicUtil=new ComicUtils();
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_COMIC);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Log.e("ALL THE STUFF", o.toString());
                    comic=comicUtil.convertFromJSONObject(o);
                    Log.e("json convert: ", comic.getSummary());
//                    comic.setId(o.getInt(comic.TAG_PID));
//                    comic.setName(o.getString(comic.TAG_NAME));
//                    comic.setNewChapter(o.getString(comic.TAG_NEW_CHAP));
//                    comic.setIconUrl(o.getString(comic.TAG_ICON_URL));
//                    if (!o.isNull(comic.getTblName()))
//                        comic.setTblName(o.getString(comic.TAG_TABLE));
//                    else comic.setTblName("0");
                }
                return comic;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comic;
    }

}

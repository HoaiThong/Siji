package net.siji.inforView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.HttpHander;
import net.siji.model.Chapter;
import net.siji.model.Comic;
import net.siji.model.Comment;
import net.siji.model.Customer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadCommentAsyncTask extends AsyncTask<String, String, List<Comment>> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMMENT = "comment";

    Comment cmt;
    Customer c;
    ArrayList<Comment> arrayList;

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
    protected List<Comment> doInBackground(String... args) {
        arrayList = new ArrayList<>();
        String idComic = args[0];
        String startAt = args[1];
        String API_URL = args[2];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idComic", idComic));
        params.add(new BasicNameValuePair("startAt", startAt));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_COMMENT);
                for (int i = 0; i < jsonArray.length(); i++) {
                    cmt = new Comment();
                    c = new Customer();
                    JSONObject o = jsonArray.getJSONObject(i);
                    Log.e("ALL THE STUFF", o.toString());
                    cmt.setComment(o.getString("cmtCustomer"));
                    if (!o.isNull("nameGoogle"))
                        c.setNameGoogle(o.getString("nameGoogle"));
                    if (!o.isNull("nameFaceBook"))
                        c.setNameFaceBook(o.getString("nameFaceBook"));
                    c.setIconUrl(o.getString("iconUrl"));
                    cmt.setCustomer(c);
                    Log.e("ALL THE STUFF", cmt.getComment());
                    // adding HashList to ArrayList
                    arrayList.add(cmt);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}

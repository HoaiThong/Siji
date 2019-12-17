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

public class WalletAsyncTask extends AsyncTask<String, String, Integer> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_AMOUNT = "amount";
    private static final String TAG_UPDATE = "update";
    private static final String TAG_INSERT = "insert";
    Chapter c;
    private ArrayList<Chapter> arrayList;
    private String table;
    private String idCustomer;
    private String idComic;
    private String chapter;
    private String fcmtoken;
    private String price;
    private String API_URL;
    private List<NameValuePair> params;
    int amountResp;
    int updateResp;
    int insertResp;

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
    protected Integer doInBackground(String... args) {
        arrayList = new ArrayList<>();
        idCustomer = args[0];
        fcmtoken = args[1];
        idComic = args[2];
        chapter = args[3];
        price = args[4];
        API_URL = args[5];
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idCustomer", idCustomer));
        params.add(new BasicNameValuePair("fcmtoken", fcmtoken));
        params.add(new BasicNameValuePair("idComic", idComic));
        params.add(new BasicNameValuePair("chapter", chapter));
        params.add(new BasicNameValuePair("price", price));
        execute();
        return amountResp;
    }

    public void execute() {
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            Log.d("wallet", jsonObject.toString());
            amountResp = jsonObject.getInt(TAG_AMOUNT);
            updateResp = jsonObject.getInt(TAG_UPDATE);
            insertResp = jsonObject.getInt(TAG_INSERT);
            if (amountResp == -2)
                execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

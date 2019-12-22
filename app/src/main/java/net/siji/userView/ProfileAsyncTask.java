package net.siji.userView;

import android.os.AsyncTask;
import android.util.Log;

import net.siji.dao.CustomerUtils;
import net.siji.dao.HttpHander;
import net.siji.model.Customer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileAsyncTask extends AsyncTask<String, String, Customer> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CUSTOMER = "customer";

    private String msgResponse = "";

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
    protected Customer doInBackground(String... args) {
        String idCustomer=args[0];
        String fcmtoken=args[1];
        String API_URL_REQUEST_PROFILE=args[2];
        Customer customer=new Customer();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idCustomer", idCustomer));
        params.add(new BasicNameValuePair("fcmtoken", fcmtoken));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL_REQUEST_PROFILE, "POST", params);
        try {
            Log.d("profile", jsonObject.toString());
            CustomerUtils customerUtils=new CustomerUtils();
            String success=jsonObject.getString(TAG_SUCCESS);
            String message=jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)){
                JSONArray jsonArray=jsonObject.getJSONArray(TAG_CUSTOMER);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    customer=customerUtils.createFromJSONObject(object);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    protected void onPostExecute(Customer c) {
    }
}


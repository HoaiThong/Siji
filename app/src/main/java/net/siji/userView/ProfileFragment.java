package net.siji.userView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import net.siji.R;
import net.siji.dao.ComicUtils;
import net.siji.dao.CustomerUtils;
import net.siji.dao.HttpHander;
import net.siji.model.ApiManager;
import net.siji.model.Comic;
import net.siji.model.Customer;
import net.siji.sessionApp.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private View view;
    private Activity mActivity;
    private EditText edt_msg;
    String idCustomer;
    String fcmtoken;
    String msg = "";
    private String API_URL_REQUEST_PROFILE = "http://192.168.0.104/siji-server/view/api_customer_send_message.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mActivity.setTitle(mActivity.getString(R.string.profile));
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

//        relativeLayout = (RelativeLayout) findViewById(R.id.header_layout);
//        Bitmap originalBitmap = BlurBuilder.getBitmapFromURL("https://i-giaitri.vnecdn.net/2019/02/25/ngoc-diem-2-1551065105_r_680x0.jpg");
//         Bitmap blurredBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
//            relativeLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
        setHasOptionsMenu(true);
        SessionManager sessionManager = new SessionManager(mActivity);
        idCustomer = sessionManager.getReaded("idUser");
        fcmtoken = sessionManager.getReaded("tokenfcm");
        API_URL_REQUEST_PROFILE = new ApiManager().API_URL_REQUEST_PROFILE;
        new ProfileAsyncTask().execute(idCustomer,fcmtoken,API_URL_REQUEST_PROFILE);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btn_send) {
//            msg = edt_msg.getText().toString().trim();
//            if (!msg.equals("") && !msg.isEmpty()) {
//                new RequesAsyncTask().execute();
//                edt_msg.setText("");
//            }
//        }
    }

    private class UpdateProfileAsyncTask extends AsyncTask<String, String, Customer> {
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

}
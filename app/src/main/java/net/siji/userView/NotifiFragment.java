package net.siji.userView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Api;

import net.siji.R;
import net.siji.dao.HttpHander;
import net.siji.model.ApiManager;
import net.siji.sessionApp.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotifiFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private View view;
    private Activity mActivity;
    private EditText edt_msg;
    String idCustomer;
    String status = "message";
    String fcmtoken;
    String msg = "";
    private String API_URL_REQUEST = "http://192.168.0.104/siji-server/view/api_customer_send_message.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user_send_notifi, container, false);
        mActivity.setTitle(mActivity.getString(R.string.user_notification));
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);
        SessionManager sessionManager = new SessionManager(mActivity);
        idCustomer = sessionManager.getReaded("idUser");
        fcmtoken = sessionManager.getReaded("tokenfcm");
        API_URL_REQUEST = new ApiManager().API_URL_REQUEST;
        edt_msg = view.findViewById(R.id.edt_msg);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
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
        if (v.getId() == R.id.btn_send) {
            msg = edt_msg.getText().toString().trim();
            if (!msg.equals("") && !msg.isEmpty()) {
                new RequesAsyncTask().execute();
                edt_msg.setText("");
            }
        }
    }

    private class RequesAsyncTask extends AsyncTask<String, String, String> {
        HttpHander httpHander = new HttpHander();
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

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
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idCustomer", idCustomer));
            params.add(new BasicNameValuePair("msg", msg));
            params.add(new BasicNameValuePair("status", status));
            params.add(new BasicNameValuePair("fcmtoken", fcmtoken));
            JSONObject jsonObject = httpHander.makeHttpRequest(API_URL_REQUEST, "POST", params);
            try {
                int success = jsonObject.getInt(TAG_SUCCESS);
                msgResponse = jsonObject.getString(TAG_MESSAGE);
                if (success == 1) {
                }
                return msgResponse;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return msgResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
        }
    }

}
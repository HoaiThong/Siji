package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import net.siji.R;
import net.siji.dao.HttpHander;
import net.siji.model.ApiManager;
import net.siji.model.Chapter;
import net.siji.model.Comic;
import net.siji.readView.ViewerActivity;
import net.siji.search.SearchViewActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestFindComicDialog extends DialogFragment implements View.OnClickListener {
    Button send_btn;
    Button cancel_btn;
    EditText msg_edt;
    String msg = "";
    private String API_URL_REQUEST_FIND_COMIC = "http://192.168.0.104/siji-server/view/api_customer_send_message.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MSG = "message";
    private String nameChapter;
    private Comic comic;
    private Context context;
    Activity activity;
    String message = "";
    String idCustomer;
    String status="find";
    String fcmtoken;
    View view;
    private Activity mActivity;
    private FragmentManager fragmentManager;

    public RequestFindComicDialog(Activity mActivity, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.mActivity = mActivity;
    }

    public RequestFindComicDialog(Activity mActivity, FragmentManager fragmentManager, String idCustomer, String fcmtoken) {
        this.fragmentManager = fragmentManager;
        this.mActivity = mActivity;
        this.idCustomer = idCustomer;
        this.fcmtoken=fcmtoken;
        this.context = context;
        this.API_URL_REQUEST_FIND_COMIC=new ApiManager().API_URL_REQUEST_FIND_COMIC;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_find_comic, null);
        msg_edt = (EditText) view.findViewById(R.id.edt_content);
        view.findViewById(R.id.find_dialog_btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.find_dialog_btn_ok).setOnClickListener(this);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        return builder.create();
    }

    public void show() {
        this.show(fragmentManager, "dialog");
        this.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_dialog_btn_cancel:
                this.dismiss();
                break;
            case R.id.find_dialog_btn_ok:
                msg = msg_edt.getText().toString().trim();
                if (!msg.isEmpty() && !msg.equals("")) {
                    new FindAsyncTask().execute(idCustomer,msg,status,API_URL_REQUEST_FIND_COMIC);
                    this.dismiss();
                }
                break;
        }
    }


    private class FindAsyncTask extends AsyncTask<String, String, String> {
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
            JSONObject jsonObject = httpHander.makeHttpRequest(API_URL_REQUEST_FIND_COMIC, "POST", params);
            try {
                int success = jsonObject.getInt(TAG_SUCCESS);
                msgResponse = jsonObject.getString(TAG_MESSAGE);
                if (success==1) {
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

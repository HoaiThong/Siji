package net.siji.readView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.siji.R;
import net.siji.dao.HttpHander;
import net.siji.model.Chapter;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationBugDialog extends DialogFragment {
    Button send_btn;
    Button cancel_btn;
    EditText msg_edt;
    String msg = "";
    private static String API_URL_SEND_BUG = "http://192.168.1.121/siji-server/view/api_send_msg_bug_chapter.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MSG = "message";
    private String nameChapter;
    private Comic comic;
    private Context context;
    Activity activity;
    String message = "";
    String idCustomer;
    String idComic;
    String chapter;

    View view;

    public NotificationBugDialog(Context context, String idCustomer, String idComic, String chapter) {
        this.idComic = idComic;
        this.idCustomer = idCustomer;
        this.chapter = chapter;
        this.context = context;
        message = context.getString(R.string.thanks_label);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_dialog_notification_bug, null);
        msg_edt = (EditText) view.findViewById(R.id.msg_dialog_edt);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.send_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        msg = msg_edt.getText().toString().trim();
                        if (!msg.equals("")) {
                            new BugChapterAsyncTask().execute();
                            dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NotificationBugDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private class BugChapterAsyncTask extends AsyncTask<String, String, String> {
        HttpHander httpHander = new HttpHander();
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        Chapter c;
        private String table;
        private String idCustomer;
        private String idComic;
        private String msg;
        private String chapter;
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
            params.add(new BasicNameValuePair("idComic", idComic));
            params.add(new BasicNameValuePair("chapter", chapter));
            params.add(new BasicNameValuePair("msg", msg));
            JSONObject jsonObject = httpHander.makeHttpRequest(API_URL_SEND_BUG, "POST", params);
            try {
                String success = jsonObject.getString(TAG_SUCCESS);
                msgResponse = jsonObject.getString(TAG_MESSAGE);
                if (success.equals(TAG_SUCCESS)) {
                }
                return msgResponse;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return msgResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        }
    }




}

package net.siji.splashScreenView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;


import net.siji.MainActivity;
import net.siji.R;
import net.siji.dao.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {
    static int SPLASH_TIME_OUT = 5000;
    String name, state;
    private String API_LOGIN_URL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new LoadingData().execute();
    }
    private class LoadingData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(API_LOGIN_URL, ServiceHandler.POST);
            if (json != null) {
                try {
                    JSONObject jObj = new JSONObject(json).getJSONObject("os");
                    name = jObj.getString("name");
                    state = jObj.getString("state");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
                @Override
                public void run() {
                    // Sau khi hoàn thành sẽ đóng activity này và chạy main activity
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
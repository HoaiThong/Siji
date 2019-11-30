package net.siji.splashScreenView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import net.siji.MainActivity;
import net.siji.R;
import net.siji.dao.HttpHander;
import net.siji.dao.ServiceHandler;
import net.siji.model.Comic;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SplashScreenActivity extends AppCompatActivity {
    static int SPLASH_TIME_OUT = 5000;
    String name, state;
    private String API_LOGIN_URL = "";
    private String API_GET_LIMIT_COMIC_BY_UPDATE = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_update.php";
    private String API_GET_LIMIT_COMIC_FULL = "http://192.168.1.121/siji-server/view/api_get_limit_comic_full.php";
    private String API_GET_LIMIT_COMIC_BY_VIEW_DAY = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_view_day.php";

    private final String startAt = "0";
    private final Handler handler = new Handler();
    ImageView mImageView;
    TextView mTextView;
    Thread mThread;
    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                data = new Bundle();
                getListComicFull();
                getListComicUpdate();
                getListComicByViewDay();
                redirect();
            }
        }, 100);

//        setContentView(R.layout.activity_splash_screen);
//        mImageView = (ImageView) findViewById(R.id.image);
//        mTextView = (TextView) findViewById(R.id.text);
//        startAnimation();
    }

    private List<Comic> getListComicUpdate() {
        List<Comic> listUpdate = new ArrayList<Comic>();
        try {
            listUpdate = new LoadDataAsyncTask().execute(startAt, API_GET_LIMIT_COMIC_BY_UPDATE).get();
            data.putParcelableArrayList("update", (ArrayList<? extends Parcelable>) listUpdate);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listUpdate;
    }

    private List<Comic> getListComicFull() {
        List<Comic> list = new ArrayList<>();
        try {
            list = new LoadDataAsyncTask().execute(startAt, API_GET_LIMIT_COMIC_FULL).get();
        data.putParcelableArrayList("full", (ArrayList<? extends Parcelable>) list);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Comic> getListComicByViewDay() {
        List<Comic> list = new ArrayList<>();
        try {
            list = new LoadDataAsyncTask().execute(startAt, API_GET_LIMIT_COMIC_BY_VIEW_DAY).get();
        data.putParcelableArrayList("viewday", (ArrayList<? extends Parcelable>) list);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }


    private void startAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);
        rotate.reset();
        translate.reset();

        mImageView.setAnimation(rotate);
        mTextView.setAnimation(translate);

        mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                int waited = 0;
                while (waited < 5000) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                redirect();
            }
        };
        mThread.start();
    }

    public void redirect() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(data);
        startActivity(intent);
        finish();
    }

}
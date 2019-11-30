package net.siji.inforView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import net.siji.MainActivity;
import net.siji.R;
import net.siji.dao.BlurBuilder;
import net.siji.readView.EpubViewerActivity;
import net.siji.splashScreenView.SplashScreenActivity;


public class InforActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private DetailFragment detailFragment;
    private ContentsFragment contentsFragment;
    RelativeLayout relativeLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        relativeLayout = (RelativeLayout) findViewById(R.id.header_layout);
//        Bitmap originalBitmap = BlurBuilder.getBitmapFromURL("https://i-giaitri.vnecdn.net/2019/02/25/ngoc-diem-2-1551065105_r_680x0.jpg");
//         Bitmap blurredBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
//            relativeLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle("");
        appBarLayout = findViewById(R.id.app_bar_layout);
//        fab=findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), EpubViewerActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        showHideTitle();
        setTitle("");
        new AsyncGettingBitmapFromUrl().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHideTitle() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbarLayout.setTitle("Bleach"); // Careful! There should be a space between double quote. Otherwise it won't work.
                    isShow = false;
                } else if (!isShow) {
                    mCollapsingToolbarLayout.setTitle("");
                    isShow = true;
                }
            }
        });
    }

    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap originalBitmap = BlurBuilder.getBitmapFromURL("https://i-giaitri.vnecdn.net/2019/02/25/ngoc-diem-2-1551065105_r_680x0.jpg");


            return originalBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            Bitmap blurredBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
            relativeLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));

        }
    }
}

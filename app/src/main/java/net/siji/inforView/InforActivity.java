package net.siji.inforView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import net.siji.R;
import net.siji.dao.BlurBuilder;
import net.siji.dialog.LoadingDialog;
import net.siji.model.Chapter;
import net.siji.model.Comic;
import net.siji.model.Comment;
import net.siji.model.Customer;
import net.siji.readView.ViewerActivity;
import net.siji.sessionApp.SessionManager;
import net.siji.splashScreenView.SplashScreenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dmax.dialog.SpotsDialog;


public class InforActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private DetailFragment detailFragment;
    private ContentsFragment contentsFragment;
    RelativeLayout relativeLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton fab;
    Comic comic = new Comic();
    Customer user;
    String isNotifi = "0";
    String idCustomer;
    List<Comment> commentList;
    List<Chapter> chapterList;
    int startAt = 0;
    int startAtCmt = 0;
    String translator = "";
    String idComic;
    private final String API_URL_READED_COMIC = "http://192.168.1.121/siji-server/view/api_readed_comic.php";
    private final String API_URL_SUBCRIBE_COMIC = "http://192.168.1.121/siji-server/view/api_subcribe_comic.php";
    private final String API_URL_GET_COMIC_BY_ID = "http://192.168.1.121/siji-server/view/api_get_comic_by_id.php";
    private final String API_URL_GET_LIST_CHAPTER = "http://192.168.1.121/siji-server/view/api_get_limit_chapters.php";
    private final String API_URL_GET_COMMENT = "http://192.168.1.121/siji-server/view/api_comments_get_all_of_comic.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        relativeLayout = (RelativeLayout) findViewById(R.id.header_layout);
//        Bitmap originalBitmap = BlurBuilder.getBitmapFromURL("https://i-giaitri.vnecdn.net/2019/02/25/ngoc-diem-2-1551065105_r_680x0.jpg");
//         Bitmap blurredBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
//            relativeLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle("");
        appBarLayout = findViewById(R.id.app_bar_layout);
        showHideTitle();
        setTitle("");
        init();
        initTabHost();
    }


    public void initTabHost() {
        tabLayout = findViewById(R.id.htab_tabs);
        viewPager = findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                Log.d("TAG", "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        showToast("One");
                        break;
                    case 1:
                        showToast("Two");
                        break;
                    case 2:
                        showToast("Three");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(
                ContextCompat.getColor(this, R.color.white)), "Cyan");
        adapter.addFrag(new DummyFragment(
                ContextCompat.getColor(this, R.color.white)), "Amber");
        viewPager.setAdapter(adapter);
    }

    public void init() {
        comic = new Comic();
        chapterList = new ArrayList<>();
        commentList = new ArrayList<>();
        startAt = 0;
        isNotifi = "0";
        startAtCmt = 0;
        comic = (Comic) getIntent().getSerializableExtra("comic");
        SessionManager sessionManager = new SessionManager(this);
        idCustomer = sessionManager.getReaded("idUser");
        idComic = String.valueOf(comic.getId());
//        final SpotsDialog spotsDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(this).build();
//        spotsDialog.setCancelable(false);
//        spotsDialog.setMessage("");
//        spotsDialog.show();
        final LoadingDialog spotsDialog = new LoadingDialog(getSupportFragmentManager());
        spotsDialog.show();
//        new AsyncGettingBitmapFromUrl().execute(comic.getIconUrl().trim());
        new SubcribeComicAsyncTask().execute(idCustomer, idComic, isNotifi, API_URL_READED_COMIC);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getComic();
                getListChapters();
                getComment();
                spotsDialog.dismiss();
            }
        }, 1000);
    }

    public void getComic() {
        try {
            comic = new LoadComicAsyncTask().execute(idComic, API_URL_GET_COMIC_BY_ID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getListChapters() {
        String start = String.valueOf(startAt);
        try {
            String tableComic = comic.getTblName();
            chapterList = new LoadListChapterAsyncTask().execute(idCustomer, idComic, tableComic, start, API_URL_GET_LIST_CHAPTER).get();
            startAt = startAt + chapterList.size();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getComment() {
        String startCmt = String.valueOf(startAtCmt);
        try {
            commentList = new LoadCommentAsyncTask().execute(idComic, startCmt, API_URL_GET_COMMENT).get();
            startAtCmt = startAtCmt + commentList.size();
            for (int i = 0; i < commentList.size(); i++) {
                System.out.println(commentList.get(i).getCustomer().getNameGoogle());
                System.out.println(commentList.get(i).getComment());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void redirect() {
        Intent intent = new Intent(this, ViewerActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("comic", comic);
        intent.putExtras(data);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info_menu, menu);
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
        if (item.getItemId() == R.id.menu_main_share) {
            redirect();
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
//                    mCollapsingToolbarLayout.setTitle("Bleach"); // Careful! There should be a space between double quote. Otherwise it won't work.
                    mCollapsingToolbarLayout.setTitle("");
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
            String url = params[0];

            Bitmap originalBitmap = BlurBuilder.getBitmapFromURL(url);
            return originalBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            Bitmap blurredBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
            relativeLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));

        }
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            hideSystemUI();
//        }
//    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}

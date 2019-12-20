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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import net.siji.MainActivity;
import net.siji.R;
import net.siji.dao.BlurBuilder;
import net.siji.dialog.LoadingDialog;
import net.siji.model.ApiManager;
import net.siji.model.Chapter;
import net.siji.model.Comic;
import net.siji.model.Comment;
import net.siji.model.Customer;
import net.siji.readView.VerticalChapterAdapter;
import net.siji.readView.ViewerActivity;
import net.siji.sessionApp.SessionManager;
import net.siji.splashScreenView.SplashScreenActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dmax.dialog.SpotsDialog;


public class InforActivity extends AppCompatActivity implements View.OnClickListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    RelativeLayout relativeLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton fab;
    Comic comic = new Comic();
    Customer user;
    String isNotifi = "-1";
    String idCustomer;
    List<Comment> commentList;
    List<Chapter> chapterList;
    int startAt = 0;
    int startAtCmt = 0;
    String translator = "";
    String idComic;
    private String API_URL_READED_COMIC = "http://192.168.1.121/siji-server/view/api_readed_comic.php";
    private String API_URL_SUBCRIBE_COMIC = "http://192.168.1.121/siji-server/view/api_subcribe_comic.php";
    private String API_URL_GET_COMIC_BY_ID = "http://192.168.1.121/siji-server/view/api_get_comic_by_id.php";
    private String API_URL_GET_LIST_CHAPTER = "http://192.168.1.121/siji-server/view/api_get_limit_chapters.php";
    private String API_URL_GET_COMMENT = "http://192.168.1.121/siji-server/view/api_comments_get_all_of_comic.php";
    private VerticalChapterAdapter verticalChapterAdapter;
    private VerticalCommentAdapter verticalCommentAdapter;
    private LinkedList<Chapter> linkedList;
    private LinkedList<Comment> linkedListCmt;
    private ListView chapterListView;
    private ListView commentListView;
    private View line_tab_chapter, line_tab_comment;
    private int quantity = 0;
    private int quantityCmt = 0;
    private boolean flagSubcribe = false;
    private ImageView imgIcon;

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
        loadApiUrl();
        init();
    }


    private void loadApiUrl() {
        ApiManager apiManager = new ApiManager();
        API_URL_GET_COMIC_BY_ID = apiManager.API_URL_GET_COMIC_BY_ID;
        API_URL_SUBCRIBE_COMIC = apiManager.API_URL_SUBCRIBE_COMIC;
        API_URL_GET_COMMENT = apiManager.API_URL_GET_COMMENT;
        API_URL_GET_LIST_CHAPTER = apiManager.API_URL_GET_LIST_CHAPTER;
        API_URL_READED_COMIC = apiManager.API_URL_READED_COMIC;
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    public void init() {
        comic = new Comic();
        chapterList = new ArrayList<>();
        commentList = new ArrayList<>();
        linkedList = new LinkedList<>();
        linkedListCmt = new LinkedList<>();
        startAt = 0;
        isNotifi = "-1";
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
                initInfo();
                initChaptersMenu();
                initComments();

            }
        }, 2000);

        imgIcon = findViewById(R.id.img_comic);
        chapterListView = findViewById(R.id.list_view_chapters);
        commentListView = findViewById(R.id.list_view_comments);
        line_tab_chapter = findViewById(R.id.line_tab_chapter);
        line_tab_comment = findViewById(R.id.line_tab_comment);
        findViewById(R.id.tab_chapter).setOnClickListener(this);
        findViewById(R.id.tab_comment).setOnClickListener(this);

//        initComments();
    }

    public void initInfo() {
        Glide.with(this)
                .load(comic.getIconUrl())
                .into(imgIcon);
    }

    public void initChaptersMenu() {
        verticalChapterAdapter = new VerticalChapterAdapter(this, linkedList);
        chapterListView.setAdapter(verticalChapterAdapter);
        chapterListView.setOnScrollListener(onScrollListener());
        chapterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter = linkedList.get(position);
                Bundle data = new Bundle();
                data.putSerializable("chapter", chapter);
                data.putSerializable("comic", comic);
                Intent intent = new Intent(InforActivity.this, ViewerActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                finish();

            }
        });
        chapterListView.setVisibility(View.VISIBLE);
        line_tab_chapter.setVisibility(View.VISIBLE);
        commentListView.setVisibility(View.GONE);
        line_tab_comment.setVisibility(View.GONE);
    }

    public void initComments() {
        verticalCommentAdapter = new VerticalCommentAdapter(this, linkedListCmt);
        commentListView.setAdapter(verticalCommentAdapter);
        commentListView.setOnScrollListener(onScrollCommentListener());

    }

    public void getComic() {
        try {
            comic = new LoadComicAsyncTask().execute(idComic,idCustomer, API_URL_GET_COMIC_BY_ID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidateOptionsMenu();
    }

    public void getListChapters() {
        String start = String.valueOf(startAt);
        try {
            String tableComic = comic.getTblName();
            chapterList = new LoadListChapterAsyncTask().execute(idCustomer, idComic, tableComic, start, API_URL_GET_LIST_CHAPTER).get();
            quantity = chapterList.size();
            startAt = startAt + chapterList.size();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        linkedList.addAll(chapterList);

    }

    public void getComment() {
        String startCmt = String.valueOf(startAtCmt);
        try {
            commentList = new LoadCommentAsyncTask().execute(idComic, startCmt, API_URL_GET_COMMENT).get();
            quantityCmt = commentList.size();
            startAtCmt = startAtCmt + quantityCmt;
            linkedListCmt.addAll(commentList);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_main_subcrise);
        if (comic.getIsNotifi()==1) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_heart_white));
        flagSubcribe=true;
        }
        else {
            flagSubcribe=false;
            item.setIcon(getResources().getDrawable(R.drawable.ic_heart_border));
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }

        if (item.getItemId() == R.id.menu_main_subcrise) {
            if (flagSubcribe) {
                flagSubcribe = false;
                isNotifi = "-1";
                new SubcribeComicAsyncTask().execute(idCustomer, idComic, isNotifi, API_URL_SUBCRIBE_COMIC);
                item.setIcon(getResources().getDrawable(R.drawable.ic_heart_border));
            } else {
                flagSubcribe = true;
                isNotifi = "1";
                new SubcribeComicAsyncTask().execute(idCustomer, idComic, isNotifi, API_URL_SUBCRIBE_COMIC);
                item.setIcon(getResources().getDrawable(R.drawable.ic_heart_white));
                Toast.makeText(getApplicationContext(), getString(R.string.them_truyen_yeu_thich), Toast.LENGTH_SHORT).show();
            }
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
//                    mCollapsingToolbarLayout.setTitle(comic.getName());
                    setTitle(comic.getName());
                    isShow = false;
                } else if (!isShow) {
                    setTitle("");
//                    mCollapsingToolbarLayout.setTitle(comic.getName());
                    isShow = true;
                }
            }
        });
    }

    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = chapterListView.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (chapterListView.getLastVisiblePosition() >= count - threshold && quantity > 0) {
                        // Execute LoadMoreDataTask AsyncTask
                        getListChapters();
                        verticalChapterAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }

    private AbsListView.OnScrollListener onScrollCommentListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = commentListView.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (commentListView.getLastVisiblePosition() >= count - threshold && quantityCmt > 0) {
                        // Execute LoadMoreDataTask AsyncTask
                        getComment();
                        verticalCommentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_chapter:
                chapterListView.setVisibility(View.VISIBLE);
                line_tab_chapter.setVisibility(View.VISIBLE);
                commentListView.setVisibility(View.GONE);
                line_tab_comment.setVisibility(View.GONE);
                break;
            case R.id.tab_comment:
                chapterListView.setVisibility(View.GONE);
                line_tab_chapter.setVisibility(View.GONE);
                commentListView.setVisibility(View.VISIBLE);
                line_tab_comment.setVisibility(View.VISIBLE);
                break;
        }
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

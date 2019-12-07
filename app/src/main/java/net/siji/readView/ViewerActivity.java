package net.siji.readView;

import android.animation.Animator;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.siji.R;
import net.siji.model.Chapter;
import net.siji.model.Comic;
import net.siji.sessionApp.SessionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ViewerActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private WebView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    int width;
    Context mContext;
    FrameLayout frameLayout;
    private final String API_URL_GET_LIST_CHAPTER = "http://192.168.1.121/siji-server/view/api_get_limit_chapters.php";
    private final String API_GET_CONTENT_CHAPTER_URL = "http://192.168.1.121/siji-server/view/api_get_all_page_of_chapter.php";
    List<Chapter> chapterList;
    int startAt = 0;
    Comic comic;
    String idCustomer;
    String idComic;
    List<Chapter> distinctChapterList;
    LinkedList<Chapter> linkedList;
    FloatingActionButton fab1;

    private LinearLayout fabContainer, img_view;
    private FloatingActionButton fab;
    private boolean fabMenuOpen = false;
    private FloatingActionButton exchange_fab;
    static boolean flagShow = false;
    private TextView exchange_fb_tv;
    ListView chapterListView;
    VerticalChapterAdapter verticalChapterAdapter;
    int quantity;
    TextView tv_title_comic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer);
        init();
        initFab();
        loadContentChapter();
        loadDistinctChapters();
        initChapterMenu();
        initWebView();
        hide();

    }

    public void loadDistinctChapters() {
        String start = String.valueOf(startAt);
        try {
            distinctChapterList = new LoadDistincChapterAsyncTask().execute(idCustomer, idComic, start, API_URL_GET_LIST_CHAPTER).get();
            linkedList.addAll(distinctChapterList);
            quantity = distinctChapterList.size();
            startAt = startAt + quantity;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadContentChapter() {
        chapterList = new ArrayList<>();
        try {
            chapterList = new LoadChapterAsyncTask().execute("", "", "", "", API_GET_CONTENT_CHAPTER_URL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initChapterMenu() {
        chapterListView = findViewById(R.id.listview_chapter);
        verticalChapterAdapter = new VerticalChapterAdapter(this, linkedList);
        chapterListView.setAdapter(verticalChapterAdapter);
        chapterListView.setOnScrollListener(onScrollListener());
    }

    public void init() {
        startAt = 0;
        SessionManager sessionManager = new SessionManager(this);
        idCustomer = sessionManager.getReaded("idUser");
        comic = (Comic) getIntent().getSerializableExtra("comic");
        idComic = String.valueOf(comic.getId());
        distinctChapterList = new ArrayList<>();
        linkedList = new LinkedList<>();
        mVisible = true;
        tv_title_comic = findViewById(R.id.tv_nav_title_comic);
        tv_title_comic.setText(comic.getName().trim());
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (WebView) findViewById(R.id.fullscreen_content);
        frameLayout = findViewById(R.id.frame_layout);
        mContext = this;


    }

    public void initFab() {
        fabContainer = (LinearLayout) findViewById(R.id.fabContainerLayout);
        exchange_fb_tv = (TextView) findViewById(R.id.exchange_fab_lb);
        fab = (FloatingActionButton) findViewById(R.id.fab_view_act);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabMenu();
            }
        });
        FloatingActionButton bug_fab = (FloatingActionButton) findViewById(R.id.msg_bug_fab);
        bug_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        exchange_fab = (FloatingActionButton) findViewById(R.id.exchange_fab);
        exchange_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagShow = !flagShow;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleFabMenu() {
        if (!fabMenuOpen) {
            fab.setImageResource(R.drawable.ic_error);
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = 0;
            int endRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;

            fabContainer.setVisibility(View.VISIBLE);
            ViewAnimationUtils
                    .createCircularReveal(
                            fabContainer,
                            centerX,
                            centerY,
                            startRadius,
                            endRadius
                    )
                    .setDuration(500)
                    .start();
        } else {
            fab.setImageResource(R.drawable.ic_add_black);
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;
            int endRadius = 0;

            Animator animator = ViewAnimationUtils
                    .createCircularReveal(
                            fabContainer,
                            centerX,
                            centerY,
                            startRadius,
                            endRadius
                    );
            animator.setDuration(200);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fabContainer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
        }
        fabMenuOpen = !fabMenuOpen;
    }

    public void initWebView() {
        WebSettings webSetting = mContentView.getSettings();
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        mContentView.setHorizontalScrollBarEnabled(false);
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head></head><body> ");
//                String html = "<html><head></head><body> <img src=\""+ imagePath + "\"> </body></html>";
        for (Chapter c : chapterList) {
            builder.append("<img src=\"");
            builder.append(c.getUrl());
            builder.append("\">");
        }
        builder.append(" </body></html>");
        String html = "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + builder.toString();
        mContentView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        mContentView.getSettings();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.epub_menu, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
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
                        loadDistinctChapters();
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

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


}

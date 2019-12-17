package net.siji.readView;

import android.animation.Animator;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.siji.adsGg.RewardGgAds;
import net.siji.R;
import net.siji.dao.ItemClickListener;
import net.siji.dialog.LoadingDialog;
import net.siji.dialog.RewardDialog;
import net.siji.model.ApiManager;
import net.siji.model.Chapter;
import net.siji.model.Comic;
import net.siji.sessionApp.SessionManager;
import net.siji.splashScreenView.SplashScreenActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ViewerActivity extends AppCompatActivity implements View.OnClickListener {
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
            frameLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//            if (!mFlag)
//                viewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//            else
//                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
    private boolean mFlag = true;
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
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String API_URL_GET_LIST_CHAPTER = "http://192.168.1.121/siji-server/view/api_get_limit_chapters.php";
    private String API_GET_CONTENT_CHAPTER_URL = "http://192.168.1.121/siji-server/view/api_get_all_page_of_chapter.php";
    private String API_GET_TRANSLATOR_URL = "http://192.168.1.121/siji-server/view/api_get_translator.php";
    private String API_EXECUTE_WALLET = "http://192.168.1.121/siji-server/view/api_wallet_execute.php";
    List<Chapter> chapterList;
    List<Chapter> chapterViewPager;
    int startAt = 0;
    Comic comic;
    String idCustomer;
    String idComic;
    String fcmtoken;
    List<Chapter> distinctChapterList;
    LinkedList<Chapter> linkedList;

    private LinearLayout fabContainer, img_view;
    private FloatingActionButton fab;
    private boolean fabMenuOpen = false;
    private FloatingActionButton exchange_fab, msg_bug_fab, home_fab;
    private ViewPager viewPager;
    private TextView exchange_fb_tv;
    ListView chapterListView;
    ListView translatorListView;
    VerticalChapterAdapter verticalChapterAdapter;
    RecyclerView mRecyclerView;
    RecyclerChapterAdapter recyclerChapterAdapter;
    int quantity;
    TextView tv_title_comic;
    Chapter chapter;
    private Spinner spinnerTranslator;
    private List<String> listTranslator;
    private StringBuilder builderHtml;
    private LinearLayout tabChapter, translatorTab;
    private View line_tab_chapter, line_tab_translator;
    private TextView tv_reading;
    private List<String> stringList;
    private FrameLayout frameLayout_prev, frameLayout_next;
    private int posit = 0;
    private String success;
    private RewardDialog rewardDialog;
    RewardGgAds rewardGgAds;
    private int flagWallet = -3;

    private boolean isShowReward = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer);
        rewardDialog = new RewardDialog(this, getSupportFragmentManager());
        init();
        initFab();
//        initRewardAds();
        final LoadingDialog loadingDialog = new LoadingDialog(getSupportFragmentManager());
        loadingDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadApiUrl();
//                loadTranslator();
                loadDistinctChapters();
                initChapterMenu();
                flagWallet = executeWallet();
                if (flagWallet == -1) {
                    loadingDialog.dismiss();
                    rewardDialog.show();
                    rewardDialog.setCancelable(false);
                } else if (flagWallet >= 0) {
                    loadContentChapter();
                    processData();
                    initTranslator();
                    initViewMode();
                }
                loadingDialog.dismiss();
            }
        }, 2000);


        hide();

    }


    private void loadApiUrl() {
        ApiManager apiManager = new ApiManager();
        API_URL_GET_LIST_CHAPTER = apiManager.API_URL_GET_LIST_CHAPTER;
        API_GET_CONTENT_CHAPTER_URL = apiManager.API_GET_CONTENT_CHAPTER_URL;
        API_GET_TRANSLATOR_URL = apiManager.API_GET_TRANSLATOR_URL;
        API_EXECUTE_WALLET = apiManager.API_EXECUTE_WALLET;
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

    public int executeWallet() {
        int i = -3;
        String chap = String.valueOf(chapter.getChapter());
        String price = String.valueOf(chapter.getPrice());
        WalletAsyncTask walletAsyncTask = new WalletAsyncTask();
        try {
            i = walletAsyncTask.execute(idCustomer, fcmtoken, idComic, chap, price, API_EXECUTE_WALLET).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return i;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return i;
        }
        return i;
    }

    public void loadContentChapter() {
        chapterList = new ArrayList<>();
        float c = chapter.getChapter();
        String chapter = String.valueOf(c);

        try {
            LoadChapterAsyncTask task = new LoadChapterAsyncTask();
            chapterList = task.execute(idCustomer, fcmtoken, idComic, chapter, API_GET_CONTENT_CHAPTER_URL).get();
            success = task.success;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadTranslator() {
        stringList = new ArrayList<>();
        try {
            stringList = new LoadDistincTranslatorAsyncTask().execute(idComic, API_GET_TRANSLATOR_URL).get();
            return;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadTranslator();
    }

    public void initChapterMenu() {
//        mRecyclerView = findViewById(R.id.chapter_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerChapterAdapter = new RecyclerChapterAdapter(this, mRecyclerView, linkedList);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(recyclerChapterAdapter);
//        recyclerChapterAdapter.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                chapter = linkedList.get(position);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        loadContentChapter();
//                        initViewMode();
//                    }
//                }, 100);
//            }
//        });
//        recyclerChapterAdapter.setLoadMore(new ILoadMore() {
//            @Override
//            public void onLoadMore() {
//                linkedList.add(null);
//                recyclerChapterAdapter.notifyItemInserted(linkedList.size() - 1);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        linkedList.remove(linkedList.size() - 1);
//                        recyclerChapterAdapter.notifyItemRemoved(linkedList.size());
//
//                        //Random more data
//                        int index = linkedList.size();
//                        int end = index + 10;
//                        loadDistinctChapters();
//                        recyclerChapterAdapter.notifyDataSetChanged();
//                        recyclerChapterAdapter.setLoaded();
//                    }
//                }, 100);
//            }
//        });
//
        verticalChapterAdapter = new VerticalChapterAdapter(this, linkedList);
        chapterListView.setAdapter(verticalChapterAdapter);
        chapterListView.setOnScrollListener(onScrollListener());
//        verticalChapterAdapter.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
//
//            }
//        });
        chapterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posit = position;
                chapter = linkedList.get(position);
                final LoadingDialog loadingDialog = new LoadingDialog(getSupportFragmentManager());
                loadingDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        flagWallet = executeWallet();
                        if (flagWallet == -1) {
                            loadingDialog.dismiss();
                            rewardDialog.show();
                            rewardDialog.setCancelable(false);
                        } else if (flagWallet >= 0) {
                            loadContentChapter();
                            processData();
                            initTranslator();
                            initViewMode();
                        }
                        loadingDialog.dismiss();
                    }
                }, 100);
                hide();

            }
        });
        chapterListView.setVisibility(View.VISIBLE);
        translatorListView.setVisibility(View.GONE);
        line_tab_translator.setVisibility(View.GONE);
        line_tab_chapter.setVisibility(View.VISIBLE);
    }

    public void initTranslator() {

        VerticalTranslatorAdapter adapter = new VerticalTranslatorAdapter(this, listTranslator);
        translatorListView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                String translator = listTranslator.get(position);
                reloadByTranslator(translator);
                initViewMode();
            }
        });

    }

    public void init() {
        startAt = 0;
        chapter = new Chapter();
        chapter = (Chapter) getIntent().getSerializableExtra("chapter");
        SessionManager sessionManager = new SessionManager(this);
        idCustomer = sessionManager.getReaded("idUser");
        fcmtoken = sessionManager.getReaded("tokenfcm");

        comic = (Comic) getIntent().getSerializableExtra("comic");
        idComic = String.valueOf(comic.getId());
        distinctChapterList = new ArrayList<>();
        linkedList = new LinkedList<>();
        mVisible = true;
        tv_title_comic = findViewById(R.id.tv_nav_title_comic);
        if (comic.getName() != null && !comic.getName().isEmpty())
            tv_title_comic.setText(comic.getName().toUpperCase().trim());
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (WebView) findViewById(R.id.fullscreen_content);
        frameLayout = findViewById(R.id.frame_layout);
        viewPager = findViewById(R.id.view_pager);
        spinnerTranslator = findViewById(R.id.spin_translator);
        translatorListView = findViewById(R.id.listview_translator);
        chapterListView = findViewById(R.id.listview_chapter);
        tabChapter = findViewById(R.id.tab_chapter);
        translatorTab = findViewById(R.id.tab_translator);
        line_tab_chapter = findViewById(R.id.line_tab_chapter);
        line_tab_translator = findViewById(R.id.line_tab_translator);
        tv_reading = findViewById(R.id.chapter_reading_tv);
        frameLayout_next = findViewById(R.id.action_next);
        frameLayout_prev = findViewById(R.id.action_prev);
        frameLayout_next.setOnClickListener(this);
        frameLayout_prev.setOnClickListener(this);
        tabChapter.setOnClickListener(this);
        translatorTab.setOnClickListener(this);
        mContext = this;


    }

    public void initFab() {
        fabContainer = (LinearLayout) findViewById(R.id.fabContainerLayout);
        exchange_fb_tv = (TextView) findViewById(R.id.tv_exchange_fab_mode);
        fab = (FloatingActionButton) findViewById(R.id.fab_view_act);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                toggleFabMenu();
            }
        });

        exchange_fab = (FloatingActionButton) findViewById(R.id.exchange_fab_mode);
        exchange_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlag = !mFlag;
                initViewMode();
            }
        });
        msg_bug_fab = (FloatingActionButton) findViewById(R.id.msg_bug_fab);
        msg_bug_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                String chap = String.valueOf(chapter.getChapter());
                NotificationBugDialog bugDialog = new NotificationBugDialog(getApplicationContext(), idCustomer, idComic, chap);
                bugDialog.show(getSupportFragmentManager(), "dialogMsgBug");
            }
        });
        home_fab = findViewById(R.id.home_fab);
        home_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewerActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initSpinner() {
//        String o = "-1";
//        List<String> list = new ArrayList<>();
//        for (Chapter c : chapterList) {
//
//            String str = c.getTranslator().trim();
//            if (!str.equals(o)) {
//                list.add("Translator : " + str);
//                o = str;
//            }
//        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listTranslator);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerTranslator.setAdapter(adapter);
        spinnerTranslator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hide();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hide();
            }
        });
    }

    public void processData() {
        int i = 0;
        String s = getString(R.string.chapter) + " " + String.valueOf(chapter.getChapter());
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        tv_reading.setText(s);
        String o = chapterList.get(0).getTranslator().trim();
        listTranslator = new ArrayList<>();
        chapterViewPager = new ArrayList<>();
        builderHtml = new StringBuilder();
        builderHtml.append("<html><head></head><body> ");
        listTranslator.add(o);
        for (Chapter c : chapterList) {

            String str = c.getTranslator().trim();
            if (!str.equals(o)) {
                listTranslator.add(str);
                i++;
                o = str;
            } else {
                if (i == 0) {
                    builderHtml.append("<img src=\"");
                    builderHtml.append(c.getUrl());
                    builderHtml.append("\">");
                    chapterViewPager.add(c);
                }
            }
        }
        builderHtml.append(" </body></html>");

    }

    public void reloadByTranslator(String translator) {
        chapterViewPager = new ArrayList<>();
        builderHtml = new StringBuilder();
        builderHtml.append("<html><head></head><body> ");
        for (Chapter c : chapterList) {
            String str = c.getTranslator().trim();
            if (str.equals(translator)) {
                builderHtml.append("<img src=\"");
                builderHtml.append(c.getUrl());
                builderHtml.append("\">");
                chapterViewPager.add(c);
            }
        }
        builderHtml.append(" </body></html>");

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleFabMenu() {
        if (!fabMenuOpen) {
            fab.setImageResource(R.drawable.ic_close_black);
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

    public void initViewMode() {
        if (mFlag) {
            exchange_fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic__line));
            exchange_fb_tv.setText(getString(R.string.title_line_mode));
            initWebView();
        } else {
            exchange_fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_slide_mode));
            exchange_fb_tv.setText(getString(R.string.title_slide_mode));
            initViewPager();
        }
        hide();
    }

    public void initViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
        ImageAdapter imageAdapter = new ImageAdapter(this, chapterViewPager);
        viewPager.setAdapter(imageAdapter);
    }

    public void initWebView() {
        viewPager.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
        WebSettings webSetting = mContentView.getSettings();
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        mContentView.setHorizontalScrollBarEnabled(false);
        String html = "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + builderHtml.toString();
        mContentView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        mContentView.scrollTo(0, 0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
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

    public void hide() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_chapter:
                chapterListView.setVisibility(View.VISIBLE);
                translatorListView.setVisibility(View.GONE);
                line_tab_translator.setVisibility(View.GONE);
                line_tab_chapter.setVisibility(View.VISIBLE);
                break;
            case R.id.tab_translator:
                translatorListView.setVisibility(View.VISIBLE);
                chapterListView.setVisibility(View.GONE);
                line_tab_translator.setVisibility(View.VISIBLE);
                line_tab_chapter.setVisibility(View.GONE);
                break;
            case R.id.action_prev:

                if (posit < linkedList.size() - 1) {
                    posit++;
                    chapter = linkedList.get(posit);
                    final LoadingDialog loadingDialog = new LoadingDialog(getSupportFragmentManager());
                    loadingDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            flagWallet = executeWallet();
                            if (flagWallet == -1) {
                                loadingDialog.dismiss();
                                rewardDialog.show();
                                rewardDialog.setCancelable(false);
                                posit--;
                            } else if (flagWallet >= 0) {
                                loadContentChapter();
                                processData();
                                initTranslator();
                                initViewMode();
                            }
                            loadingDialog.dismiss();
                        }
                    }, 100);
                    hide();

                }
                hide();
                break;
            case R.id.action_next:

                if (posit > 0) {
                    posit--;
                    chapter = linkedList.get(posit);
                    final LoadingDialog loadingDialog = new LoadingDialog(getSupportFragmentManager());
                    loadingDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            flagWallet = executeWallet();
                            if (flagWallet == -1) {
                                loadingDialog.dismiss();
                                rewardDialog.show();
                                rewardDialog.setCancelable(false);
                                posit++;
                            } else if (flagWallet >= 0) {
                                loadContentChapter();
                                processData();
                                initTranslator();
                                initViewMode();
                            }
                            loadingDialog.dismiss();
                        }
                    }, 100);
                    hide();

                }
                hide();
                break;
        }

    }

    public void initRewardAds() {
        rewardGgAds = new RewardGgAds(this);
//        rewardGgAds.setIdUnit(getString(R.string.advertise));
        rewardGgAds.loadAdsReward();
    }

    public void dismissRewardDialog() {
        rewardDialog.dismiss();
        isShowReward = false;
    }

    public void showRewardAds() {
        rewardGgAds.showAdsReward();
        dismissRewardDialog();
    }
}

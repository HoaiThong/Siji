package net.siji.homeView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import net.siji.R;
import net.siji.carouselViewPager.Adapter;
import net.siji.carouselViewPager.CarouselPagerAdapter;
import net.siji.dao.AdvertiseUtils;
import net.siji.imageSliderViewPager.IndicatorView;
import net.siji.imageSliderViewPager.PagesLessException;
import net.siji.model.Advertise;
import net.siji.model.ApiManager;
import net.siji.model.Comic;
import net.siji.model.Header;
import net.siji.sessionApp.SessionManager;
import net.siji.verticalView.VerticalListFragment;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private View view;
    private ViewPager viewPager;
    private IndicatorView indicatorView;
    Timer timer;
    int page = 0, pageAds = 0;
    ArrayList<Header> headerList;
    private Activity mActivity;
    private RecyclerView recyclerViewRank;
    private RecyclerView recyclerViewNew;
    private RecyclerView recyclerViewMost;
    private RecyclerView recyclerViewFull;
    private HorizontalListAdapter horizontalAdapter;
    private GridViewAdapter gridViewAdapter;
    private FragmentManager fragmentManager;
    private String API_URL_RANDOM_COMIC = "http://192.168.1.121/siji-server/view/api_get_comic_by_random.php";
    private String API_GET_LIMIT_COMIC_BY_UPDATE = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_update.php";
    private String API_GET_LIMIT_COMIC_FULL = "http://192.168.1.121/siji-server/view/api_get_limit_comic_full.php";
    private String API_GET_LIMIT_COMIC_BY_VIEW_DAY = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_view_day.php";
    private String API_GET_ADVERTIDE = "http://192.168.1.121/siji-server/view/api_get_advertise.php";
    private String API_GET_HEADER;
    private LinearLayout ll_form_comic_full;
    private LinearLayout ll_form_view_day;
    private LinearLayout ll_form_new_comic;
    private LinearLayout ll_form_random_today;
    private LinearLayout ll_form_view_ads;
    private CardView banner_header;

    //    Carousel
    public final static int LOOPS = 1000;
    public CarouselPagerAdapter adapter;
    public ViewPager pager;
    public static int count = 10; //ViewPager items size
    /**
     * You shouldn't define first page = 0.
     * Let define firstpage = 'number viewpager size' to make endless carousel
     */
    public static int FIRST_PAGE = 10;
    private AdvertiseAdapter advertiseAdapter;
    private ViewPager adsViewPager;
    private Adapter mAdapter;
    private ArrayList<Advertise> advertiseList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home_view, container, false);
        mActivity.setTitle(mActivity.getString(R.string.title_home));
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        SessionManager sessionManager = new SessionManager(mActivity);
        String user = sessionManager.getReaded("user");
        Log.d("uuuuuuuuuuuuuu:", user);
        fragmentManager = getFragmentManager();
        loadApiUrl();
        init();
//        progressBar.setVisibility(View.GONE);
        return view;
    }

    private  void loadApiUrl(){
        ApiManager apiManager=new ApiManager();
        API_URL_RANDOM_COMIC=apiManager.API_GET_COMIC_RANDOM;
        API_GET_ADVERTIDE=apiManager.API_GET_ADVERTIDE;
        API_GET_HEADER=apiManager.API_GET_HEADER;
        API_GET_LIMIT_COMIC_BY_UPDATE=apiManager.API_GET_LIMIT_COMIC_BY_UPDATE;
        API_GET_LIMIT_COMIC_BY_VIEW_DAY=apiManager.API_GET_LIMIT_COMIC_BY_VIEW_DAY;
        API_GET_LIMIT_COMIC_FULL=apiManager.API_GET_LIMIT_COMIC_FULL;
    }


    public void init() {
        viewPager = view.findViewById(R.id.viewPager);
        indicatorView = view.findViewById(R.id.indicator);
        ll_form_comic_full = view.findViewById(R.id.ll_form_comic_full);
        ll_form_random_today = view.findViewById(R.id.ll_hot_today);
        ll_form_new_comic = view.findViewById(R.id.ll_form_new_comic);
        ll_form_view_day = view.findViewById(R.id.ll_form_view_day);
        ll_form_view_ads = view.findViewById(R.id.ll_form_view_ads);
        banner_header = view.findViewById(R.id.banner_header);


        ll_form_new_comic.setOnClickListener(this);
        ll_form_comic_full.setOnClickListener(this);
        ll_form_view_day.setOnClickListener(this);
        ll_form_random_today.setOnClickListener(this);
        initViewPager();
        initRandom();
        initNew();
        initViewsDay();
        initFull();
        initAdvertise();
        pageSwitcher(3);
    }

    public void initAds() {
//        pager = (ViewPager) view.findViewById(R.id.myviewpager);
//
//        //set page margin between pages for viewpager
//        DisplayMetrics metrics = new DisplayMetrics();
//        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int pageMargin = ((metrics.widthPixels / 4) * 2);
//        pager.setPageMargin(-pageMargin);
//        adapter = new CarouselPagerAdapter(mActivity,getFragmentManager());
//        pager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        pager.addOnPageChangeListener(adapter);
//
//        // Set current item to the middle page so we can fling to both
//        // directions left and right
//        pager.setCurrentItem(FIRST_PAGE);
//        pager.setOffscreenPageLimit(3);

        List<Object> list = new ArrayList<>();
        mAdapter = new Adapter(list, mActivity);
        adsViewPager = view.findViewById(R.id.myviewpager);
        adsViewPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_indicator_layout);
        tabLayout.setupWithViewPager(adsViewPager, true);
        ll_form_view_ads.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    public void initRandom() {
        List<Comic> objectArrayList = new ArrayList<>();
        objectArrayList = mActivity.getIntent().getParcelableArrayListExtra("today");
        if (!objectArrayList.isEmpty()) {
            recyclerViewRank = (RecyclerView) view.findViewById(R.id.horizontal_recycler_rate);
            recyclerViewRank.setHasFixedSize(true);
            LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRank.setLayoutManager(horizontalManager);
            horizontalAdapter = new HorizontalListAdapter(mActivity, fragmentManager, objectArrayList);
            recyclerViewRank.setAdapter(horizontalAdapter);
            ll_form_random_today.setVisibility(View.VISIBLE);
        } else ll_form_random_today.setVisibility(View.GONE);
    }

    public void initNew() {
        List<Comic> objectArrayList = new ArrayList<>();
        objectArrayList = mActivity.getIntent().getParcelableArrayListExtra("update");
        if (!objectArrayList.isEmpty()) {
            recyclerViewNew = (RecyclerView) view.findViewById(R.id.horizontal_recycler);

            recyclerViewNew.setHasFixedSize(true);

            //set horizontal LinearLayout as layout manager to creating horizontal list view
            LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewNew.setLayoutManager(horizontalManager);
            horizontalAdapter = new HorizontalListAdapter(mActivity, fragmentManager, objectArrayList);
            recyclerViewNew.setAdapter(horizontalAdapter);
            ll_form_new_comic.setVisibility(View.VISIBLE);
        } else ll_form_new_comic.setVisibility(View.GONE);
    }

    public void initViewsDay() {
        List<Comic> objectArrayList = new ArrayList<>();
        objectArrayList = mActivity.getIntent().getParcelableArrayListExtra("viewday");
        if (!objectArrayList.isEmpty()) {
            recyclerViewMost = (RecyclerView) view.findViewById(R.id.horizontal_recycler_most_view);

            recyclerViewMost.setHasFixedSize(true);

            //set horizontal LinearLayout as layout manager to creating horizontal list view
            LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewMost.setLayoutManager(horizontalManager);
            horizontalAdapter = new HorizontalListAdapter(mActivity, fragmentManager, objectArrayList);
            recyclerViewMost.setAdapter(horizontalAdapter);
            ll_form_view_day.setVisibility(View.VISIBLE);
        } else ll_form_view_day.setVisibility(View.GONE);
    }

    public void initAdvertise() {
        advertiseList = new ArrayList<>();
        adsViewPager = view.findViewById(R.id.myviewpager);
        try {
            advertiseList = new LoadAdvertiseAsyncTask().execute(API_GET_ADVERTIDE).get();
            if (!advertiseList.isEmpty()) {
                AdvertiseAdapter advertiseAdapter = new AdvertiseAdapter(mActivity,advertiseList);
                Log.d("advertise",advertiseList.get(0).getImgUrl());
                adsViewPager.setAdapter(advertiseAdapter);
                TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_indicator_layout);
                tabLayout.setupWithViewPager(adsViewPager, true);
                ll_form_view_ads.setVisibility(View.VISIBLE);
            } else ll_form_view_ads.setVisibility(View.GONE);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initFull() {
        List<Comic> objectArrayList = new ArrayList<>();
        objectArrayList = mActivity.getIntent().getParcelableArrayListExtra("full");
        if (!objectArrayList.isEmpty()) {
            recyclerViewFull = (RecyclerView) view.findViewById(R.id.horizontal_recycler_full);

            recyclerViewFull.setHasFixedSize(true);

            //set horizontal LinearLayout as layout manager to creating horizontal list view
            LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewFull.setLayoutManager(horizontalManager);
            horizontalAdapter = new HorizontalListAdapter(mActivity, fragmentManager, objectArrayList);
            recyclerViewFull.setAdapter(horizontalAdapter);
            ll_form_comic_full.setVisibility(View.VISIBLE);
        } else ll_form_comic_full.setVisibility(View.GONE);
    }

    public void initViewPager() {
        headerList = new ArrayList<>();
        headerList = mActivity.getIntent().getParcelableArrayListExtra("header");
        if (!headerList.isEmpty()) {
            ImageAdapter adapter = new ImageAdapter(mActivity, headerList);
            viewPager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_indicator_line_layout);
            tabLayout.setupWithViewPager(viewPager, true);
            banner_header.setVisibility(View.VISIBLE);
        } else banner_header.setVisibility(View.GONE);
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    @Override
    public void onClick(View v) {
        System.out.println("OnClick");
        VerticalListFragment fragment = new VerticalListFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.ll_hot_today:
                mActivity.getIntent().putExtra("apiUrl", API_URL_RANDOM_COMIC);
                fragmentTransaction.replace(R.id.fragment_content, fragment);
                fragmentTransaction.commit();
                getActivity().setTitle(mActivity.getString(R.string.title_today));
                break;

            case R.id.ll_form_new_comic:
                mActivity.getIntent().putExtra("apiUrl", API_GET_LIMIT_COMIC_BY_UPDATE);
                fragmentTransaction.replace(R.id.fragment_content, fragment);
                fragmentTransaction.commit();
                getActivity().setTitle(mActivity.getString(R.string.truyen_moi));
                break;
            case R.id.ll_form_view_day:
                mActivity.getIntent().putExtra("apiUrl", API_GET_LIMIT_COMIC_BY_VIEW_DAY);
                fragmentTransaction.replace(R.id.fragment_content, fragment);
                fragmentTransaction.commit();
                getActivity().setTitle(mActivity.getString(R.string.truyen_xem_nhieu));
                break;
            case R.id.ll_form_comic_full:
                mActivity.getIntent().putExtra("apiUrl", API_GET_LIMIT_COMIC_FULL);
                fragmentTransaction.replace(R.id.fragment_content, fragment);
                fragmentTransaction.commit();
                getActivity().setTitle(mActivity.getString(R.string.truyen_hoan_thanh));
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (!headerList.isEmpty()) {
                        if (page >= headerList.size()) { // In my case the number of pages are 5
//                        timer.cancel();
                            page = 0;
                            viewPager.setCurrentItem(page);
//                        indicatorView.onPageSelected(page);
//                        // Showing a toast for just testing purpose
//                        Toast.makeText(getApplicationContext(), "Timer stoped",
//                                Toast.LENGTH_LONG).show();
                        } else {
                            page++;
                            viewPager.setCurrentItem(page);
//                        indicatorView.onPageSelected(page);
                        }
                    }
                    if (!advertiseList.isEmpty()) {
                        if (pageAds >= advertiseList.size()) { // In my case the number of pages are 5
//                        timer.cancel();
                            pageAds = 0;
                            adsViewPager.setCurrentItem(pageAds);
//                        indicatorView.onPageSelected(page);
//                        // Showing a toast for just testing purpose
//                        Toast.makeText(getApplicationContext(), "Timer stoped",
//                                Toast.LENGTH_LONG).show();
                        } else {
                            pageAds++;
                            adsViewPager.setCurrentItem(pageAds);
//                        indicatorView.onPageSelected(page);
                        }
                    }
                }
            });

        }
    }
}

package net.siji.homeView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import net.siji.R;
import net.siji.imageSliderViewPager.IndicatorView;
import net.siji.imageSliderViewPager.PagesLessException;
import net.siji.model.Comic;
import net.siji.model.Header;
import net.siji.sessionApp.SessionManager;
import net.siji.verticalView.VerticalListFragment;

import java.lang.reflect.Field;
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
    int page = 0;
    ArrayList<Header> headerList;
    private Activity mActivity;
    private RecyclerView recyclerViewRank;
    private RecyclerView recyclerViewNew;
    private RecyclerView recyclerViewMost;
    private RecyclerView recyclerViewFull;
    private HorizontalListAdapter horizontalAdapter;
    private GridViewAdapter gridViewAdapter;
    private FragmentManager fragmentManager;
    private String API_RANK_URL = "http://192.168.0.110/KindleServer/view/rank_manga.php";
    private String count = "10";
    private String API_GET_LIMIT_COMIC_BY_UPDATE = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_update.php";
    private String API_GET_LIMIT_COMIC_FULL = "http://192.168.1.121/siji-server/view/api_get_limit_comic_full.php";
    private String API_GET_LIMIT_COMIC_BY_VIEW_DAY = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_view_day.php";
    private LinearLayout ll_form_comic_full;
    private LinearLayout ll_form_view_day;
    private LinearLayout ll_form_new_comic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home_view, container, false);
        mActivity.setTitle(mActivity.getString(R.string.title_home));
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        SessionManager sessionManager = new SessionManager(mActivity);
        String user = sessionManager.getReaded("user");
        Log.d("uuuuuuuuuuuuuu:", user);
        fragmentManager = getFragmentManager();
        init();
//        progressBar.setVisibility(View.GONE);
        return view;
    }

    public void init() {
        viewPager = view.findViewById(R.id.viewPager);
        indicatorView = view.findViewById(R.id.indicator);
        ll_form_comic_full = view.findViewById(R.id.ll_form_comic_full);
        ll_form_new_comic = view.findViewById(R.id.ll_form_new_comic);
        ll_form_view_day = view.findViewById(R.id.ll_form_view_day);
        ll_form_new_comic.setOnClickListener(this);
        ll_form_comic_full.setOnClickListener(this);
        ll_form_view_day.setOnClickListener(this);
        initViewPager();
//        initRank();
        initNew();
        initViewsDay();
        initFull();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    public void initRank() {
        List<Comic> objectArrayList = new ArrayList<>();
        objectArrayList = mActivity.getIntent().getParcelableArrayListExtra("rank");
        if (objectArrayList.isEmpty()) {
            recyclerViewRank = (RecyclerView) view.findViewById(R.id.horizontal_recycler_rate);
            recyclerViewRank.setHasFixedSize(true);
            LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRank.setLayoutManager(horizontalManager);
            horizontalAdapter = new HorizontalListAdapter(mActivity, fragmentManager, objectArrayList);
            recyclerViewRank.setAdapter(horizontalAdapter);
            LinearLayout linearLayout = view.findViewById(R.id.ll_rank);
            linearLayout.setVisibility(View.VISIBLE);
        }
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
        }
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
        }
    }

    public void initViewPager() {
        headerList = new ArrayList<>();
        headerList = mActivity.getIntent().getParcelableArrayListExtra("header");
        if (!headerList.isEmpty()) {
            ImageAdapter adapter = new ImageAdapter(mActivity, headerList);
            viewPager.setAdapter(adapter);
            try {
                indicatorView.setViewPager(viewPager);
            } catch (PagesLessException e) {
                e.printStackTrace();
            }
            pageSwitcher(3);
        }
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

    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            mActivity.runOnUiThread(new Runnable() {
                public void run() {

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
            });

        }
    }
}

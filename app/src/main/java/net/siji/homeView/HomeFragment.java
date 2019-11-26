package net.siji.homeView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import net.siji.R;
import net.siji.imageSliderViewPager.IndicatorView;
import net.siji.imageSliderViewPager.PagesLessException;
import net.siji.sessionApp.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private ListView listView;
    private View view;
    private ViewPager viewPager;
    private IndicatorView indicatorView;
    Timer timer;
    int page = 0;
    ArrayList<String> listImageSlider;
    private Activity mActivity;
    private RecyclerView recyclerViewNew;
    private RecyclerView recyclerViewMost;
    private RecyclerView recyclerViewFull;
    private RecyclerView recyclerViewCountry;
    private RecyclerView recyclerViewOtherCountry;
    private HorizontalListAdapter horizontalAdapter;
    private GridViewAdapter gridViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home_view, container, false);
        SessionManager sessionManager=new SessionManager(mActivity);
        String user =sessionManager.getReaded("user");
        Log.d("uuuuuuuuuuuuuu:",user);
        Toast.makeText(mActivity,user,Toast.LENGTH_SHORT).show();

        init();
        return view;
    }

    public void init() {
        viewPager = view.findViewById(R.id.viewPager);
        indicatorView = view.findViewById(R.id.indicator);
        initViewPager();
        initNew();
        initMostViews();
        initFull();
        initCountry();
        initOtherCountry();

    }

    public void initNew() {
        recyclerViewNew = (RecyclerView) view.findViewById(R.id.horizontal_recycler);

        recyclerViewNew.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNew.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListAdapter(mActivity);
        recyclerViewNew.setAdapter(horizontalAdapter);
    }

    public void initMostViews() {
        recyclerViewMost = (RecyclerView) view.findViewById(R.id.horizontal_recycler_most_view);

        recyclerViewMost.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMost.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListAdapter(mActivity);
        recyclerViewMost.setAdapter(horizontalAdapter);
    }

    public void initFull() {
        recyclerViewFull = (RecyclerView) view.findViewById(R.id.horizontal_recycler_full);

        recyclerViewFull.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFull.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListAdapter(mActivity);
        recyclerViewFull.setAdapter(horizontalAdapter);
    }

    public void initCountry() {
        recyclerViewCountry = (RecyclerView) view.findViewById(R.id.recycler_in_country);
        recyclerViewCountry.setNestedScrollingEnabled(false);
        recyclerViewCountry.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 3);
        recyclerViewCountry.setLayoutManager(gridLayoutManager);
        gridViewAdapter = new GridViewAdapter(mActivity);
        recyclerViewCountry.setAdapter(gridViewAdapter);
    }

    public void initOtherCountry() {
        recyclerViewOtherCountry = (RecyclerView) view.findViewById(R.id.recycler_other_country);
        recyclerViewOtherCountry.setNestedScrollingEnabled(false);
        recyclerViewOtherCountry.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        recyclerViewOtherCountry.setLayoutManager(gridLayoutManager);
        gridViewAdapter = new GridViewAdapter(mActivity);
        recyclerViewOtherCountry.setAdapter(gridViewAdapter);
    }

    public void initViewPager() {
        listImageSlider = getImageSlider();
        ImageAdapter adapter = new ImageAdapter(mActivity, listImageSlider);
        viewPager.setAdapter(adapter);
        try {
            indicatorView.setViewPager(viewPager);
        } catch (PagesLessException e) {
            e.printStackTrace();
        }
        pageSwitcher(3);
    }

    public ArrayList<String> getImageSlider() {
        ArrayList<String> list = new ArrayList<>();
        list.add("https://i-giaitri.vnecdn.net/2019/02/25/ngoc-diem-2-1551065105_r_680x0.jpg");
        list.add("https://icdn.dantri.com.vn/k:487bd2df65/2016/08/08/img-6481-copy-1470629461604/hoahaungocdiemlonglaylammchoahaubansacviet.jpg");
        return list;
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            mActivity.runOnUiThread(new Runnable() {
                public void run() {

                    if (page >= listImageSlider.size()) { // In my case the number of pages are 5
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

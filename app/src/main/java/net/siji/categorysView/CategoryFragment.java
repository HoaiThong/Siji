package net.siji.categorysView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import net.siji.R;
import net.siji.dao.ItemClickListener;
import net.siji.homeView.GridViewAdapter;
import net.siji.homeView.HorizontalListAdapter;
import net.siji.homeView.ImageAdapter;
import net.siji.imageSliderViewPager.IndicatorView;
import net.siji.imageSliderViewPager.PagesLessException;
import net.siji.model.ApiManager;
import net.siji.model.Comic;
import net.siji.sessionApp.SessionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class CategoryFragment extends Fragment {
    private ListView listView;
    private View view;
    private ViewPager viewPager;
    private IndicatorView indicatorView;
    Timer timer;
    int page = 0;
    ArrayList<String> listImageSlider;
    private Activity mActivity;
    private RecyclerView recyclerView;
    private HorizontalListCategoryAdapter horizontalAdapter;
    private GridViewAdapter gridViewAdapter;
    private ItemClickListener itemClickListener;
    private List<String> listCategory;

    private String API_GET_CATEGORYS = "http://192.168.1.121/siji-server/view/api_get_categorys.php";
    private String API_ALL_URL = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_update.php";
    private String API_URL = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_category.php";
    private static int start = 0;
    private int quantity;
    private LinkedList<Comic> linkedList;
    private VerticalListAdapter verticalListAdapter;
    String nameCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_category_view, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        start=0;
        SessionManager sessionManager = new SessionManager(mActivity);
        String user = sessionManager.getReaded("user");
        Log.d("uuuuuuuuuuuuuu:", user);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadApiUrl();
                initCategory();
                init();
            }
        }, 100);
        return view;
    }


    private  void loadApiUrl(){
        ApiManager apiManager=new ApiManager();
        API_URL=apiManager.API_GET_COMIC_BY_CATEGORY;
        API_GET_CATEGORYS=apiManager.API_GET_CATEGORYS;
    }
    public void getCategory() {
        try {
            listCategory = new LoadCategorysAsyncTask().execute(API_GET_CATEGORYS).get();
            nameCategory = listCategory.get(0);
            Log.e("nameCategory", nameCategory);
        } catch (ExecutionException e) {
            getCategory();
            e.printStackTrace();
        } catch (InterruptedException e) {
            getCategory();
            e.printStackTrace();
        }
    }

    public void initCategory() {
        listCategory = new ArrayList<>();
        getCategory();
        recyclerView = (RecyclerView) view.findViewById(R.id.horizontal_recycler_category);

        recyclerView.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        final LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListCategoryAdapter(mActivity, listCategory);
        recyclerView.setAdapter(horizontalAdapter);
        horizontalAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                nameCategory = listCategory.get(position).trim();
                start = 0;
                init();
//                view.findViewById(R.id.divider_horizontal).setVisibility(View.VISIBLE);
//                view.findViewById(R.id.divider_horizontal).setBackgroundColor(Color.parseColor("#567845"));
//                viewHolder.mView.setVisibility(View.VISIBLE);
            }
        });
    }

    private List<Comic> getListComic(String nameCategory) {
        List<Comic> listUpdate = new ArrayList<Comic>();
        try {
            String startAt = String.valueOf(start);
            listUpdate = new LoadDataAsyncTask().execute(nameCategory, startAt, API_URL).get();
            quantity = listUpdate.size();
            start = start + quantity;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        linkedList.addAll(listUpdate);
        return listUpdate;
    }

    public void init() {
        linkedList = new LinkedList<>();
        getListComic(nameCategory);
        mActivity.setTitle(nameCategory);
        listView = view.findViewById(R.id.list_view_comic);
        verticalListAdapter = new VerticalListAdapter(mActivity, linkedList);
        listView.setAdapter(verticalListAdapter);
        listView.setOnScrollListener(onScrollListener());

    }

    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = listView.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count - threshold && quantity > 0) {
                        // Execute LoadMoreDataTask AsyncTask
                        getListComic(nameCategory);
                        verticalListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }


    public void show(int position) {
        Toast.makeText(mActivity, String.valueOf(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }
}

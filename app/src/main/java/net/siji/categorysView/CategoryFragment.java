package net.siji.categorysView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import net.siji.sessionApp.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_category_view, container, false);
        SessionManager sessionManager = new SessionManager(mActivity);
        String user = sessionManager.getReaded("user");
        Log.d("uuuuuuuuuuuuuu:", user);
        listCategory=new ArrayList<>();
        listCategory.add("All");
        listCategory.add("All");
        listCategory.add("All");
        listCategory.add("All");
        listCategory.add("All");
        initCategory();
        return view;
    }

    public void initCategory() {
        recyclerView = (RecyclerView) view.findViewById(R.id.horizontal_recycler_category);

        recyclerView.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        final LinearLayoutManager horizontalManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListCategoryAdapter(mActivity,listCategory);
        recyclerView.setAdapter(horizontalAdapter);
        horizontalAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(mActivity, String.valueOf(position), Toast.LENGTH_SHORT).show();
                System.out.println(isLongClick);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }
}

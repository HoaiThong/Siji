package net.siji.verticalView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import net.siji.R;
import net.siji.model.Comic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VerticalListFragment extends Fragment {
    private ListView listView;
    private View view;
    private List<Comic> objectList;
    private String API_NEW_MANGA = "";
    private String API_FULL_MANGA = "";
    private String API_MOST_VIEW_MANGA = "";
    private String API_RANK_MANGA = "";
    private String API_URL;
    private Activity mActivity;
    private static int start = 0;
    private int quantity;
    private LinkedList<Comic> linkedList;
    private VerticalListAdapter verticalListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_list_manga, container, false);
        API_URL = mActivity.getIntent().getStringExtra("apiUrl");
        Log.d("api", API_URL);
        start = 0;
        init();
        return view;
    }

    private List<Comic> getListComic() {
        List<Comic> listUpdate = new ArrayList<Comic>();
        try {
            String startAt = String.valueOf(start);
            listUpdate = new LoadDataAsyncTask().execute(startAt, API_URL).get();
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
        objectList = getListComic();
        listView = view.findViewById(R.id.list_view_manga);
        verticalListAdapter = new VerticalListAdapter(mActivity, linkedList);
        listView.setAdapter(verticalListAdapter);
        listView.setOnScrollListener(onScrollListener());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
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
                        getListComic();
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
}

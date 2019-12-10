package net.siji.libraryView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import net.siji.R;
import net.siji.model.Comic;
import net.siji.sessionApp.SessionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LibraryFragment
        extends Fragment {
    private ListView listView;
    private View view;
    private String API_URL="http://192.168.1.121/siji-server/view/api_get_limit_subcribe_comic.php";
    private Activity mActivity;
    private static int start = 0;
    private int quantity;
    private LinkedList<Comic> linkedList;
    private LibraryListAdapter libraryListAdapter;
    String idCustomer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_list_manga, container, false);
        mActivity.setTitle(mActivity.getString(R.string.bo_suu_tap));
        SessionManager sessionManager=new SessionManager(mActivity);
        idCustomer=sessionManager.getReaded("idUser");
        start = 0;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                init();
            }
        }, 100);
        return view;
    }

    private List<Comic> getListComic() {
        List<Comic> listUpdate = new ArrayList<Comic>();
        try {
            String startAt = String.valueOf(start);
            listUpdate = new LoadDataAsyncTask().execute(idCustomer,startAt, API_URL).get();
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
        getListComic();
        listView = view.findViewById(R.id.list_view_manga);
        libraryListAdapter= new LibraryListAdapter(mActivity, linkedList);
        listView.setAdapter(libraryListAdapter);
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
                        libraryListAdapter.notifyDataSetChanged();
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

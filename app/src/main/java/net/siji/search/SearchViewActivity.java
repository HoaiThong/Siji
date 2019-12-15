package net.siji.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.siji.R;
import net.siji.model.ApiManager;
import net.siji.model.Comic;
import net.siji.splashScreenView.SplashScreenActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchViewActivity extends AppCompatActivity {
    private EditText searchEditText;
    private ListView listView;
    private static int start = 0;
    private int quantity;
    private LinkedList<Comic> linkedList;
    private VerticalListAdapter verticalListAdapter;
    private String API_URL_SEARCH = "http://192.168.1.121/siji-server/view/api_get_limit_comic_by_search.php";
    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle(getString(R.string.search));
        ApiManager apiManager=new ApiManager();
        API_URL_SEARCH=apiManager.API_URL_SEARCH;
        linkedList = new LinkedList<>();
        init();

    }

    public void loadListComicBySearch() {
        List<Comic> listUpdate = new ArrayList<Comic>();
        try {
            String startAt = String.valueOf(start);
            listUpdate = new LoadSearchAsyncTask().execute(searchText, startAt, API_URL_SEARCH).get();
            quantity = listUpdate.size();
            Log.d("quantity", String.valueOf(start));
            start = start + quantity;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        linkedList.addAll(listUpdate);
    }

    public void init() {
        searchEditText = findViewById(R.id.search_edt);
        searchEditText.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchText = searchEditText.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !searchText.equals("")) {
                    Toast.makeText(getApplicationContext(), searchText, Toast.LENGTH_SHORT).show();
                    linkedList = new LinkedList<>();
                    start = 0;
                    loadListComicBySearch();
                    listView = findViewById(R.id.list_view_search);
                    verticalListAdapter = new VerticalListAdapter(SearchViewActivity.this, linkedList);

                    verticalListAdapter.notifyDataSetChanged();
                    listView.setAdapter(verticalListAdapter);
                    listView.setOnScrollListener(onScrollListener());
                    return true;
                }
                return false;
            }
        });


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
                        loadListComicBySearch();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

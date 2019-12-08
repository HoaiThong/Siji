package net.siji.readView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.siji.R;
import net.siji.dao.ItemClickListener;
import net.siji.model.Chapter;

import java.util.List;

public class RecyclerChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public List<Chapter> mItemList;
    private Activity mActivity;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;
    boolean isLoading;
    ILoadMore loadMore;
    private ItemClickListener itemClickListener;


    public RecyclerChapterAdapter(Activity mActivity, RecyclerView recyclerView, List<Chapter> itemList) {
        this.mActivity = mActivity;
        mItemList = itemList;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();

    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_chapter;
        private TextView tv_price;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            view.setOnClickListener(this);
            tv_chapter = view.findViewById(R.id.tv_chapter_title);
            tv_price = view.findViewById(R.id.tv_price_chapter);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        viewHolder.progressBar.setVisibility(View.VISIBLE);
    }

    private void populateItemRows(ItemViewHolder holder, int position) {

        Chapter c = mItemList.get(position);
        int num = (int) (c.getChapter() * 10);
        if (num % 10 == 0) {
            String name = mActivity.getString(R.string.chapter) + " " + (int) c.getChapter();
            holder.tv_chapter.setText(name);
        } else {
            String name = mActivity.getString(R.string.chapter) + " " + c.getChapter();
            holder.tv_chapter.setText(name);
        }
        int coin = (int) c.getPrice();
        if (coin > 0) {
            holder.tv_price.setVisibility(View.VISIBLE);
            holder.tv_price.setText(String.valueOf(coin));
        } else holder.tv_price.setVisibility(View.GONE);

    }


}
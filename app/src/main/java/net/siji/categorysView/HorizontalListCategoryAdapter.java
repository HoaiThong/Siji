package net.siji.categorysView;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import net.siji.R;
import net.siji.dao.ItemClickListener;
import net.siji.inforView.InforActivity;

import java.util.List;


public class HorizontalListCategoryAdapter extends RecyclerView.Adapter<HorizontalListCategoryAdapter.ViewHolder> {

    private Activity activity;
    private ItemClickListener itemClickListener;
    private List<String> list;

    public HorizontalListCategoryAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_horizontal_list_category, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String label = list.get(position);
        viewHolder.mTextView.setText(label);
        viewHolder.mView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private View mView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tv_title_category);
            mView = view.findViewById(R.id.divider_horizontal);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
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


public class HorizontalListCategoryAdapter extends RecyclerView.Adapter<HorizontalListCategoryAdapter.ViewHolder> {

    private Activity activity;
    private ItemClickListener itemClickListener;

    public HorizontalListCategoryAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_horizontal_list_category, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tv_title_category);
            view.setOnClickListener( this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }
    }
}
package net.siji.homeView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import net.siji.MainActivity;
import net.siji.R;
import net.siji.dao.ItemClickListener;
import net.siji.inforView.InforActivity;
import net.siji.model.Comic;
import net.siji.verticalView.VerticalListFragment;

import java.util.List;


public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {

    private Activity activity;
    private ItemClickListener itemClickListener;
    private FragmentManager fragmentManager;
    private List<Comic> comicList;

    public HorizontalListAdapter(Activity activity, FragmentManager fragmentManager, List<Comic> comicList) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.comicList = comicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_horizontal_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Comic c = comicList.get(position);
        viewHolder.title_tv.setText(c.getName());
        Glide.with(activity)
                .load(c.getIconUrl())
                .into(viewHolder.imageView);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Comic c = comicList.get(position);
                Intent intent = new Intent(activity, InforActivity.class);
                Bundle data=new Bundle();
                data.putSerializable("comic",c);
                intent.putExtras(data);
                activity.startActivity(intent);
                activity.finish();
//                VerticalListFragment fragment = new VerticalListFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_content, fragment);
//                fragmentTransaction.commit();
//                Toast.makeText(activity, "Position clicked: " + c.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayout;
        private ImageView imageView;
        private TextView title_tv;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.layout);
            imageView = view.findViewById(R.id.img_comic);
            title_tv = view.findViewById(R.id.tv_title_comic);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
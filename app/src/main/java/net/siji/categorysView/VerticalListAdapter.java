package net.siji.categorysView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import net.siji.R;
import net.siji.inforView.InforActivity;
import net.siji.model.Comic;

import java.util.List;

public class VerticalListAdapter extends BaseAdapter {

    private List<Comic> listData;
    private LayoutInflater layoutInflater;
    private Activity mActivity;
    private ViewHolder holder;

    public VerticalListAdapter(Activity aContext, List<Comic> listData) {
        this.mActivity = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_vertical_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Comic c = listData.get(position);
        holder.tv_title.setText(c.getName().trim());
        holder.tv_author.setText(c.getAuthor().trim());
        holder.tv_status.setText(c.getNewChapter().trim());

        Glide.with(mActivity)
                .load(c.getIconUrl())
                .into(holder.imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, InforActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("comic", c);
                intent.putExtras(data);
                mActivity.startActivity(intent);
                mActivity.finish();
            }
        });

        return convertView;
    }


    static class ViewHolder {
        private ImageView imageView;
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_kind;
        private TextView tv_status;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.icon_comic_img);
            tv_title = view.findViewById(R.id.tv_detail_title);
            tv_author = view.findViewById(R.id.tv_detail_author);
            tv_kind = view.findViewById(R.id.tv_detail_kind);
            tv_status = view.findViewById(R.id.tv_detail_status);
//            view.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), false);
//        }
    }


}


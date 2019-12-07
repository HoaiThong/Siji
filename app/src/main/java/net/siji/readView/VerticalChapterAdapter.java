package net.siji.readView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.siji.R;
import net.siji.inforView.InforActivity;
import net.siji.model.Chapter;
import net.siji.model.Comic;

import java.util.List;

public class VerticalChapterAdapter extends BaseAdapter {

    private List<Chapter> listData;
    private LayoutInflater layoutInflater;
    private Activity mActivity;
    private ViewHolder holder;

    public VerticalChapterAdapter(Activity aContext, List<Chapter> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_chapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Chapter c = listData.get(position);
        int num = (int) (c.getChapter() * 10);
        if (num % 10 == 0) {
            String name = mActivity.getString(R.string.chapter) + " " + (int)c.getChapter();
            holder.tv_chapter.setText(name);
        } else {
            String name = mActivity.getString(R.string.chapter) + " " + c.getChapter();
            holder.tv_chapter.setText(name);
        }
        int coin = (int) c.getPrice();
        holder.tv_price.setText(String.valueOf(coin));
        return convertView;
    }


    static class ViewHolder {
        private TextView tv_chapter;
        private TextView tv_price;

        public ViewHolder(View view) {
            tv_chapter = view.findViewById(R.id.tv_chapter_title);
            tv_price = view.findViewById(R.id.tv_price_chapter);

//            view.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), false);
//        }
    }


}


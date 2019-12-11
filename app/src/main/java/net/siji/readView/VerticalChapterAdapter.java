package net.siji.readView;

import android.app.Activity;
import android.content.Context;
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
import net.siji.dao.ItemClickListener;
import net.siji.inforView.InforActivity;
import net.siji.model.Chapter;
import net.siji.model.Comic;

import java.util.List;

public class VerticalChapterAdapter extends BaseAdapter {

    private List<Chapter> listData;
    private LayoutInflater layoutInflater;
    private Context mActivity;
    private ViewHolder holder;
    private int rowIndex=0;
    private ItemClickListener itemClickListener;

    public VerticalChapterAdapter(Context aContext, List<Chapter> listData) {
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
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                notifyDataSetChanged();
//                if (itemClickListener != null) {
//                    itemClickListener.onClick(v, position, false);
//                    rowIndex = position;
//                }
//            }
//        });
//        if (rowIndex == position) {
//            holder.tv_chapter.setTextColor(mActivity.getResources().getColor(R.color.red));
//        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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


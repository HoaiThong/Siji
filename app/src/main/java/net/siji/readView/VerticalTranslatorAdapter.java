package net.siji.readView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import net.siji.R;
import net.siji.dao.ItemClickListener;
import net.siji.model.Chapter;

import java.util.List;

public class VerticalTranslatorAdapter extends BaseAdapter {

    private List<String> listData;
    private LayoutInflater layoutInflater;
    private Context mActivity;
    private ViewHolder holder;
    private  int rowIndex=0;
    private ItemClickListener itemClickListener;

    public VerticalTranslatorAdapter(Context aContext, List<String> listData) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_translator, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowIndex=position;
                notifyDataSetChanged();
                if (itemClickListener != null) {
                    itemClickListener.onClick(v, position, false);
                }
            }
        });
        if (rowIndex == position) {
           holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        String c = listData.get(position);
        holder.tv_translator.setText(c.trim());
        return convertView;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder {
        private TextView tv_translator;
        private CheckBox checkBox;

        public ViewHolder(View view) {
            tv_translator = view.findViewById(R.id.tv_translator_title);
            checkBox = view.findViewById(R.id.checkbox);

//            view.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), false);
//        }
    }


}


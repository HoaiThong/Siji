package net.siji.verticalView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import net.siji.R;

import java.util.List;

public class VerticalListAdapter extends BaseAdapter {

    private List<Integer> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder holder;

    public VerticalListAdapter(Context aContext, List<Integer> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int action = this.listData.get(position);
        String str = context.getString(action);

        return convertView;
    }



    static class ViewHolder {
        ImageView imageView;
    }


}


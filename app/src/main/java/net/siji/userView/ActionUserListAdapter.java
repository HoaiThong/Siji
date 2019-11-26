package net.siji.userView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import net.siji.R;

import java.util.List;

public class ActionUserListAdapter extends BaseAdapter {

    private List<Integer> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder holder;

    public ActionUserListAdapter(Context aContext, List<Integer> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_action_user_layout, null);
            holder = new ViewHolder();
            holder.actionUserView = (TextView) convertView.findViewById(R.id.action_user_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int action = this.listData.get(position);
        setIconDrawable(action);
        String str = context.getString(action);
        holder.actionUserView.setText(str);

        return convertView;
    }

    private void setIconDrawable(int iconDrawable) {
        switch (iconDrawable) {
            case R.string.logout:
                holder.actionUserView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_exit_to_app_black, 0, 0, 0);
                break;
            case R.string.profile:
                holder.actionUserView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_man_user, 0, R.drawable.ic_chevron_right_black, 0);
//                holder.actionUserView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_chevron_right_black, 0);
                break;
            case R.string.user_notification:
                holder.actionUserView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, R.drawable.ic_chevron_right_black, 0);
//                holder.actionUserView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_chevron_right_black, 0);
                break;
            case R.string.policy:
                holder.actionUserView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_security, 0, 0, 0);
                break;
        }
    }


    static class ViewHolder {
        TextView actionUserView;
    }


}

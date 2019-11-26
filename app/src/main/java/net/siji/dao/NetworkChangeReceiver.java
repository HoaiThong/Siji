package net.siji.dao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    NetworkUtil networkUtil;

    @Override
    public void onReceive(Context context, Intent intent) {
        networkUtil = new NetworkUtil(context);
        if (!networkUtil.isNetworkConnected())
//            Toast.makeText(context, "Network Available!", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Network is Unavailable !", Toast.LENGTH_SHORT).show();
    }
}
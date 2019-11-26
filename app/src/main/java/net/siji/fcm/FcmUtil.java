package net.siji.fcm;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import net.siji.R;


public class FcmUtil {
    Activity mActivity;
    private String TAG = "MainActivity";
    String token;

    public FcmUtil(Activity activity) {

        this.mActivity = activity;
        TAG = activity.getLocalClassName();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void accessToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        setToken(token);
                        // Log and toast
//                        String msg = mActivity.getString(R.string.msg_token_fmt, token);
                        Log.d("token id :", token);
//                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void subscribe(final String strTopic) {
        FirebaseMessaging.getInstance().subscribeToTopic(strTopic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = mActivity.getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = mActivity.getString(R.string.msg_subscribe_failed);
                            subscribe(strTopic);
                        }
                        Log.d(TAG, msg);
//                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END subscribe_topics]
    }
}

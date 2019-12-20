package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import net.siji.R;
import net.siji.dao.HttpHander;
import net.siji.model.ApiManager;
import net.siji.model.Chapter;
import net.siji.readView.ViewerActivity;
import net.siji.readView.WalletAsyncTask;
import net.siji.sessionApp.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RewardDialog extends DialogFragment implements View.OnClickListener {
    private Activity mActivity;
    private View view;
    private TextView msg_tv;
    private FragmentManager fragmentManager;
    private RewardedAd rewardedAd;
    private String idCustomer;
    private String fcmtoken;
    private String API_URL_UPDATE_REWARD_ADS="";

    public RewardDialog(Activity context) {
        this.mActivity = context;
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    public RewardDialog(Activity mActivity, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.mActivity = mActivity;
        SessionManager sessionManager = new SessionManager(mActivity);
        idCustomer = sessionManager.getReaded("idUser");
        fcmtoken = sessionManager.getReaded("tokenfcm");
        API_URL_UPDATE_REWARD_ADS=new ApiManager().API_URL_UPDATE_REWARD_ADS;
        loadAdsReward();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_reward, null);
        msg_tv = (TextView) view.findViewById(R.id.tv_msg_dialog);
        view.findViewById(R.id.reward_dialog_btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.reward_dialog_btn_ok).setOnClickListener(this);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        return builder.create();
    }


    public void show() {
        this.show(fragmentManager, "dialog");
        this.setCancelable(false);
    }

    public void loadAdsReward() {
        rewardedAd = new RewardedAd(this.mActivity, mActivity.getString(R.string.ADMOB_UNIT_REWARD));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reward_dialog_btn_cancel:
                ((ViewerActivity) getActivity()).dismissRewardDialog();
                break;
            case R.id.reward_dialog_btn_ok:
//                ((ViewerActivity)getActivity()).showRewardAds();
                showAdsReward();
                this.dismiss();
                break;
        }
    }

    public void showAdsReward() {
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                public void onRewardedAdClosed() {
                    // Ad closed.
                    loadAdsReward();
                    ((ViewerActivity) getActivity()).hide();
                }

                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    int rewardCoin = reward.getAmount();
                    String str=String.valueOf(rewardCoin);
                    Log.d("rewardCoin", str);

                    new  WalletAsyncTask().execute(idCustomer,fcmtoken,str,API_URL_UPDATE_REWARD_ADS);

                }

                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display
                }
            };
            rewardedAd.show(mActivity, adCallback);
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }

    protected class WalletAsyncTask extends AsyncTask<String, String, Void> {
        HttpHander httpHander = new HttpHander();
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_INSERT = "update";

        private String quantity;
        private String API_URL;
        private List<NameValuePair> params;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All mangas from url
         *
         * @return
         */
        protected Void doInBackground(String... args) {
            idCustomer = args[0];
            fcmtoken = args[1];
            quantity = args[2];
            API_URL = args[3];
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idCustomer", idCustomer));
            params.add(new BasicNameValuePair("fcmtoken", fcmtoken));
            params.add(new BasicNameValuePair("quantity", quantity));
            execute();
            return null;
        }

        public void execute() {
            JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
            try {
                Log.d("wallet", jsonObject.toString());
                String success = jsonObject.getString(TAG_SUCCESS);
                String message = jsonObject.getString(TAG_MESSAGE);
                if (success .equals("error"))
                    execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

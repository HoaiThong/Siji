package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
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
import net.siji.readView.ViewerActivity;

public class RewardDialog extends DialogFragment implements View.OnClickListener {
    private Activity mActivity;
    private View view;
    private TextView msg_tv;
    private FragmentManager fragmentManager;
    private RewardedAd rewardedAd;

    public RewardDialog(Activity context) {
        this.mActivity = context;
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    public RewardDialog(Activity mActivity, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.mActivity = mActivity;
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
                    Log.d("TAG_reward", String.valueOf(rewardCoin));

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
}

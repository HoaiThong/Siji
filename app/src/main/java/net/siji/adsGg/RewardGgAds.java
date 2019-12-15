package net.siji.adsGg;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardGgAds {
    private Activity mActivity;
    private RewardedAd rewardedAd;
    private String idUnit="ca-app-pub-3940256099942544/5224354917";
    public RewardGgAds(Activity mActivity) {
        this.mActivity=mActivity;
    }

    public void loadAdsReward() {
        rewardedAd = new RewardedAd(this.mActivity, idUnit);
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

    public void showAdsReward(){
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                public void onRewardedAdClosed() {
                    // Ad closed.
                    loadAdsReward();
                }

                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    int rewardCoin=reward.getAmount();
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

    public void setIdUnit(String idUnit) {
        this.idUnit = idUnit;
    }
}

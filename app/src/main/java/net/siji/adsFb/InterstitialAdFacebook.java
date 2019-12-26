package net.siji.adsFb;

import android.app.Activity;
import android.util.Log;

import com.facebook.ads.*;

import net.siji.R;

public class InterstitialAdFacebook {
    private Activity mActivity;
    private InterstitialAd interstitialAd;
    private final String TAG = InterstitialAdFacebook.class.getSimpleName();

    public InterstitialAdFacebook(Activity mActivity) {
        AudienceNetworkAds.initialize(mActivity);
        this.mActivity = mActivity;
        interstitialAd = new InterstitialAd(mActivity, mActivity.getResources().getString(R.string.Ad_Facebook_Inters));
    }

    public void show() {
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }

    public void destroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
    }
}

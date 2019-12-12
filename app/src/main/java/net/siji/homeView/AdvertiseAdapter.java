package net.siji.homeView;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import net.siji.R;
import net.siji.inforView.InforActivity;
import net.siji.model.Advertise;
import net.siji.model.Header;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private ImageView imageView;
    private Context mContext;
    private ArrayList<Advertise> arrayList;
    private PendingIntent pendingIntent;

    public AdvertiseAdapter(Activity activity, ArrayList<Advertise> advertiseArrayList) {
        this.arrayList = advertiseArrayList;
        this.activity = activity;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View imageLayout = inflater.inflate(R.layout.item_advertise, view, false);

        imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final Advertise a = arrayList.get(position);
        try {
            Glide.with(activity)
                    .load(a.getImgUrl())
                    .into(imageView);
        } catch (Exception e) {
        }
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = a.getTargetUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
            }
        });
        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

//    public void zoom(View view) {
//        Animation animation = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.zoom);
//        imageView.startAnimation(animation);
//    }
}
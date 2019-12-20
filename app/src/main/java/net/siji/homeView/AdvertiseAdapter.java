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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
    private ImageView imageView, imgIcon;
    private TextView tv_title, tv_description;
    private RelativeLayout relativeLayout;
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
        relativeLayout = imageLayout.findViewById(R.id.rl_advertise);
        final Advertise a = arrayList.get(position);
        String mimeTypeText = activity.getResources().getString(R.string.mimeType_text);
        String mimeTypeImg = activity.getResources().getString(R.string.mimeType_image);
        if (a.getMimeType().toLowerCase().trim().equals(mimeTypeImg)) {
            try {
                Glide.with(activity)
                        .load(a.getImgUrl())
                        .into(imageView);
            } catch (Exception e) {
            }
            imageView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        if (a.getMimeType().toLowerCase().trim().equals(mimeTypeText)) {
            imgIcon = imageLayout.findViewById(R.id.img_icon);
            tv_title = imageLayout.findViewById(R.id.tv_title);
            tv_description = imageLayout.findViewById(R.id.tv_description);
            tv_title.setText(a.getTitle());
            tv_description.setText(a.getDescription());
            try {
                Glide.with(activity)
                        .load(a.getImgUrl())
                        .into(imgIcon);
            } catch (Exception e) {
            }
            imageView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
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
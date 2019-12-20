package net.siji.homeView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;


import net.siji.R;
import net.siji.inforView.InforActivity;
import net.siji.model.Header;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private ImageView imageView;
    private TextView tv_summary;
    private Context mContext;
    private ArrayList<Header> arrayList;

    public ImageAdapter(Activity activity, ArrayList<Header> listUrl) {
        this.arrayList=listUrl;
        this.activity=activity;
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
        View imageLayout = inflater.inflate(R.layout.image_slide, view, false);

        imageView = (ImageView) imageLayout.findViewById(R.id.image);
        tv_summary=(TextView) imageLayout.findViewById(R.id.tv_summary);
        DisplayMetrics dis = new DisplayMetrics();
        final Header header=arrayList.get(position);
        tv_summary.setText(header.getComic().getSummary());
        try {
            Glide.with(activity)
                    .load(header.getComic().getIconUrl())
                    .into(imageView);
        } catch (Exception e) {
        }
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, InforActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("comic", header.getComic());
                intent.putExtras(data);
                activity.startActivity(intent);
                activity.finish();
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
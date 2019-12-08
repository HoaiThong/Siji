package net.siji.readView;

/**
 * Created by Sun on 7/28/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import net.siji.R;
import net.siji.model.Chapter;
import net.siji.touchimageview.TouchImageView;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends PagerAdapter {


    List<Chapter> arrayList;
    private LayoutInflater inflater;
    private Activity activity;
    private TouchImageView imageView;

    public ImageAdapter(Activity activity, List<Chapter> chaptersList) {
        this.activity = activity;
        arrayList = chaptersList;
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
    public Object instantiateItem(ViewGroup view, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View imageLayout = inflater.inflate(R.layout.image_viewpage_slide, view, false);

        imageView = (TouchImageView) imageLayout.findViewById(R.id.image);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
//        int height = dis.heightPixels;
//        int width = dis.widthPixels;
        int height = imageView.getMeasuredHeight();
        int width = imageView.getMeasuredWidth();
        imageView.setMinimumHeight(height);
        imageView.setMinimumWidth(width);
        Chapter c=arrayList.get(position);
//        imageLoader = new ImageLoader(imageLayout.getContext());
//        imageLoader.displayImage(arrayList.get(position).getUrl(), R.drawable.ic_launcher, imageView);
//        imageView.clearAnimation();
        try {
            Picasso.with(activity.getApplicationContext())
                    .load(c.getUrl())
					.networkPolicy(NetworkPolicy.NO_CACHE)
					.memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        } catch (Exception e) {
        }
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
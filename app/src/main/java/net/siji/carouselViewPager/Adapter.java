package net.siji.carouselViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import net.siji.R;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Object> lessionList;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Object> lessionList, Context context) {
        this.lessionList = lessionList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_carousel, container, false);
        ImageView imageView;
//        TextView titleView;
        imageView=view.findViewById(R.id.pagerImg);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bleach));
//        titleView=view.findViewById(R.id.description_tv);
//        Lession lession=lessionList.get(position);
//        imageView.setImageResource(lession.getImage());
//        titleView.setText(lession.getTitle());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
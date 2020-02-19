package com.tech.cloudnausor.ohmytennis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;


public class HomeBannerAdapter extends PagerAdapter {
    String url= ApiClient.BASE_URL;
    private Context context;
    private LayoutInflater layoutInflater;
    private String [] images = {url+"/views/images/banner.png",url+"/views/images/banner.png",url+"/views/images/banner.png"};
    public HomeBannerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.home_banner_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get().load(images[position]).fit().into(imageView);
//        imageView.setBackgroundResource(images[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
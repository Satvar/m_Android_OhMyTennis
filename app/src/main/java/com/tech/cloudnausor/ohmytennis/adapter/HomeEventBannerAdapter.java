package com.tech.cloudnausor.ohmytennis.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;

import java.util.ArrayList;

public class HomeEventBannerAdapter extends RecyclerView.Adapter<HomeEventBannerAdapter.HomeEventBannerHolder> {

    private Context context;
    private ArrayList<String> drawableArrayList;
    private ArrayList<String>stringArrayList;

    public HomeEventBannerAdapter(Context context, ArrayList<String> drawableArrayList, ArrayList<String> stringArrayList) {
        this.context = context;
        this.drawableArrayList = drawableArrayList;
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public HomeEventBannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_event_banner,parent,false);
        return new HomeEventBannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeEventBannerHolder holder, int position) {
          if(stringArrayList.size()!=0){
              if(drawableArrayList.get(position)!=null){
                  Picasso.get().load(drawableArrayList.get(position)).fit().into(holder.eventBannerImage);
//              holder.eventBannerImage.setBackground(drawableArrayList.get(position));
              }else{
//                  holder.eventBannerImage.setBackground(context.getResources().getDrawable(R.drawable.register_profile_defalut));
              }
              if(stringArrayList.get(position)!=null){
                  holder.eventBannerAbout.setText(stringArrayList.get(position));}else{
                  holder.eventBannerAbout.setText("");
              }

          }
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class HomeEventBannerHolder extends RecyclerView.ViewHolder {
        ImageView eventBannerImage;
        TextView eventBannerAbout;
        public HomeEventBannerHolder(@NonNull View itemView) {
            super(itemView);
            eventBannerImage =(ImageView)itemView.findViewById(R.id.eventbannerimage);
            eventBannerAbout =(TextView) itemView.findViewById(R.id.eventbannabout);
        }
    }
}

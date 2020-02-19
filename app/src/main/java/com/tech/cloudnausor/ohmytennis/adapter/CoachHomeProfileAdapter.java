package com.tech.cloudnausor.ohmytennis.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;

import java.util.ArrayList;
import java.util.List;

public class CoachHomeProfileAdapter extends RecyclerView.Adapter<CoachHomeProfileAdapter.CoachHomeProfileHolder> {
Context context;
ArrayList<Drawable> profile;
ArrayList<String> name;
int a;

    public CoachHomeProfileAdapter(Context context, ArrayList<Drawable> profile, ArrayList<String> name, int a) {
        this.context = context;
        this.profile = profile;
        this.name = name;
        this.a = a;
    }

    @NonNull
    @Override
    public CoachHomeProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_profil_show,parent,false);
        return new CoachHomeProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachHomeProfileHolder holder, int position) {

        holder.profileshow.setImageDrawable(profile.get(position));
        holder.profilenameshow.setText(name.get(position));
        if(a == 5){
            holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
        }else if(a == 4){
            holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
        }else if(a == 3){
            holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
        }else if(a == 2){
            holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
        }else if(a == 1){
            holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
            holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
        }else {
            holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
            holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
        }

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class CoachHomeProfileHolder extends RecyclerView.ViewHolder {
        ImageView star1,star2,star3,star4,star5,profileshow;
        TextView profilenameshow;

        public CoachHomeProfileHolder(@NonNull View itemView) {
            super(itemView);
            star1 = (ImageView)itemView.findViewById(R.id.profilestar1);
            star2 = (ImageView)itemView.findViewById(R.id.profilestar2);
            star3 = (ImageView)itemView.findViewById(R.id.profilestar3);
            star4 = (ImageView)itemView.findViewById(R.id.profilestar4);
            star5 = (ImageView)itemView.findViewById(R.id.profilestar5);
            profileshow  = (ImageView)itemView.findViewById(R.id.profilehome);
            profilenameshow  = (TextView)itemView.findViewById(R.id.profilename);


        }
    }
}

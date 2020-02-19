package com.tech.cloudnausor.ohmytennis.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.model.ListPartnerModel;

import java.util.ArrayList;

public class ListPartnerAdapter extends RecyclerView.Adapter<ListPartnerAdapter.ListPartnerHolder> {
    Context context;
    ArrayList<ListPartnerModel> listPartnerModelArrayList;
    public ListPartnerAdapter(Context context, ArrayList<ListPartnerModel> listPartnerModelArrayList) {
        this.context = context;
        this.listPartnerModelArrayList = listPartnerModelArrayList;
    }
    @NonNull
    @Override
    public ListPartnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_partner,parent,false);
        return new ListPartnerHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ListPartnerHolder holder, int position) {
        ListPartnerModel listPartnerModel = listPartnerModelArrayList.get(position);
        if(listPartnerModel.getName() != null){
            holder.Partner_Name.setText(listPartnerModel.getName());
        }else {
            holder.Partner_Name.setText("");
        }
        if(listPartnerModel.getDescribtion() != null){
            holder.Partner_Command.setText(listPartnerModel.getDescribtion());
        }else {
            holder.Partner_Command.setText(" - ");
        }

        if (listPartnerModel.getRating() != null) {
            switch (listPartnerModel.getRating()) {
                case "4":
                    holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    break;
                case "3":
                    holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    break;
                case "2":
                    holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    break;
                case "1":
                    holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    break;
                default:
                    holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty_24dp));
                    break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return listPartnerModelArrayList.size();
    }
    public class ListPartnerHolder extends RecyclerView.ViewHolder {
        ImageView star1,star2,star3,star4,star5;
        TextView Partner_Name,Partner_Qualification,Partner_Rating,Partner_Command;
        public ListPartnerHolder(@NonNull View itemView) {
            super(itemView);
            star1 = (ImageView)itemView.findViewById(R.id.profilestar1);
            star2 = (ImageView)itemView.findViewById(R.id.profilestar2);
            star3 = (ImageView)itemView.findViewById(R.id.profilestar3);
            star4 = (ImageView)itemView.findViewById(R.id.profilestar4);
            star5 = (ImageView)itemView.findViewById(R.id.profilestar5);
            Partner_Name =(TextView)itemView.findViewById(R.id.partner_name);
            Partner_Command =(TextView)itemView.findViewById(R.id.partner_describtion);

        }
    }
}

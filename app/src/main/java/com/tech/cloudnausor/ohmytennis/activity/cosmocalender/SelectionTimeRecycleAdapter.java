package com.tech.cloudnausor.ohmytennis.activity.cosmocalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;

import java.util.ArrayList;

public class SelectionTimeRecycleAdapter extends RecyclerView.Adapter<SelectionTimeRecycleAdapter.SelectionTimeRecycleHolder> {
    Context context;
    ArrayList<String> arraySelectionTime;
    ItemClickListenerSelection mClickListener;


    public SelectionTimeRecycleAdapter(Context context, ArrayList<String> arraySelectionTime) {
        this.context = context;
        this.arraySelectionTime = arraySelectionTime;
    }

    @NonNull
    @Override
    public SelectionTimeRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerviewcalender_time,parent,false);
        return new SelectionTimeRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionTimeRecycleHolder holder, int position) {
        holder.timeSpilt.setText(arraySelectionTime.get(position));
        holder.timeSpilt.setTextColor(context.getResources().getColor(R.color.colorGoogleGreen));
        holder.timeSpilt.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white_tint)));

    }

    @Override
    public int getItemCount() {
        return arraySelectionTime.size();
    }

    public class SelectionTimeRecycleHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        Button timeSpilt;
        public SelectionTimeRecycleHolder(@NonNull View itemView) {
            super(itemView);
            timeSpilt = (Button)itemView.findViewById(R.id.timeSpilt);
            timeSpilt.setClickable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.itemClickListerner(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListenerSelection itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListenerSelection{
        void itemClickListerner(View view, int postion);
    }
}
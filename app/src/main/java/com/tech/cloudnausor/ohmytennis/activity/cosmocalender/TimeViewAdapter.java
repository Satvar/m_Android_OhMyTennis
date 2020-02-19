package com.tech.cloudnausor.ohmytennis.activity.cosmocalender;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;

import java.util.ArrayList;

public class TimeViewAdapter extends RecyclerView.Adapter<TimeViewAdapter.TimeViewAdapterHolder> {
    private ArrayList<String> timeShow;
    private ArrayList<String> timeStatus;
    private ItemClickListener mClickListener;
    private Context context;

    public TimeViewAdapter(Context context,ArrayList<String> timeShow,ArrayList<String> timeStatus) {
        this.context = context;
        this.timeShow = timeShow;
        this.timeStatus = timeStatus;
    }

    @Override
    public TimeViewAdapterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recylerviewcalender_time,viewGroup,false);
        return new TimeViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeViewAdapterHolder timeViewAdapterHolder, int i) {
        System.out.println("timeStatus --> "+timeStatus);
        if(timeStatus.get(i).equals("available")){
         timeViewAdapterHolder.timeSpilt.setText(timeShow.get(i));
         timeViewAdapterHolder.timeSpilt.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
        }else if(timeStatus.get(i).equals("wait")){
            timeViewAdapterHolder.timeSpilt.setText(timeShow.get(i));
            timeViewAdapterHolder.timeSpilt.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));

        }else if(timeStatus.get(i).equals("booked")){
            timeViewAdapterHolder.timeSpilt.setText(timeShow.get(i));
            timeViewAdapterHolder.timeSpilt.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleRed)));

        }else if(timeStatus.get(i).equals("unavailable")){
            timeViewAdapterHolder.timeSpilt.setText(timeShow.get(i));
            timeViewAdapterHolder.timeSpilt.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));

        }
    }

    @Override
    public int getItemCount() {
        return timeShow.size();
    }

    public class TimeViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button timeSpilt;
        public TimeViewAdapterHolder(View itemView) {
            super(itemView);
            timeSpilt = (Button)itemView.findViewById(R.id.timeSpilt);
            timeSpilt.setClickable(false);
             itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(timeStatus.get(getAdapterPosition()).equals("available"))
                if (mClickListener != null) mClickListener.itemClickListerner(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void itemClickListerner(View view, int postion);
    }
}

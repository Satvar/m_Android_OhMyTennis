package com.tech.cloudnausor.ohmytennis.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.model.SparringPartnerModel;

import java.util.ArrayList;

public class SparringPartnerAdapter extends RecyclerView.Adapter<SparringPartnerAdapter.SparringPartnerHolder> {
    Context context;
    ArrayList<SparringPartnerModel> sparringPartnerModelArrayList;

    public SparringPartnerAdapter(Context context, ArrayList<SparringPartnerModel> sparringPartnerModelArrayList) {
        this.context = context;
        this.sparringPartnerModelArrayList = sparringPartnerModelArrayList;
    }

    @NonNull
    @Override
    public SparringPartnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sparring_partner,parent,false);
        return new SparringPartnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SparringPartnerHolder holder, int position) {
        SparringPartnerModel sparringPartnerModel = sparringPartnerModelArrayList.get(position);
        if(sparringPartnerModel.getName() != null){
            holder.SparringName.setText(sparringPartnerModel.getName());
        }else {
            holder.SparringName.setText("");
        }
        if(sparringPartnerModel.getQualification() != null){
            holder.SparringQualification.setText(sparringPartnerModel.getQualification());
        }else {
            holder.SparringQualification.setText("");
        }
        if(sparringPartnerModel.getRating() != null){
            holder.Sparring_progress.setProgress(Integer.parseInt(sparringPartnerModel.getRating()));
        }else {
            holder.Sparring_progress.setProgress(0);
        }
        if(sparringPartnerModel.getDescribtion() != null){
            holder.SparringCommand.setText(sparringPartnerModel.getDescribtion());
        }else {
            holder.SparringCommand.setText(" - ");
        }

    }

    @Override
    public int getItemCount() {
        return sparringPartnerModelArrayList.size();
    }

    public class SparringPartnerHolder extends RecyclerView.ViewHolder {
        TextView SparringName,SparringQualification,SparringCommand;
        ProgressBar Sparring_progress;
        public SparringPartnerHolder(@NonNull View itemView) {
            super(itemView);
            SparringName =(TextView)itemView.findViewById(R.id.sparring_name);
            SparringQualification =(TextView)itemView.findViewById(R.id.sparring_qualification);
            Sparring_progress =(ProgressBar) itemView.findViewById(R.id.sparring_progress);
            SparringCommand =(TextView)itemView.findViewById(R.id.sparring_describtion);
        }
    }
}

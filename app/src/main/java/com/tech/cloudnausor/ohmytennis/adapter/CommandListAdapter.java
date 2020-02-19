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
import com.tech.cloudnausor.ohmytennis.model.CommandListModel;

import java.util.ArrayList;

public class CommandListAdapter extends RecyclerView.Adapter<CommandListAdapter.CommandListHolder> {
    Context context;
    ArrayList<CommandListModel> commandListModelArrayList;

    public CommandListAdapter(Context context, ArrayList<CommandListModel> commandListModelArrayList) {
        this.context = context;
        this.commandListModelArrayList = commandListModelArrayList;
    }

    @NonNull
    @Override
    public CommandListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_command_list,parent,false);
        return new CommandListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandListHolder holder, int position) {
        CommandListModel commandListModel = commandListModelArrayList.get(position);
        if(commandListModel.getName() != null){
            holder.Command_Name.setText(commandListModel.getName());
        }else {
            holder.Command_Name.setText("");
        }
        if(commandListModel.getQualification() != null){
            holder.Command_Qualification.setText(commandListModel.getQualification());
        }else {
            holder.Command_Qualification.setText("");
        }
        if(commandListModel.getRating() != null){
            holder.Command_Rating.setText(commandListModel.getRating());
        }else {
            holder.Command_Rating.setText("0");
        }
        if(commandListModel.getCommand() != null){
            holder.Command_Command.setText(commandListModel.getCommand());
        }else {
            holder.Command_Command.setText(" - ");
        }

        if (commandListModel.getRating() != null) {
            switch (commandListModel.getRating()) {
                case "5":
                    holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_24dp));
                    break;
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
        return commandListModelArrayList.size();
    }

    public class CommandListHolder extends RecyclerView.ViewHolder {
        ImageView star1,star2,star3,star4,star5;
        TextView Command_Name,Command_Qualification,Command_Rating,Command_Command;
        public CommandListHolder(@NonNull View itemView) {
            super(itemView);
            star1 = (ImageView)itemView.findViewById(R.id.profilestar1);
            star2 = (ImageView)itemView.findViewById(R.id.profilestar2);
            star3 = (ImageView)itemView.findViewById(R.id.profilestar3);
            star4 = (ImageView)itemView.findViewById(R.id.profilestar4);
            star5 = (ImageView)itemView.findViewById(R.id.profilestar5);
            Command_Name =(TextView)itemView.findViewById(R.id.command_name);
            Command_Qualification =(TextView)itemView.findViewById(R.id.command_qualification);
            Command_Rating =(TextView)itemView.findViewById(R.id.command_rating);
            Command_Command =(TextView)itemView.findViewById(R.id.command_command);

        }
    }
}

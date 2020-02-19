package com.tech.cloudnausor.ohmytennis.activity.cosmocalender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.model.SelectionTimeDetails;

import java.util.ArrayList;

public class SelectionTimeListAdapter extends RecyclerView.Adapter<SelectionTimeListAdapter.SelectionTimeListHolder> {
    Context context;
    ArrayList<SelectionTimeDetails> selectionTimeDetailsArrayList;
    SelectionTimeRecycleAdapter selectionTimeRecycleAdapter;
    ItemClickListenerRemoveSelection itemClickListenerRemoveSelection;
    DefaultCalendarActivity defaultCalendarActivity ;

    public SelectionTimeListAdapter(Context context, ArrayList<SelectionTimeDetails> selectionTimeDetailsArrayList,DefaultCalendarActivity defaultCalendarActivity) {
        this.context = context;
        this.selectionTimeDetailsArrayList = selectionTimeDetailsArrayList;
        this.defaultCalendarActivity = defaultCalendarActivity;
    }

    @NonNull
    @Override
    public SelectionTimeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectiontimelist,parent,false);
        return new SelectionTimeListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectionTimeListHolder holder, final int position) {
        SelectionTimeDetails selectionTimeDetails = selectionTimeDetailsArrayList.get(position);
        holder.timeTitle.setText(selectionTimeDetails.getDateTitte());
        selectionTimeRecycleAdapter = new SelectionTimeRecycleAdapter(context,selectionTimeDetails.getArraySelectionTime());
        AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(context,140);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.timeSelectionList.setLayoutManager(autoFitGridLayoutManager);
        holder.timeSelectionList.setAdapter(selectionTimeRecycleAdapter);
        selectionTimeRecycleAdapter.notifyDataSetChanged();

        selectionTimeRecycleAdapter.setClickListener(new SelectionTimeRecycleAdapter.ItemClickListenerSelection() {
            @Override
            public void itemClickListerner(View view, int postionTime) {
                if (itemClickListenerRemoveSelection != null) itemClickListenerRemoveSelection.itemClickListerner(view, position,postionTime);
                defaultCalendarActivity.selectionTimeDetails.get(position).arraySelectionTime.remove(postionTime);
               if (defaultCalendarActivity.Service_Type.equals("CoursIndividuel")){
                defaultCalendarActivity.indi_course_cout = defaultCalendarActivity.indi_course_cout -1;
                }else if(defaultCalendarActivity.Service_Type.equals("CoursCollectifOndemand")) {
                   defaultCalendarActivity.ondemand_course_cout = defaultCalendarActivity.ondemand_course_cout -1;

                }
                if(defaultCalendarActivity.selectionTimeDetails.get(position).arraySelectionTime.isEmpty()){
                    defaultCalendarActivity.selectionTimeDetails.remove(position);
                    defaultCalendarActivity.selectionTimeListAdapter.notifyDataSetChanged();
                    defaultCalendarActivity.recylerTimeSelection.scrollToPosition(defaultCalendarActivity.selectionTimeDetails.size()-1);
                }else {
                    defaultCalendarActivity.selectionTimeListAdapter.notifyDataSetChanged();
                    defaultCalendarActivity.recylerTimeSelection.scrollToPosition(position);
                }
//                        Toast.makeText(context,"Checking"+ position + postionTime,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != selectionTimeDetailsArrayList ? selectionTimeDetailsArrayList.size() : 0);
    }

    public class SelectionTimeListHolder extends RecyclerView.ViewHolder   {
        TextView timeTitle;
        RecyclerView timeSelectionList;
        public SelectionTimeListHolder(@NonNull View itemView) {
            super(itemView);
            timeTitle =(TextView)itemView.findViewById(R.id.timeTitle);
            timeSelectionList=(RecyclerView)itemView.findViewById((R.id.timeSelection));

        }
    }

    public void setClickListener(ItemClickListenerRemoveSelection itemClickListenerRemoveSelection) {
        this.itemClickListenerRemoveSelection = itemClickListenerRemoveSelection;
    }

    public interface ItemClickListenerRemoveSelection{
        void itemClickListerner(View view, int postionDate, int postionTime);
    }
}
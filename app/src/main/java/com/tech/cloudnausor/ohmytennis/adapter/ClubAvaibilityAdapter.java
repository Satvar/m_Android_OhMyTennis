package com.tech.cloudnausor.ohmytennis.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.model.ClubAvabilityModelData;

import java.util.ArrayList;

public class ClubAvaibilityAdapter extends RecyclerView.Adapter<ClubAvaibilityAdapter.ClubAvaibilityHolder> {
    Context context;
    ArrayList<ArrayList<ClubAvabilityModelData>> ClubAvabilityModelDataArrayList;
    ClubAvaibilityAdapterData ClubAvaibilityAdapterData;
    ItemClickListenerRemoveSelection itemClickListenerRemoveSelection;
    FragmentActivity fragmentActivity ;
    String enable ="";
    String coach_name= "";
    String place = "";

//    DefaultCalendarActivity defaultCalendarActivity ;

    public ClubAvaibilityAdapter(Context context, ArrayList<ArrayList<ClubAvabilityModelData>> ClubAvabilityModelDataArrayList,FragmentActivity fragmentActivity,    String enable ,String coach_name,String place) {
        this.context = context;
        this.ClubAvabilityModelDataArrayList = ClubAvabilityModelDataArrayList;
        this.fragmentActivity = fragmentActivity;
        this.enable = enable;
        this.coach_name= coach_name;
        this.place = place;
//        this.defaultCalendarActivity = defaultCalendarActivity;
    }

    @NonNull
    @Override
    public ClubAvaibilityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_course_and_details,parent,false);
        return new ClubAvaibilityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClubAvaibilityHolder holder, final int position) {
        ArrayList<ClubAvabilityModelData> ClubAvabilityModelData = ClubAvabilityModelDataArrayList.get(position);
        ClubAvabilityModelData clubAvabilityModelDatass = ClubAvabilityModelData.get(0);
//        holder.timeTitle.setText(ClubAvabilityModelData.getDateTitte());
        ClubAvaibilityAdapterData = new ClubAvaibilityAdapterData(context,ClubAvabilityModelData,fragmentActivity,enable,coach_name,place);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder. club_course_details_show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder. club_course_details_show.getVisibility() == View.VISIBLE) {
                    holder.club_course_details_show.setVisibility(View.GONE);
                    holder.club_course_details_show_hide.setText("More");
                    holder.club_course_details_show_hide.setBackgroundTintList(ColorStateList.valueOf(
                            context.getResources().getColor(R.color.greencolor)));
                }else if(holder. club_course_details_show.getVisibility() == View.GONE){
                    holder. club_course_details_show.setVisibility(View.VISIBLE);
                holder.club_course_details_show_hide.setText("Hide");
                holder.club_course_details_show_hide.setBackgroundTintList(ColorStateList.valueOf(
                        context.getResources().getColor(R.color.button_cancel)));}
            }
        });

        holder. club_dialog_course_name.setText(clubAvabilityModelDatass.getCourse());
        holder.club_dialog_course_day.setText(clubAvabilityModelDatass.getWeekday());

        holder.club_course_details_recycl.setLayoutManager(linearLayoutManager);
        holder.club_course_details_recycl.setAdapter(ClubAvaibilityAdapterData);
        ClubAvaibilityAdapterData.notifyDataSetChanged();

        ClubAvaibilityAdapterData.setClickListener(new ClubAvaibilityAdapterData.ItemClickListenerSelection() {
            @Override
            public void itemClickListerner(View view, int postionTime) {
                if (itemClickListenerRemoveSelection != null) itemClickListenerRemoveSelection.itemClickListerner(view, position,postionTime);
//                defaultCalendarActivity.ClubAvabilityModelData.get(position).arraySelectionTime.remove(postionTime);
//                if(defaultCalendarActivity.ClubAvabilityModelData.get(position).arraySelectionTime.isEmpty()){
//                    defaultCalendarActivity.ClubAvabilityModelData.remove(position);
//                    defaultCalendarActivity.ClubAvaibilityAdapter.notifyDataSetChanged();
//                    defaultCalendarActivity.recylerTimeSelection.scrollToPosition(defaultCalendarActivity.ClubAvabilityModelData.size()-1);
//                }else {
//                    defaultCalendarActivity.ClubAvaibilityAdapter.notifyDataSetChanged();
//                    defaultCalendarActivity.recylerTimeSelection.scrollToPosition(position);
//                }
//                        Toast.makeText(context,"Checking"+ position + postionTime,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != ClubAvabilityModelDataArrayList ? ClubAvabilityModelDataArrayList.size() : 0);
    }

    public class ClubAvaibilityHolder extends RecyclerView.ViewHolder   {
        TextView timeTitle;
        RecyclerView club_course_details_recycl;
        Button club_course_details_show_hide;
        TextView  club_dialog_course_day,club_dialog_course_name  ;
        LinearLayout club_course_details_show;

        public ClubAvaibilityHolder(@NonNull View itemView) {
            super(itemView);
            club_dialog_course_name = (TextView)itemView.findViewById(R.id.club_dialog_course_name);
            club_dialog_course_day = (TextView)itemView.findViewById(R.id.club_dialog_course_day);
            club_course_details_show_hide = (Button) itemView.findViewById(R.id.club_course_details_show_hide);
            club_course_details_show = (LinearLayout) itemView.findViewById(R.id.club_course_details_show);
            club_course_details_recycl=(RecyclerView)itemView.findViewById((R.id.club_course_details_recycl));

        }
    }

    public void setClickListener(ItemClickListenerRemoveSelection itemClickListenerRemoveSelection) {
        this.itemClickListenerRemoveSelection = itemClickListenerRemoveSelection;
    }

    public interface ItemClickListenerRemoveSelection{
        void itemClickListerner(View view, int postionDate, int postionTime);
    }
}

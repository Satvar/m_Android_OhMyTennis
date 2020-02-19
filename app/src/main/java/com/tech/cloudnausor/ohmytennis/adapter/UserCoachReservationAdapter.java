package com.tech.cloudnausor.ohmytennis.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.model.AllCoachDetails;
import com.tech.cloudnausor.ohmytennis.model.UserCoachReserveModel;

import java.util.ArrayList;

public class UserCoachReservationAdapter extends RecyclerView.Adapter<UserCoachReservationAdapter.UserCoachReservationAdapterHolder> {
    Context context ;
    Activity f;
    UserCoachReserveModel userCoachReserveModel;
    ArrayList<UserCoachReserveModel> userCoachReserveModelArrayList1 = new ArrayList<UserCoachReserveModel>();
    ArrayList<UserCoachReserveModel> userCoachReserveModelArrayListFilter = new ArrayList<UserCoachReserveModel>();
    ArrayList<String> reservationHeading = new ArrayList<String>();
    ArrayList<String> reserveName = new ArrayList<String>();
    ArrayList<String> reserveredDate = new ArrayList<String>();
    ArrayList<String> reserveredTime = new ArrayList<String>();
    ArrayList<String> reserveStatus = new ArrayList<String>();

//    public UserCoachReservationAdapter(Context context, ArrayList<String> reservationHeading, ArrayList<String> reserveName, ArrayList<String> reserveredDate, ArrayList<String> reserveredTime, ArrayList<String> reserveStatus) {
//        this.context = context;
//        this.reservationHeading = reservationHeading;
//        this.reserveName = reserveName;
//        this.reserveredDate = reserveredDate;
//        this.reserveredTime = reserveredTime;
//        this.reserveStatus = reserveStatus;
//    }


    public UserCoachReservationAdapter(Activity f, Context context, ArrayList<UserCoachReserveModel> userCoachReserveModelArrayList) {
        this.f = f;
        this.userCoachReserveModelArrayList1 = userCoachReserveModelArrayList;
        this.context = context;
        this.userCoachReserveModelArrayListFilter.addAll(userCoachReserveModelArrayList1);
    }

    @NonNull
    @Override
    public UserCoachReservationAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_coach_user_reservation_list,parent,false);
        return new UserCoachReservationAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCoachReservationAdapterHolder holder, int position) {

        if(userCoachReserveModelArrayListFilter != null){
            if(userCoachReserveModelArrayListFilter.get(position) != null){
                userCoachReserveModel = userCoachReserveModelArrayListFilter.get(position);
                if(userCoachReserveModel.getReservationHeading() != null){
                    holder.Reserve_Heading.setText(userCoachReserveModel.getReservationHeading());
                }else {
                    holder.Reserve_Heading.setText(" - ");
                }
                if(userCoachReserveModel.getReserveName() != null){
                    holder.Reserve_Name.setText(userCoachReserveModel.getReserveName());
                }else {
                    holder.Reserve_Name.setText(" - ");
                }
                if(userCoachReserveModel.getReserveName() != null){
                    holder.Reserve_Date.setText(userCoachReserveModel.getReserveName());
                }else {
                    holder.Reserve_Date.setText(" - ");
                }
                if(userCoachReserveModel.getReserveredTime() != null){
                    holder.Reserve_Time.setText(userCoachReserveModel.getReserveredTime());
                }else {
                    holder.Reserve_Time.setText(" - ");
                }
                if(userCoachReserveModel.getReserveStatus() != null){

                    switch (userCoachReserveModel.getReserveStatus()){
                        case "0":
                            holder.Reserve_Status.setText("Pas encore confirmé");
                            holder.Reserve_Status.setVisibility(View.VISIBLE);
                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                             break;
                        case "1":
                            holder.Reserve_Status.setText("Terminé");
                            holder.Reserve_Status.setVisibility(View.VISIBLE);
                    holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.greencolor)));
                            break;
                        case "2":
                            holder.Reserve_Status.setText("Annulé");
                            holder.Reserve_Status.setVisibility(View.VISIBLE);
                    holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.error)));
                            break;

                            default:
                                holder.Reserve_Status.setText("");
                                holder.Reserve_Status.setVisibility(View.GONE);
                                break;
                    }

                }else {
                    holder.Reserve_Status.setText("");
                    holder.Reserve_Status.setVisibility(View.GONE);
                }

            }
        }

//            UserCoachReserveModel userCoachReserveModel = userCoachReserveModelArrayList.get(position);
//        if(reservationHeading.get(position) != null){
//            holder.Reserve_Heading.setText(reservationHeading.get(position));
//        }else {
//            holder.Reserve_Heading.setText(" - ");
//        }
//        if(reserveName.get(position) != null){
//            holder.Reserve_Name.setText(reserveName.get(position));
//        }else {
//            holder.Reserve_Name.setText(" - ");
//        }
//        if(reserveredDate.get(position) != null){
//            holder.Reserve_Date.setText(reserveredDate.get(position));
//        }else {
//            holder.Reserve_Date.setText(" - ");
//        }
//        if(reserveredTime.get(position) != null){
//            holder.Reserve_Time.setText(reserveredTime.get(position));
//        }else {
//            holder.Reserve_Time.setText(" - ");
//        }
//        if(reserveStatus.get(position) != null){
//
//            switch (reserveStatus.get(position)){
//                case "0":
//                    holder.Reserve_Status.setText("Pas encore confirmé");
//                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                    holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
//                    break;
//                case "1":
//                    holder.Reserve_Status.setText("Terminé");
//                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                    holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.greencolor)));
//                    break;
//                case "2":
//                    holder.Reserve_Status.setText("Annulé");
//                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                    holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.error)));
//                    break;
//
//                default:
//                    holder.Reserve_Status.setText("");
//                    holder.Reserve_Status.setVisibility(View.GONE);
//                    break;
//            }
//
//        }else {
//            holder.Reserve_Status.setText("");
//            holder.Reserve_Status.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return (null != userCoachReserveModelArrayListFilter? userCoachReserveModelArrayListFilter.size() : 0);
    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {

            @Override
            public void run() {

                // Clear the filter list
                userCoachReserveModelArrayListFilter.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    userCoachReserveModelArrayListFilter.addAll(userCoachReserveModelArrayList1);

                } else {
                    System.out.println("testing bala filter 666 " + userCoachReserveModelArrayList1);
                    // Iterate in the original List and add it to filter list...
                    for (UserCoachReserveModel item : userCoachReserveModelArrayList1) {
                        System.out.println("testing bala filter 1"+item.getReserveStatus());

                        if (item.getReserveStatus().contains(text)) {
                            System.out.println("testing bala filter 1");

                            // Adding Matched items
                            userCoachReserveModelArrayListFilter.add(item);
                        }
                    }
                }

                // Set on UI Thread
                (f).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("testing bala filter 1");

                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }


    public class UserCoachReservationAdapterHolder extends RecyclerView.ViewHolder {
        TextView  Reserve_Heading,Reserve_Name,Reserve_Date,Reserve_Time;
        Button Reserve_Status;
        public UserCoachReservationAdapterHolder(@NonNull View itemView) {
            super(itemView);
            Reserve_Heading = (TextView)itemView.findViewById(R.id.reserve_heading);
            Reserve_Name = (TextView)itemView.findViewById(R.id.reserve_name);
            Reserve_Date = (TextView)itemView.findViewById(R.id.reserve_date);
            Reserve_Time  = (TextView)itemView.findViewById(R.id.reserve_time);
            Reserve_Status = (Button)itemView.findViewById(R.id.reserve_status);
        }
    }
}

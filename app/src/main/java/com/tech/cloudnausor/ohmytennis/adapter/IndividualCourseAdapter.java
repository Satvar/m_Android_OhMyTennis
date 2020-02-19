package com.tech.cloudnausor.ohmytennis.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.activity.services.collectivecourseclub.BookingClubDetails;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.services.collectivcourse.CollectiveCourseActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.individual.CoachDataActivity;
import com.tech.cloudnausor.ohmytennis.model.LoacationBaseCoachDataDetails;

import java.util.ArrayList;



public class IndividualCourseAdapter extends RecyclerView.Adapter<IndividualCourseAdapter.IndividualCourseHolder>  {
    Context context;
    Activity f;
    String selectedCourse = "";
    ArrayList<LoacationBaseCoachDataDetails> listdata = new ArrayList<LoacationBaseCoachDataDetails>();
    ArrayList<LoacationBaseCoachDataDetails> allCoachDetailsArrayList = new ArrayList<LoacationBaseCoachDataDetails>();
    LoacationBaseCoachDataDetails allCoachDetails;

     AlertDialog alertDialog;
     RadioButton Servicecheck_1 ;
     RadioButton Servicecheck_2;
     RadioButton Servicecheck_3 ;
     RadioButton Servicecheck_4 ;
     RadioButton Servicecheck_5 ;
     RadioButton Servicecheck_6 ;
     RadioButton Servicecheck_7;

    ArrayList<String> R_Frist_Name_Array = new ArrayList<String>();
    ArrayList<String> R_Second_Name_Array = new ArrayList<String>();
    ArrayList<String> R_Price_hr_Array = new ArrayList<String>();
    ArrayList<String> R_Price_ten_Array = new ArrayList<String>();
    ArrayList<String> R_Desc_Array = new ArrayList<String>();

    public IndividualCourseAdapter(Activity t,Context context, ArrayList<LoacationBaseCoachDataDetails> allCoachDetailsArrayList) {
        this.f = t;
        this.listdata=allCoachDetailsArrayList;
        this.context = context;
        this.allCoachDetailsArrayList.addAll(listdata);
    }
    //    public IndividualCourseAdapter(Context context, ArrayList<String> r_Frist_Name_Array, ArrayList<String> r_Second_Name_Array,
//                                   ArrayList<String> r_Price_hr_Array, ArrayList<String> r_Price_ten_Array,
//                                   ArrayList<String> r_Desc_Array) {
//        this.context = context;
//        R_Frist_Name_Array = r_Frist_Name_Array;
//        R_Second_Name_Array = r_Second_Name_Array;
//        R_Price_hr_Array = r_Price_hr_Array;
//        R_Price_ten_Array = r_Price_ten_Array;
//        R_Desc_Array = r_Desc_Array;
//    }

    @NonNull
    @Override
    public IndividualCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_coach_list, parent, false);
        return new IndividualCourseHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull IndividualCourseHolder holder, int position) {
        if (allCoachDetailsArrayList != null) {

            if (allCoachDetailsArrayList.get(position) != null) {

                allCoachDetails = allCoachDetailsArrayList.get(position);
                System.out.println("allCoachDetailsArrayList-->"+allCoachDetails.getId());
                if (allCoachDetails.getCoach_Description() != null) {
                    holder.Home_Coach_Desc.setText(allCoachDetails.getCoach_Description());
                } else {
                    holder.Home_Coach_Desc.setText("-");
                }
                if (allCoachDetails.getCoach_Fname() != null && allCoachDetails.getCoach_Lname() != null) {
                    holder.Home_Coach_Name.setText(allCoachDetails.getCoach_Fname() + " " + allCoachDetails.getCoach_Lname());
                } else if (allCoachDetails.getCoach_Fname() != null) {
                    holder.Home_Coach_Name.setText(allCoachDetails.getCoach_Fname());
                } else {
                    holder.Home_Coach_Name.setText("");
                }

//                if (allCoachDetails.getCoach_Price() != null) {
//                    holder.Home_Coach_Price_Hr.setText("1 Heure / " + allCoachDetails.getCoach_Price() + " €");
//                } else {
//                    holder.Home_Coach_Price_Hr.setText("");
//                }
//
//                if (allCoachDetails.getCoach_PriceX10() != null) {
//                    holder.Home_Coach_Price_Ten_Hr.setText("10 Heure / " + allCoachDetails.getCoach_PriceX10() + " €");
//                } else {
//                    holder.Home_Coach_Price_Ten_Hr.setText("");
//                }


                if (allCoachDetails.getCoach_Phone() != null) {
                    holder.Home_Coach_Price_Hr.setVisibility(View.VISIBLE);
                    holder.Home_Coach_Price_Hr.setText("Phone : " + allCoachDetails.getCoach_Phone());
                } else {
                    holder.Home_Coach_Price_Hr.setVisibility(View.GONE);
                    holder.Home_Coach_Price_Hr.setText("");
                }

                if (allCoachDetails.getCoach_Email() != null) {
                    holder.Home_Coach_Price_Ten_Hr.setVisibility(View.VISIBLE);
                    holder.Home_Coach_Price_Ten_Hr.setText("E-mail :  " + allCoachDetails.getCoach_Email());
                } else {
                    holder.Home_Coach_Price_Ten_Hr.setVisibility(View.GONE);
                    holder.Home_Coach_Price_Ten_Hr.setText("");
                }

                if(allCoachDetails.getCoach_Image() != null && !allCoachDetails.getCoach_Image().equals("") && !allCoachDetails.getCoach_Image().matches("http") && !allCoachDetails.getCoach_Image().contains("WWW.") && !allCoachDetails.getCoach_Image().contains("https") &&
                        !allCoachDetails.getCoach_Image().contains(".jpeg") && !allCoachDetails.getCoach_Image().contains(".png") && !allCoachDetails.getCoach_Image().contains("undefined") ){
                    String imageString = allCoachDetails.getCoach_Image();
                    System.out.println("123456-->"+position);
                    String ss = allCoachDetails.getCoach_Image();
                    ss = ss.replace("data:image/png;base64,","");
                    ss = ss.replace("data:image/jpeg;base64,","");
                    byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
//                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    Drawable drawable = new BitmapDrawable(context.getResources(), decodedImage);
//                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
                    holder.Profile_Coach_image.setImageDrawable(drawable);
                    System.out.println("123456-->"+position);
//                    Picasso.get().load("http://172.107.175.10:3000/upload/"+allCoachDetails.getCoach_Image()).fit().into(holder.Profile_Coach_image);
                } else{
                    System.out.println("12345622-->"+position);

                    Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(holder.Profile_Coach_image);

                }

                if(!allCoachDetails.getCoach_Services().isEmpty()){
                    if(allCoachDetails.getCoach_Services().contains("CoursIndividuel")){
                      holder.Servicecheck_1.setEnabled(true);
                    }
                    if(allCoachDetails.getCoach_Services().contains("CoursCollectifOndemand")){
                        holder.Servicecheck_2.setEnabled(true);
                    }
                    if(allCoachDetails.getCoach_Services().contains("CoursCollectifClub")){
                        holder.Servicecheck_3.setEnabled(true);
                    }
                }



//
//                holder.Home_Coach_Details.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(context, CoachDataActivity.class);
//                        intent.putExtra("Coach_id", allCoachDetails.getId());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    }
//                });

//                if (allCoachDetails.getCoach_Description() != null) {
//                    holder.Home_Coach_Desc.setText(allCoachDetails.getCoach_Description());
//                } else {
//                    holder.Home_Coach_Desc.setText("");
//                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != allCoachDetailsArrayList? allCoachDetailsArrayList.size() : 0);
    }
//
//    @Override
//    public Filter getFilter() {
//        return mfilter;
//    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {

            @Override
            public void run() {

                // Clear the filter list
                allCoachDetailsArrayList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    allCoachDetailsArrayList.addAll(listdata);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (LoacationBaseCoachDataDetails item : listdata) {
                        if (item.getCoach_Lname().toLowerCase().contains(text.toLowerCase()) ||
                                item.getCoach_Fname().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            allCoachDetailsArrayList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                (f).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    private void onCourseSelection(final int getAdapterPosition){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(f);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dailog_service, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        final RadioButton Servicecheck_1 =(RadioButton)dialogView.findViewById(R.id.servicecheck_1);
        final RadioButton Servicecheck_2 =(RadioButton)dialogView.findViewById(R.id.servicecheck_2);
        final RadioButton Servicecheck_3 =(RadioButton)dialogView.findViewById(R.id.servicecheck_3);
        final RadioButton Servicecheck_4 =(RadioButton)dialogView.findViewById(R.id.servicecheck_4);
        final RadioButton Servicecheck_5 =(RadioButton)dialogView.findViewById(R.id.servicecheck_5);
        final RadioButton Servicecheck_6 =(RadioButton)dialogView.findViewById(R.id.servicecheck_6);
        final RadioButton Servicecheck_7 =(RadioButton)dialogView.findViewById(R.id.servicecheck_7);
        Button Service_cancel =(Button) dialogView.findViewById(R.id.service_cancel);
        Button Service_ok =(Button) dialogView.findViewById(R.id.service_ok);
        Service_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Servicecheck_1.isChecked()){

                    selectedCourse = Servicecheck_1.getText().toString();
                    Intent intent = new Intent(context, CoachDataActivity.class);
                    Bundle b = new Bundle();
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursIndividuel");
                    intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Fname());
                    intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Lname());
                    intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Email());
                    intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
                    intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
                    intent.putExtra("location_coach",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Fname());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();

                }else if(Servicecheck_2.isChecked()){

                    selectedCourse = Servicecheck_2.getText().toString();
                    Intent intent = new Intent(context, CollectiveCourseActivity.class);
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursCollectifOndemand");
                    intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Fname());
                    intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Lname());
                    intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Email());
                    intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
                    intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();

                }else if(Servicecheck_3.isChecked()){

                    selectedCourse = Servicecheck_3.getText().toString();
                    Intent intent = new Intent(context, BookingClubDetails.class);
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursCollectifOndemand");
                    intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Fname());
                    intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Lname());
                    intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Email());
                    intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
                    intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();

                }else if(Servicecheck_4.isChecked()){

                    selectedCourse = Servicecheck_4.getText().toString();
                    Intent intent = new Intent(context, CoachDataActivity.class);
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursIndividuel");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();

                }else if(Servicecheck_5.isChecked()){

                    selectedCourse = Servicecheck_5.getText().toString();
                    Intent intent = new Intent(context, CoachDataActivity.class);
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursIndividuel");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();

                }else if(Servicecheck_6.isChecked()){

                    selectedCourse = Servicecheck_6.getText().toString();
                    Intent intent = new Intent(context, CoachDataActivity.class);
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursIndividuel");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();
                }else if(Servicecheck_7.isChecked()){
                    selectedCourse = Servicecheck_7.getText().toString();
                    Intent intent = new Intent(context, CoachDataActivity.class);
                    intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition).getId());
                    intent.putExtra("ServiceType", "CoursIndividuel");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context,"Select any service",Toast.LENGTH_LONG).show();
                }



                /*else{
                    Toast toast = Toast.makeText(MainActivity.this, "Select a course to proceed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }*/
//                Intent intent = new Intent(MainActivity.this, CoachTrainginDetails.class);
//                intent.putExtra(intentSelectedCourse, selectedCourse);
//                startActivity(intent);

            }
        });
        Service_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    public class IndividualCourseHolder extends RecyclerView.ViewHolder {
        TextView Home_Coach_Desc, Home_Coach_Price_Hr, Home_Coach_Price_Ten_Hr, Home_Coach_Name;
        Button Home_Coach_Details, Home_Coach_Reserce;
        ImageView Profile_Coach_image;
        AlertDialog.Builder dialogBuilder;
        LayoutInflater inflater;
        AlertDialog alertDialog;
        RadioButton Servicecheck_1, Servicecheck_2, Servicecheck_3, Servicecheck_4, Servicecheck_5, Servicecheck_6, Servicecheck_7;
        View dialogView;
        Button Service_cancel;
        Button Service_ok;
        public IndividualCourseHolder(@NonNull View itemView) {
            super(itemView);
            Home_Coach_Desc = (TextView) itemView.findViewById(R.id.home_coach_desc);
            Home_Coach_Price_Hr = (TextView) itemView.findViewById(R.id.home_coach_price_hr);
            Home_Coach_Price_Ten_Hr = (TextView) itemView.findViewById(R.id.home_coach_price_ten_hr);
            Home_Coach_Name = (TextView) itemView.findViewById(R.id.home_coach_name);
            Home_Coach_Details = (Button) itemView.findViewById(R.id.home_coach_details);
            Home_Coach_Reserce = (Button) itemView.findViewById(R.id.home_coach_reserce);
            Profile_Coach_image = (ImageView) itemView.findViewById(R.id.profile_coach_image);

            dialogBuilder = new AlertDialog.Builder(itemView.getContext());
            inflater = LayoutInflater.from(itemView.getContext());
            dialogView = inflater.inflate(R.layout.dailog_service, null);
            dialogBuilder.setView(dialogView);

             alertDialog = dialogBuilder.create();
             Servicecheck_1 =(RadioButton)dialogView.findViewById(R.id.servicecheck_1);
             Servicecheck_2 =(RadioButton)dialogView.findViewById(R.id.servicecheck_2);
             Servicecheck_3 =(RadioButton)dialogView.findViewById(R.id.servicecheck_3);
             Servicecheck_4 =(RadioButton)dialogView.findViewById(R.id.servicecheck_4);
             Servicecheck_5 =(RadioButton)dialogView.findViewById(R.id.servicecheck_5);
             Servicecheck_6 =(RadioButton)dialogView.findViewById(R.id.servicecheck_6);
             Servicecheck_7 =(RadioButton)dialogView.findViewById(R.id.servicecheck_7);
             Service_cancel =(Button) dialogView.findViewById(R.id.service_cancel);
             Service_ok =(Button) dialogView.findViewById(R.id.service_ok);
             Servicecheck_1.setEnabled(false);
             Servicecheck_2.setEnabled(false);
             Servicecheck_3.setEnabled(false);
             Servicecheck_4.setEnabled(false);
             Servicecheck_5.setEnabled(false);
             Servicecheck_6.setEnabled(false);
             Servicecheck_7.setEnabled(false);

            Home_Coach_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    onCourseSelection(getAdapterPosition());
                    Service_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(Servicecheck_1.isChecked()){

                                selectedCourse = Servicecheck_1.getText().toString();
                                Intent intent = new Intent(f, CoachDataActivity.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursIndividuel");
                                intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Fname());
                                intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Lname());
                                intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Email());
                                intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Phone());
                                intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Image());
//                                intent.putExtra("postal_code",allCoachDetailsArrayList.get(getAdapterPosition()).getPostalCode());
//                                intent.putExtra("postal_code_city",allCoachDetailsArrayList.get(getAdapterPosition()).getCityId());

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                alertDialog.dismiss();
//                                Servicecheck_1.setChecked(false);
                                f.startActivity(intent);


                            }else if(Servicecheck_2.isChecked()){
                                selectedCourse = Servicecheck_2.getText().toString();
                                Intent intent = new Intent(f, CollectiveCourseActivity.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursCollectifOndemand");
                                intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Fname());
                                intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Lname());
                                intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Email());
                                intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Phone());
                                intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Image());
//                                intent.putExtra("postal_code",allCoachDetailsArrayList.get(getAdapterPosition()).getPostalCode());
//                                intent.putExtra("postal_code_city",allCoachDetailsArrayList.get(getAdapterPosition()).getCityId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Servicecheck_2.setChecked(false);
                                f.startActivity(intent);
                                alertDialog.dismiss();
                            }else if(Servicecheck_3.isChecked()){

                                selectedCourse = Servicecheck_3.getText().toString();
                                Intent intent = new Intent(f, BookingClubDetails.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursCollectifOndemand");
                                intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Fname());
                                intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Lname());
                                intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Email());
                                intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Phone());
                                intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition()).getCoach_Image());
//                                intent.putExtra("postal_code",allCoachDetailsArrayList.get(getAdapterPosition()).getPostalCode());
//                                intent.putExtra("postal_code_city",allCoachDetailsArrayList.get(getAdapterPosition()).getCityId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                alertDialog.dismiss();
                                f.startActivity(intent);
//                                Servicecheck_3.setChecked(false);

                            }else if(Servicecheck_4.isChecked()){
                                selectedCourse = Servicecheck_4.getText().toString();
                                Intent intent = new Intent(context, CoachDataActivity.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursIndividuel");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                alertDialog.dismiss();
                            }else if(Servicecheck_5.isChecked()){
                                selectedCourse = Servicecheck_5.getText().toString();
                                Intent intent = new Intent(context, CoachDataActivity.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursIndividuel");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                alertDialog.dismiss();
                            }else if(Servicecheck_6.isChecked()){
                                selectedCourse = Servicecheck_6.getText().toString();
                                Intent intent = new Intent(context, CoachDataActivity.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursIndividuel");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                alertDialog.dismiss();
                            }else if(Servicecheck_7.isChecked()){
                                selectedCourse = Servicecheck_7.getText().toString();
                                Intent intent = new Intent(context, CoachDataActivity.class);
                                intent.putExtra("Coach_id", allCoachDetailsArrayList.get(getAdapterPosition()).getId());
                                intent.putExtra("ServiceType", "CoursIndividuel");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                alertDialog.dismiss();
                            }else {
                                Toast.makeText(context,"Select any service",Toast.LENGTH_LONG).show();
                            }


                /*else{
                    Toast toast = Toast.makeText(MainActivity.this, "Select a course to proceed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }*/
//                Intent intent = new Intent(MainActivity.this, CoachTrainginDetails.class);
//                intent.putExtra(intentSelectedCourse, selectedCourse);
//                startActivity(intent);

                        }
                    });

                    Service_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }
            });
        }

    }




}
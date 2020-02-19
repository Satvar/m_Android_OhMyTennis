package com.tech.cloudnausor.ohmytennis.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.services.animation.AnimationActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.collectivcourse.CollectiveCourseActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.stage.StageCoachActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.teambuilding.TeamBuildingActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.touranament.TournementActivity;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;
import com.tech.cloudnausor.ohmytennis.dto.OhMyEventsDTO;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OhMyEventAdapter extends RecyclerView.Adapter<OhMyEventAdapter.OhMyEventAdapterHolder> {

    Context context;
    ArrayList<OhMyEventsDTO.OhMyEventData> ohMyEventDataArrayList;
    String typeofEvent;
    ArrayList<EventDTO.AnimationCourse> animationCourseArrayList;
    ArrayList<EventDTO.AnimationCourse> stageCourseArrayList;
    ArrayList<EventDTO.AnimationCourse> tournamentCourseArrayList;
    ArrayList<EventDTO.AnimationCourse> teamBindingCourseArrayList;
    Bitmap decodedImage;
    String imageString = new String();


    public OhMyEventAdapter(Context context, ArrayList<EventDTO.AnimationCourse> animationCourseArrayList, ArrayList<EventDTO.AnimationCourse> stageCourseArrayList, ArrayList<EventDTO.AnimationCourse> tournamentCourseArrayList, String typeofEvent,
                            ArrayList<EventDTO.AnimationCourse> teamBindingCourseArrayList) {

        this.context = context;
        this.animationCourseArrayList = animationCourseArrayList;
        this.stageCourseArrayList = stageCourseArrayList;
        this.tournamentCourseArrayList = tournamentCourseArrayList;
        this.teamBindingCourseArrayList = teamBindingCourseArrayList;
        this.typeofEvent = typeofEvent;

    }

    @NonNull
    @Override
    public OhMyEventAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_stage_events,parent,false);
        return new OhMyEventAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OhMyEventAdapterHolder holder, int position) {

        if(typeofEvent.equals("stage")){
            EventDTO.AnimationCourse stageCourse = stageCourseArrayList.get(position);
            if (stageCourse.getPhoto() != null && stageCourse.getPhoto() != "" && !stageCourse.getPhoto().matches("http") && !stageCourse.getPhoto().contains("WWW.") && !stageCourse.getPhoto().contains("https") &&
                    !stageCourse.getPhoto().contains(".jpeg") && !stageCourse.getPhoto().contains(".png") && !stageCourse.getPhoto().contains("undefined")) {
                imageString = stageCourse.getPhoto();
                if(imageString.contains("data:image/jpeg;base64,")){
                    imageString = imageString.replace("data:image/jpeg;base64,","");
                }
                if(imageString.contains("data:image/png;base64,")){
                    imageString = imageString.replace("data:image/png;base64,","");
                }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.stage_event_image.setImageBitmap(decodedImage);

            }


            holder.stage_event_name.setText(stageCourse.getEventname());
            holder.date_disable.setVisibility(View.VISIBLE);

            holder.stage_event_descrition.setText(stageCourse.getDescription());
            String utcDateStr = stageCourse.getFrom_date();
            SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = null;
            try {
                date = sdf.parse(utcDateStr);
                holder.stage_event_date.setText( new  SimpleDateFormat("dd MMM yyyy").format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }

//            holder.stage_event_date.setText(stageCourse.getFrom_date());
            holder.stage_event_name.setVisibility(View.VISIBLE);
            holder.layoutfull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentstage = new Intent(context, StageCoachActivity.class);
                    Bundle d1 = new Bundle();
                    d1.putString("Coach_id", stageCourse.getCoach_Id());
                    d1.putString("Event_id", stageCourse.getId());
                    intentstage.putExtra("couse",d1);
                    intentstage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentstage);

                }
            });

//           if(position == 0){
//                   holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }


        }else if(typeofEvent.equals("animation")){

            EventDTO.AnimationCourse animationCourse = animationCourseArrayList.get(position);

            if (animationCourse.getPhoto() != null && animationCourse.getPhoto() != "" && !animationCourse.getPhoto().matches("http") && !animationCourse.getPhoto().contains("WWW.") && !animationCourse.getPhoto().contains("https") &&
                    !animationCourse.getPhoto().contains(".jpeg") && !animationCourse.getPhoto().contains(".png") && !animationCourse.getPhoto().contains("undefined")) {
                imageString = animationCourse.getPhoto();
                if(imageString.contains("data:image/jpeg;base64,")){
                    imageString = imageString.replace("data:image/jpeg;base64,","");
                }
                if(imageString.contains("data:image/png;base64,")){
                    imageString = imageString.replace("data:image/png;base64,","");
                }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);


                decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                holder.stage_event_image.setImageBitmap(decodedImage);
                System.out.println("imageString--."+ imageString);
            }
            holder.date_disable.setVisibility(View.GONE);
            holder.stage_event_descrition.setText(animationCourse.getDescription());
            System.out.println("imageString--."+ animationCourse.getDescription());

            holder.content_view.setVisibility(View.VISIBLE);
            holder.stage_event_name.setVisibility(View.GONE);

            holder.layoutfull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentstage = new Intent(context, AnimationActivity.class);
                    Bundle d1 = new Bundle();
                    d1.putString("Coach_id", animationCourse.getCoach_Id());
                    d1.putString("Event_id", animationCourse.getId());
                    intentstage.putExtra("couse",d1);
                    intentstage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentstage);

                }
            });

//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
        }

//       else if(typeofEvent.equals("teambuilding")){
//           holder.stage_event_descrition.setText(ohMyEventDataArrayList.get(position).getEventdescribution());
//           holder.start_date_layout.setVisibility(View.GONE);
//           holder.end_date_layout.setVisibility(View.GONE);
//           holder.start_date_layout_1.setVisibility(View.GONE);
//           holder.end_date_layout_1.setVisibility(View.GONE);
//           holder.stage_event_name.setVisibility(View.GONE);
//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
//       }
        else if(typeofEvent.equals("tournament")){
            EventDTO.AnimationCourse tournamentCourse = tournamentCourseArrayList.get(position);
            if (tournamentCourse.getPhoto() != null && tournamentCourse.getPhoto() != "" && !tournamentCourse.getPhoto().matches("http") && !tournamentCourse.getPhoto().contains("WWW.") && !tournamentCourse.getPhoto().contains("https") &&
                    !tournamentCourse.getPhoto().contains(".jpeg") && !tournamentCourse.getPhoto().contains(".png") && !tournamentCourse.getPhoto().contains("undefined")) {
                imageString = tournamentCourse.getPhoto();
                if(imageString.contains("data:image/jpeg;base64,")){
                    imageString = imageString.replace("data:image/jpeg;base64,","");
                }
                if(imageString.contains("data:image/png;base64,")){
                    imageString = imageString.replace("data:image/png;base64,","");
                }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.stage_event_image.setImageBitmap(decodedImage);
            }
            holder.stage_event_name.setText(tournamentCourse.getTournamentname());
            holder.stage_event_descrition.setText(tournamentCourse.getDescription());
            holder.date_disable.setVisibility(View.VISIBLE);


            String utcDateStr = tournamentCourse.getFrom_date();
            SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = null;
            try {
                date = sdf.parse(utcDateStr);
                holder.stage_event_date.setText( new  SimpleDateFormat("dd MMM yyyy").format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }

//            holder.stage_event_date.setText(tournamentCourse.getFrom_date());
            holder.stage_event_name.setVisibility(View.VISIBLE);
            holder.layoutfull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentstage = new Intent(context, TournementActivity.class);
                    Bundle d1 = new Bundle();
                    d1.putString("Coach_id", tournamentCourse.getCoach_Id());
                    d1.putString("Event_id", tournamentCourse.getId());
                    intentstage.putExtra("couse",d1);
                    intentstage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentstage);

                }
            });

//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
        }else if(typeofEvent.equals("teambuilding")){

            EventDTO.AnimationCourse teamBuildingCourse = teamBindingCourseArrayList.get(position);
            if (teamBuildingCourse.getPhoto() != null && teamBuildingCourse.getPhoto() != "" && !teamBuildingCourse.getPhoto().matches("http") && !teamBuildingCourse.getPhoto().contains("WWW.") && !teamBuildingCourse.getPhoto().contains("https") &&
                    !teamBuildingCourse.getPhoto().contains(".jpeg") && !teamBuildingCourse.getPhoto().contains(".png") && !teamBuildingCourse.getPhoto().contains("undefined")) {
                imageString = teamBuildingCourse.getPhoto();
                if(imageString.contains("data:image/jpeg;base64,")){
                    imageString = imageString.replace("data:image/jpeg;base64,","");
                }
                if(imageString.contains("data:image/png;base64,")){
                    imageString = imageString.replace("data:image/png;base64,","");
                }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.stage_event_image.setImageBitmap(decodedImage);
            }

            holder.stage_event_descrition.setText(teamBuildingCourse.getDescription());
            holder.stage_event_name.setVisibility(View.GONE);
            holder.date_disable.setVisibility(View.GONE);
            holder.layoutfull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentstage = new Intent(context, TeamBuildingActivity.class);
//                    Bundle d1 = new Bundle();

//                    System.out.println("new Gson().toJson(d1) ---> "+ new Gson().toJson(d1));
                    intentstage.putExtra("Coach_id", teamBuildingCourse.getCoach_Id());
                    intentstage.putExtra("Event_id", teamBuildingCourse.getId());
//                    d1.putString("Coach_id", teamBuildingCourse.getCoach_Id());
//                    d1.putString("Event_id", teamBuildingCourse.getId());
//                    intentstage.putExtra("couse",d1);

//                     System.out.println("new Gson().toJson(d1) ---> "+ new Gson().toJson(d1));

                    intentstage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentstage);

                }
            });

//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
        }

    }

    @Override
    public int getItemCount() {
        switch (typeofEvent) {
            case "animation":
                return animationCourseArrayList.size();
            case "stage":
                return stageCourseArrayList.size();
            case "tournament":
                return tournamentCourseArrayList.size();
            case "teambuilding":
                return teamBindingCourseArrayList.size();
        }
        return 0;
    }

    public class OhMyEventAdapterHolder extends RecyclerView.ViewHolder {

        TextView stage_event_name,stage_event_descrition,stage_event_date;
        ImageView stage_event_image;
        LinearLayout datelayout,layoutfull,content_view,date_disable;

        public OhMyEventAdapterHolder(@NonNull View itemView) {
            super(itemView);

            stage_event_name=(TextView)itemView.findViewById(R.id.stage_event_name);
            date_disable=(LinearLayout) itemView.findViewById(R.id.date_disable);
            stage_event_descrition=(TextView)itemView.findViewById(R.id.stage_event_descrition);
            stage_event_date=(TextView)itemView.findViewById(R.id.stage_event_date);
            stage_event_image =(ImageView) itemView.findViewById(R.id.stage_event_image);
            datelayout  =(LinearLayout) itemView.findViewById(R.id.datelayout);
            layoutfull =(LinearLayout) itemView.findViewById(R.id.layoutfull);
            content_view  =(LinearLayout) itemView.findViewById(R.id.content_view);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (typeofEvent) {
//
//                        case "animation":
//
//                            Intent intentanimation = new Intent(context, AnimationActivity.class);
//                            intentanimation.putExtra("Coach_id", animationCourseArrayList.get(getAdapterPosition()).getCoach_Id());
//                            intentanimation.putExtra("Event_id", animationCourseArrayList.get(getAdapterPosition()).getId());
////                            intent.putExtra("ServiceType", "CoursCollectifOndemand");
////                            intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getFirstName());
////                            intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getLastName());
////                            intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getEmail());
////                            intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
////                            intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
//                            intentanimation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                            context.startActivity(intentanimation);
//
//
////                            return animationCourseArrayList.size();
//                        case "stage":
//
//                            Intent intentstage = new Intent(context, StageCoachActivity.class);
//                            Bundle d1 = new Bundle();
//                            d1.putString("Coach_id", stageCourseArrayList.get(getAdapterPosition()).getCoach_Id());
//                            d1.putString("Event_id", stageCourseArrayList.get(getAdapterPosition()).getId());
//                            intentstage.putExtra("couse",d1);
////                            intentstage.putExtra("Coach_id", stageCourseArrayList.get(getAdapterPosition()).getCoach_Id());
////                            intentstage.putExtra("Event_id", stageCourseArrayList.get(getAdapterPosition()).getId());
////                            intent.putExtra("ServiceType", "CoursCollectifOndemand");
////                            intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getFirstName());
////                            intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getLastName());
////                            intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getEmail());
////                            intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
////                            intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
//                            intentstage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intentstage);
//
////                            return stageCourseArrayList.size();
//                        case "tournament":
//
//                            Intent intenttournament = new Intent(context, TournementActivity.class);
//                            Bundle d = new Bundle();
//                            d.putString("Coach_id", tournamentCourseArrayList.get(getAdapterPosition()).getCoach_Id());
//                            d.putString("Event_id", tournamentCourseArrayList.get(getAdapterPosition()).getId());
////                            intenttournament.putExtra("Coach_id", tournamentCourseArrayList.get(getAdapterPosition()).getCoach_Id());
////                            intenttournament.putExtra("Event_id", tournamentCourseArrayList.get(getAdapterPosition()).getId());
////                            intent.putExtra("ServiceType", "CoursCollectifOndemand");
////                            intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getFirstName());
////                            intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getLastName());
////                            intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getEmail());
////                            intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
////                            intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
//                            intenttournament.putExtra("couse",d);
//                            intenttournament.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intenttournament);
//
////                            return tournamentCourseArrayList.size();
//                        case "teambuilding":
//
//                            Intent intentteambuilding = new Intent(context, TeamBuildingActivity.class);
//                            intentteambuilding.putExtra("Coach_id", teamBindingCourseArrayList.get(getAdapterPosition()).getCoach_Id());
//                            intentteambuilding.putExtra("Event_id", teamBindingCourseArrayList.get(getAdapterPosition()).getId());
////                            intent.putExtra("ServiceType", "CoursCollectifOndemand");
////                            intent.putExtra("f_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getFirstName());
////                            intent.putExtra("l_name_coach", allCoachDetailsArrayList.get(getAdapterPosition).getLastName());
////                            intent.putExtra("gmail_coach", allCoachDetailsArrayList.get(getAdapterPosition).getEmail());
////                            intent.putExtra("number_coach", allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Phone());
////                            intent.putExtra("profileimag",allCoachDetailsArrayList.get(getAdapterPosition).getCoach_Image());
//                            intentteambuilding.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intentteambuilding);
//
////                            return teamBindingCourseArrayList.size();
//                    }
//                }
//            });

        }
    }

}

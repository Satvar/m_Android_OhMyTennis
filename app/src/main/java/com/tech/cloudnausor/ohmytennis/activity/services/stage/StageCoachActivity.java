package com.tech.cloudnausor.ohmytennis.activity.services.stage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.services.animation.AnimationActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.StageBookingDTO;
import com.tech.cloudnausor.ohmytennis.dto.StageDTO;
import com.tech.cloudnausor.ohmytennis.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StageCoachActivity extends AppCompatActivity {
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    View dialogView;
    LayoutInflater inflater;
    Button User_reserce,dialog_confirm,dialog_close;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    String coach_id = new String();
    String Event_id = new String();
    String gmail = new String();

    TextView user_coach_title,user_coach_name,user_coach_email,user_coach_number,from_date_id,to_date_id,price,
            tv_describtion,event_place,event_contact_number,event_transport,event_email,dialog_user_name,dialog_place
            ,dialog_date_time,dialog_dis_price,event_anem;
    ImageView GoBack,Profile_Image_Upload ;
    ImageView backk;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_="";
    StageBookingDTO stageBookingDTO = new StageBookingDTO();
    Button rediret_to_map;
    String redirect_address= "";
    String redirect_postal_code= "";
    ImageView back;
    SingleTonProcess singleTonProcess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_stage_coach);

        Bundle d = getIntent().getBundleExtra("couse");

        coach_id = d.getString("Coach_id");
        Event_id = d.getString("Event_id");

        sharedPreferences = getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");
        }

//        gmail = getIntent().getStringExtra("gmail_coach");
        singleTonProcess = singleTonProcess.getInstance();

        user_coach_title =(TextView)findViewById(R.id.user_coach_title);
        user_coach_name=(TextView)findViewById(R.id.user_coach_name);
        user_coach_email=(TextView)findViewById(R.id.user_coach_email);
        user_coach_number=(TextView)findViewById(R.id.user_coach_number);
        back = (ImageView)findViewById(R.id.back);
        event_anem=(TextView)findViewById(R.id.event_anem);
        from_date_id=(TextView)findViewById(R.id.from_date_id);
        to_date_id=(TextView)findViewById(R.id.to_date_id);
        price=(TextView)findViewById(R.id.price);
        tv_describtion=(TextView)findViewById(R.id.tv_describtion);
        event_place=(TextView)findViewById(R.id.event_place);
        event_contact_number=(TextView)findViewById(R.id.event_contact_number);
        event_transport=(TextView)findViewById(R.id.event_transport);
        event_email=(TextView)findViewById(R.id.event_email);
        Profile_Image_Upload = (ImageView)findViewById(R.id.profile_image_upload);
        backk = (ImageView)findViewById(R.id.back);
        User_reserce = (Button)findViewById(R.id.user_coach_reserce) ;

        dialogBuilder = new AlertDialog.Builder(this);
        inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.dailog_stage_booking_summary, null);
        dialogBuilder.setView(dialogView);

        dialog_user_name=(TextView)dialogView.findViewById(R.id.dialog_user_name);
        dialog_place=(TextView)dialogView.findViewById(R.id.dialog_place);
        dialog_date_time=(TextView)dialogView.findViewById(R.id.dialog_date_time);
        dialog_dis_price=(TextView)dialogView.findViewById(R.id.dialog_dis_price);
        dialog_confirm=(Button)dialogView.findViewById(R.id.dialog_confirm);
        dialog_close=(Button)dialogView.findViewById(R.id.dialog_close);

        alertDialog = dialogBuilder.create();

        rediret_to_map =  (Button) findViewById(R.id.rediret_to_map);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rediret_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+redirect_address+","+redirect_postal_code);
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+redirect_address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        User_reserce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singleTonProcess.show(StageCoachActivity.this);

                stageBookingDTO.setCoach_id(coach_id);
                stageBookingDTO.setUser_Id(userid_);
                stageBookingDTO.setStatus("R");
                stageBookingDTO.setBooking_date(from_date_id.getText().toString());
                stageBookingDTO.setBookingEnd(to_date_id.getText().toString());
                stageBookingDTO.setCourse("Stage");
                stageBookingDTO.setAmount(price.getText().toString().replace("€ ",""));

                apiInterface.getbookcourse(stageBookingDTO).enqueue(new Callback<StageDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                        assert response.body() != null;
                        if(response.body().getIsSuccess().equals("true")) {

                            System.out.println("new Gson()---> "+new Gson().toJson(response.body()));
                            alertDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Réservation réussie",Toast.LENGTH_LONG).show();

//                            if (!response.body().getData().getCourse().isEmpty()){
//
//                                if (response.body().getData().getCourse().get(0) != null) {
//                                    from_date_id.setText(response.body().getData().getCourse().get(0).getFrom_date());
//                                    to_date_id.setText(response.body().getData().getCourse().get(0).getTo_date());
//                                    price.setText(response.body().getData().getCourse().get(0).getPrice());
//                                    tv_describtion.setText(response.body().getData().getCourse().get(0).getDescription());
//                                    event_place.setText(response.body().getData().getCourse().get(0).getLocation());
//                                    event_transport.setText(response.body().getData().getCourse().get(0).getMode_of_transport());
//                                }
//
//                            }

                            singleTonProcess.dismiss();

                        }else {
                            singleTonProcess.dismiss();
                            System.out.println("new Gson()---> "+new Gson().toJson(response.body()));
                        }
                    }
                    @Override
                    public void onFailure(Call<StageDTO> call, Throwable t) {
                        singleTonProcess.dismiss();
                        System.out.println("---> "+ call +" "+ t);
                    }
                });

            }
        });

        getOnDemand();


    }

    public void getOnDemand(){

//        apiInterface.getCoachDeatils(gmail).enqueue(new Callback<CoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                if(response.body().getStatus().equals("true")) {
//                    if(response.body().getData() != null && response.body().getData().getCoachDetailsModel() != null){
//                    if(response.body().getData().getCoachDetailsModel().get(0) != null){
//                        user_coach_name.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Fname()+" "
//                        +response.body().getData().getCoachDetailsModel().get(0).getCoach_Lname());
//                        user_coach_number.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Phone());
//                        event_email.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Email());
//                        event_contact_number.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Phone());
////                        if(profilebase != null && !profilebase.equals("") && !profilebase.matches("http") && !profilebase.contains("WWW.") && !profilebase.contains("https") &&
////                                !profilebase.contains(".jpeg") && !profilebase.contains(".png") && !profilebase.contains("undefined") ){
////                            String imageString = profilebase;
//////            System.out.println("123456-->"+position);
////                            String ss = profilebase;
////                            ss = ss.replace("data:image/png;base64,","");
////                            ss = ss.replace("data:image/jpeg;base64,","");
////
////                            byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
//////                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
////                            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//////            Drawable drawable = new BitmapDrawable(context.getResources(), decodedImage);
//////                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
////                            Profile_Image_Upload.setImageBitmap(decodedImage);
//////            System.out.println("123456-->"+position);
//////                    Picasso.get().load("http://172.107.175.10:3000/upload/"+profilebase).fit().into(holder.Profile_Coach_image);
////                        } else{
////            System.out.println("12345622-->"+position);
//
//                            Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Profile_Image_Upload);
//
//                        }
//                    }
//
//                    }
//
//                }
//            }
//            @Override
//            public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
//
//            }
//        });

        singleTonProcess.show(StageCoachActivity.this);

        apiInterface.getstage(Event_id,coach_id).enqueue(new Callback<StageDTO>() {
            @Override
            public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                assert response.body() != null;
                if(response.body().getIsSuccess().equals("true")) {
                    if (!response.body().getData().getCourse().isEmpty()){

                        if (response.body().getData().getCourse().get(0) != null) {

                            String utcDateStr = response.body().getData().getCourse().get(0).getFrom_date();
                            SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            Date date = null;
                            try {
                                date = sdf.parse(utcDateStr);
                                from_date_id.setText( new  SimpleDateFormat("yyyy-MM-dd").format(date));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String utcDateStrto = response.body().getData().getCourse().get(0).getTo_date();
                            SimpleDateFormat sdfto = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            Date dateto = null;
                            try {
                                dateto = sdfto.parse(utcDateStrto);
                                to_date_id.setText( new  SimpleDateFormat("yyyy-MM-dd").format(dateto));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            event_anem.setText(response.body().getData().getCourse().get(0).getEventname());
                            dialog_date_time.setText(from_date_id.getText().toString()+ " To " + to_date_id.getText().toString());

//                            from_date_id.setText(response.body().getData().getCourse().get(0).getFrom_date());
//                            to_date_id.setText(response.body().getData().getCourse().get(0).getTo_date());
                            price.setText(response.body().getData().getCourse().get(0).getPrice());
                            tv_describtion.setText(response.body().getData().getCourse().get(0).getDescription());
                            event_place.setText(response.body().getData().getCourse().get(0).getLocation());
                            event_transport.setText(response.body().getData().getCourse().get(0).getMode_of_transport());
                            dialog_place.setText(response.body().getData().getCourse().get(0).getLocation());
                            dialog_dis_price.setText(response.body().getData().getCourse().get(0).getPrice());
                            dialog_user_name.setText(response.body().getData().getCourse().get(0).getEventname());
                            user_coach_name.setText(response.body().getData().getCourse().get(0).getFirstName()+" "
                                    +response.body().getData().getCourse().get(0).getLastName());
                            user_coach_number.setText(response.body().getData().getCourse().get(0).getMobile());
                            event_email.setText(response.body().getData().getCourse().get(0).getEmail());

                            if(response.body().getData().getCourse().get(0).getPhoto() != null && !response.body().getData().getCourse().get(0).getPhoto().equals("") && !response.body().getData().getCourse().get(0).getPhoto().matches("http") && !response.body().getData().getCourse().get(0).getPhoto().contains("WWW.") && !response.body().getData().getCourse().get(0).getPhoto().contains("https") &&
                                    !response.body().getData().getCourse().get(0).getPhoto().contains(".jpeg") && !response.body().getData().getCourse().get(0).getPhoto().contains(".png") && !response.body().getData().getCourse().get(0).getPhoto().contains("undefined") ){
                                String imageString = response.body().getData().getCourse().get(0).getPhoto();
                                String ss = response.body().getData().getCourse().get(0).getPhoto();
                                ss = ss.replace("data:image/png;base64,","");
                                ss = ss.replace("data:image/jpeg;base64,","");
                                byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
//                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                Drawable drawable = new BitmapDrawable(getResources(), decodedImage);
//                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
                                Profile_Image_Upload.setImageDrawable(drawable);
//                    Picasso.get().load("http://172.107.175.10:3000/upload/"+response.body().getData().getCourse().get(0).getPhoto()).fit().into(holder.Profile_Coach_image);
                            }

                            event_contact_number.setText(response.body().getData().getCourse().get(0).getMobile());
                            redirect_address = response.body().getData().getCourse().get(0).getLocation();
                            redirect_postal_code = response.body().getData().getCourse().get(0).getPostalCode();
                            singleTonProcess.dismiss();

                        }else {
                            singleTonProcess.dismiss();
                        }

//                            onDeamndModel = response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0);
//                            user_coach_title.setText(frist_name);
//                            user_coach_name.setText(frist_name+" "+last_name);
//                            user_coach_email.setText(gmail);
//                            user_coach_number.setText(phone);
//                            if(profilebase != null && !profilebase.equals("") && !profilebase.matches("http") && !profilebase.contains("WWW.") && !profilebase.contains("https") &&
//                                    !profilebase.contains(".jpeg") && !profilebase.contains(".png") && !profilebase.contains("undefined") ){
//                                String imageString = profilebase;
////            System.out.println("123456-->"+position);
//                                String ss = profilebase;
//                                ss = ss.replace("data:image/png;base64,","");
//                                ss = ss.replace("data:image/jpeg;base64,","");
//
//                                byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
////                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
////            Drawable drawable = new BitmapDrawable(context.getResources(), decodedImage);
////                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
//                                Profile_Image_Upload.setImageBitmap(decodedImage);
////            System.out.println("123456-->"+position);
////                    Picasso.get().load("http://172.107.175.10:3000/upload/"+profilebase).fit().into(holder.Profile_Coach_image);
//                            } else{
////            System.out.println("12345622-->"+position);
//
//                                Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Profile_Image_Upload);
//
//                            }
//                            price_2_person.setText(onDeamndModel.getPrice_2pl_1hr()+" € / 1 heure");
//                            price_3_person.setText(onDeamndModel.getPrice_3pl_1hr()+" € / 1 heure");
//                            price_4_person.setText(onDeamndModel.getPrice_4pl_1hr()+" € / 1 heure");
//                            price_5_person.setText(onDeamndModel.getPrice_5pl_1hr()+" € / 1 heure");
//                            price_6_person.setText(onDeamndModel.getPrice_6pl_1hr()+" € / 1 heure");
//                        }

                    }else {
                        singleTonProcess.dismiss();
                    }
                }else {
                    singleTonProcess.dismiss();
                }
            }
            @Override
            public void onFailure(Call<StageDTO> call, Throwable t) {
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

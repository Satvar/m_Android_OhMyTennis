package com.tech.cloudnausor.ohmytennis.activity.services.animation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.AnimationDTO;
import com.tech.cloudnausor.ohmytennis.dto.StageBookingDTO;
import com.tech.cloudnausor.ohmytennis.dto.StageDTO;
import com.tech.cloudnausor.ohmytennis.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennis.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimationActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    View dialogView;
    LayoutInflater inflater;
    Button User_reserce,avaibility_ok;

    TextView user_coach_title,user_coach_name,user_coach_email,user_coach_number,
            tv_describtion,event_place,event_contact_number,event_transport,event_email;
            EditText reserve_name__company
            ,reserve_email,reserve_phone,reserve_date,reserve_place,reseve_postal,reserve_number_person;
    ImageView GoBack,Profile_Image_Upload ;
    ImageView backk;
    String coach_id = new String();
    String Event_id = new String();

    String profilebase = new String();
    String gmail = new String();
    StageBookingDTO stageBookingDTO = new StageBookingDTO();
    ArrayList<StageBookingDTO.Reserve> reserveArrayList = new ArrayList<StageBookingDTO.Reserve>();
    StageBookingDTO.Reserve reserve = new StageBookingDTO.Reserve();
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_="";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    Calendar myCalendarTo = Calendar.getInstance();
    Button rediret_to_map,reserve_cancel;
    String redirect_address= "";
    String redirect_postal_code= "";
    SingleTonProcess singleTonProcess;
    ImageView go_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_animation);

        Bundle d = getIntent().getBundleExtra("couse");
        singleTonProcess = singleTonProcess.getInstance();

        coach_id = d.getString("Coach_id");
        Event_id = d.getString("Event_id");

        sharedPreferences = getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");
        }

        user_coach_title =(TextView)findViewById(R.id.user_coach_title);
        user_coach_name =(TextView)findViewById(R.id.user_coach_name);
        user_coach_email =(TextView)findViewById(R.id.user_coach_email);
        user_coach_number =(TextView)findViewById(R.id.user_coach_number);
        tv_describtion =(TextView)findViewById(R.id.tv_describtion);
        event_place =(TextView)findViewById(R.id.event_place);
        event_contact_number =(TextView)findViewById(R.id.event_contact_number);
        event_email =(TextView)findViewById(R.id.event_email);
        Profile_Image_Upload = (ImageView)findViewById(R.id.profile_image_upload);
        backk = (ImageView)findViewById(R.id.back);

        User_reserce = (Button)findViewById(R.id.user_coach_reserce) ;
        dialogBuilder = new AlertDialog.Builder(this);
        inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.animationdailolayout, null);
        dialogBuilder.setView(dialogView);

        reserve_name__company=(EditText)dialogView.findViewById(R.id.reserve_name__company);
        reserve_email=(EditText)dialogView.findViewById(R.id.reserve_email);
        reserve_phone=(EditText)dialogView.findViewById(R.id.reserve_phone);
        reserve_date=(EditText)dialogView.findViewById(R.id.reserve_date);
        reserve_place=(EditText)dialogView.findViewById(R.id.reserve_place);
        reseve_postal=(EditText)dialogView.findViewById(R.id.reseve_postal);
        reserve_cancel = (Button) dialogView.findViewById(R.id.avaibility_cancel);

        reserve_number_person=(EditText)dialogView.findViewById(R.id.reserve_number_person);
        avaibility_ok=(Button) dialogView.findViewById(R.id.avaibility_ok);
        alertDialog = dialogBuilder.create();

        rediret_to_map =  (Button) findViewById(R.id.rediret_to_map);

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

        reserve_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        backk.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 onBackPressed();
                                             }
                                         });
        User_reserce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        avaibility_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singleTonProcess.show(AnimationActivity.this);
                if(!reserve_date.getText().toString().equals("") && !reserve_date.getText().toString().equals("") && !reserve_place.getText().toString().equals("")
               && !reserve_phone.getText().toString().equals("") &&   !reserve_date.getText().toString().equals("") &&
                        !reserve_email.getText().toString().equals("") &&   !reserve_name__company.getText().toString().equals("") &&
                        !reserve_number_person.getText().toString().equals("") &&   !reseve_postal.getText().toString().equals("") ){


                stageBookingDTO.setCoach_id(coach_id);
                stageBookingDTO.setUser_Id(userid_);
                stageBookingDTO.setStatus("R");
                stageBookingDTO.setBooking_date(reserve_date.getText().toString());
                stageBookingDTO.setBookingEnd(reserve_date.getText().toString());
                stageBookingDTO.setCourse("Animation");
                stageBookingDTO.setAmount("");
                reserve.setCoach_Id(coach_id);
                reserve.setAddress(reserve_place.getText().toString());
                reserve.setCourse("Animation");
                reserve.setDate(reserve_date.getText().toString());
                reserve.setEmail(reserve_email.getText().toString());
                reserve.setLevel("");
                reserve.setMobile(reserve_phone.getText().toString());
                reserve.setName_of_company(reserve_name__company.getText().toString());
                reserve.setNumber_of_person(reserve_number_person.getText().toString());
                reserve.setPostalcode(reseve_postal.getText().toString());
                reserve.setUser_Id(userid_);
                reserveArrayList.add(reserve);
                stageBookingDTO.setReserve(reserveArrayList);

                apiInterface.getbookcourse(stageBookingDTO).enqueue(new Callback<StageDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                        if(response.body().getIsSuccess().equals("true")) {
                            alertDialog.dismiss();
                            singleTonProcess.dismiss();
                            Toast.makeText(getApplicationContext(),"Réservation réussie",Toast.LENGTH_LONG).show();
                            System.out.println("response.body()---> "+new Gson().toJson(response.body()));
                        }else {
                            singleTonProcess.dismiss();
                            System.out.println("response.body()---> "+new Gson().toJson(response.body()));
                        }
                    }
                    @Override
                    public void onFailure(Call<StageDTO> call, Throwable t) {
                        singleTonProcess.dismiss();
                        System.out.println("---> "+ call +" "+ t);
                    }
                });

            }else {
                    singleTonProcess.dismiss();
                    Toast.makeText(getApplicationContext(),"Indiquez les champs obligatoires.",Toast.LENGTH_LONG).show();
                }
            }
        });
        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTo();
            }
        };

        reserve_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Objects.requireNonNull(view.getContext()), dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        getOnDemand();
    }

    private void updateTo() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        reserve_date.setText(sdf.format(myCalendarTo.getTime()));
    }

    public void getOnDemand(){
        singleTonProcess.show(AnimationActivity.this);

//        apiInterface.getCoachDeatils(gmail).enqueue(new Callback<CoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                if(response.body().getStatus().equals("true")) {
//                    if(response.body().getData() != null && response.body().getData().getCoachDetailsModel() != null){
//                        if(response.body().getData().getCoachDetailsModel().get(0) != null){
//
//
//                            user_coach_name.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Fname()+" "
//                                    +response.body().getData().getCoachDetailsModel().get(0).getCoach_Lname());
//                            user_coach_number.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Phone());
//                            event_email.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Email());
//                            event_contact_number.setText(response.body().getData().getCoachDetailsModel().get(0).getCoach_Phone());
//                            if(profilebase != null && !profilebase.equals("") && !profilebase.matches("http") && !profilebase.contains("WWW.") && !profilebase.contains("https") &&
//                                    !profilebase.contains(".jpeg") && !profilebase.contains(".png") && !profilebase.contains("undefined") ){
//                                String imageString = profilebase;
//                                String ss = profilebase;
//                                ss = ss.replace("data:image/png;base64,","");
//                                ss = ss.replace("data:image/jpeg;base64,","");
//
//                                byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
//                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//                                Profile_Image_Upload.setImageBitmap(decodedImage);
//                            } else{
//                                Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Profile_Image_Upload);
//
//                            }
//                        }
//                    }
//
//                }
//            }
//            @Override
//            public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
//
//            }
//        });
        System.out.println(Event_id);
        System.out.println(coach_id);

        apiInterface.getanimation(coach_id,Event_id).enqueue(new Callback<AnimationDTO>() {
            @Override
            public void onResponse(@NonNull Call<AnimationDTO> call, @NonNull Response<AnimationDTO> response) {
                System.out.println("toJson---> "+new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")) {

                    if (!response.body().getData().getCourse().isEmpty()){
                        if (response.body().getData().getCourse().get(0) != null) {
//                            reserve_place.setText(response.body().getData().getCourse().get(0).getLocation());
                            redirect_address = response.body().getData().getCourse().get(0).getLocation();
                            redirect_postal_code = response.body().getData().getCourse().get(0).getPostalCode();
                            tv_describtion.setText(response.body().getData().getCourse().get(0).getDescription());
                            event_place.setText(response.body().getData().getCourse().get(0).getLocation());
                            user_coach_name.setText(response.body().getData().getCourse().get(0).getFirstName()+" "
                                    +response.body().getData().getCourse().get(0).getLastName());
                            user_coach_number.setText(response.body().getData().getCourse().get(0).getMobile());
                            event_email.setText(response.body().getData().getCourse().get(0).getEmail());
                            event_contact_number.setText(response.body().getData().getCourse().get(0).getMobile());
                            profilebase= response.body().getData().getCourse().get(0).getPhoto();
                            if(profilebase != null && !profilebase.equals("") && !profilebase.matches("http") && !profilebase.contains("WWW.") && !profilebase.contains("https") &&
                                    !profilebase.contains(".jpeg") && !profilebase.contains(".png") && !profilebase.contains("undefined") ){
                                String imageString = profilebase;
                                String ss = profilebase;
                                ss = ss.replace("data:image/png;base64,","");
                                ss = ss.replace("data:image/jpeg;base64,","");

                                byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                Profile_Image_Upload.setImageBitmap(decodedImage);
                            } else{
                                Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Profile_Image_Upload);

                            }
                            singleTonProcess.dismiss();
                        }else {
                            singleTonProcess.dismiss();
                        }
                    }else {
                        singleTonProcess.dismiss();
                    }

                }else {
                    singleTonProcess.dismiss();
                }
            }
            @Override
            public void onFailure(Call<AnimationDTO> call, Throwable t) {
                System.out.println("---> "+ call +" "+ t);
//                onBackPressed();
                singleTonProcess.dismiss();
            }
        });

    }

}

package com.tech.cloudnausor.ohmytennis.activity.services.touranament;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.services.teambuilding.TeamBuildingActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.StageBookingDTO;
import com.tech.cloudnausor.ohmytennis.dto.StageDTO;
import com.tech.cloudnausor.ohmytennis.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennis.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournementActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    View dialogView;
    LayoutInflater inflater;
    Button User_reserce,reserve_ok;
    TextView user_coach_title,user_coach_name,user_coach_email,user_coach_number,price,
            tv_describtion,event_contact_number,event_transport,event_email,event_place,from_date_id,to_date_id;
    EditText reserve_name_company,reserve_email,reserve_phone,reserve_date,reserve_number_person;
    AutoCompleteTextView reserve_level;
    ImageView GoBack,Profile_Image_Upload ;
    ImageView backk;
    String coach_id = new String();
    String profilebase = new String();
    String gmail = new String();
    StageBookingDTO stageBookingDTO = new StageBookingDTO();
    ArrayList<StageBookingDTO.Reserve> reserveArrayList = new ArrayList<StageBookingDTO.Reserve>();
    StageBookingDTO.Reserve reserve = new StageBookingDTO.Reserve();
    String Event_id = new String();
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_="";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    Calendar myCalendarTo = Calendar.getInstance();
    ArrayAdapter<String> adapter;
    Button rediret_to_map,reserve_cancel;
    String redirect_address= "";
    String redirect_postal_code= "";
    SingleTonProcess singleTonProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tournement);

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
        singleTonProcess = singleTonProcess.getInstance();

        user_coach_title =(TextView)findViewById(R.id.user_coach_title);
        user_coach_name =(TextView)findViewById(R.id.user_coach_name);
        user_coach_email =(TextView)findViewById(R.id.user_coach_email);
        user_coach_number =(TextView)findViewById(R.id.user_coach_number);
        price =(TextView)findViewById(R.id.price);
        event_place =(TextView)findViewById(R.id.event_place);
        tv_describtion =(TextView)findViewById(R.id.tv_describtion);
        event_transport =(TextView)findViewById(R.id.event_transport);
        event_contact_number =(TextView)findViewById(R.id.event_contact_number);
        event_email =(TextView)findViewById(R.id.event_email);
        Profile_Image_Upload = (ImageView)findViewById(R.id.profile_image_upload);
        backk = (ImageView)findViewById(R.id.back);
        from_date_id = (TextView)findViewById(R.id.from_date_id);
        to_date_id = (TextView)findViewById(R.id.to_date_id);

        User_reserce = (Button)findViewById(R.id.user_coach_reserce) ;
        dialogBuilder = new AlertDialog.Builder(this);
        inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.tournamentdailoglayout, null);
        dialogBuilder.setView(dialogView);

        reserve_name_company=(EditText)dialogView.findViewById(R.id.reserve_name_company);
        reserve_email=(EditText)dialogView.findViewById(R.id.reserve_email);
        reserve_phone=(EditText)dialogView.findViewById(R.id.reserve_phone);
        reserve_date=(EditText)dialogView.findViewById(R.id.reserve_date);
        reserve_level=(AutoCompleteTextView)dialogView.findViewById(R.id.reserve_level);
        reserve_number_person=(EditText)dialogView.findViewById(R.id.reserve_number_person);
        reserve_ok=(Button) dialogView.findViewById(R.id.avaibility_ok);
        reserve_cancel = (Button) dialogView.findViewById(R.id.avaibility_cancel);



        alertDialog = dialogBuilder.create();

        rediret_to_map =  (Button) findViewById(R.id.rediret_to_map);

        backk.setOnClickListener(new View.OnClickListener() {
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

        reserve_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
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
        reserve_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleTonProcess.show(TournementActivity.this);

                if(!from_date_id.getText().toString().equals("") && !to_date_id.getText().toString().equals("") && !event_place.getText().toString().equals("")
                && !reserve_date.getText().toString().equals("") && !event_email.getText().toString().equals("")
                && !reserve_level.getText().toString().equals("") && !reserve_number_person.getText().toString().equals("")
                ) {
                    reserveArrayList.clear();
                    stageBookingDTO.setCoach_id(coach_id);
                    stageBookingDTO.setUser_Id(userid_);
                    stageBookingDTO.setStatus("R");
                    stageBookingDTO.setBooking_date(from_date_id.getText().toString());
                    stageBookingDTO.setBookingEnd(to_date_id.getText().toString());
                    stageBookingDTO.setCourse("Tournoi");
                    stageBookingDTO.setAmount(price.getText().toString().replace("€ ", ""));
                    reserve.setCoach_Id(coach_id);
                    reserve.setAddress(event_place.getText().toString());
                    reserve.setCourse("Tournament");
                    reserve.setDate(reserve_date.getText().toString());
                    reserve.setEmail(event_email.getText().toString());
                    reserve.setLevel(reserve_level.getText().toString());
                    reserve.setMobile(reserve_phone.getText().toString());
                    reserve.setName_of_company(reserve_name_company.getText().toString());
                    reserve.setNumber_of_person(reserve_number_person.getText().toString());
                    reserve.setPostalcode("94210");
                    reserve.setUser_Id(userid_);
                    reserveArrayList.add(reserve);
                    stageBookingDTO.setReserve(reserveArrayList);

                    System.out.println("stageBookingDTO ---> " + new Gson().toJson(stageBookingDTO));

                    apiInterface.getbookcourse(stageBookingDTO).enqueue(new Callback<StageDTO>() {
                        @Override
                        public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                            System.out.println("(response.body()" + new Gson().toJson(response.body()));
                            if (response.body().getIsSuccess().equals("true")) {
                                System.out.println("getIsSuccess");
                                alertDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Réservation réussie", Toast.LENGTH_LONG).show();
                            } else {
                                singleTonProcess.dismiss();
                                System.out.println("getIsFail");
                            }
                            singleTonProcess.dismiss();
                        }

                        @Override
                        public void onFailure(Call<StageDTO> call, Throwable t) {
                            singleTonProcess.dismiss();
                            System.out.println("---> " + call + " " + t);
                        }
                    });
                }else {
                    singleTonProcess.dismiss();
                    Toast.makeText(getApplicationContext(),"Indiquez les champs obligatoires.",Toast.LENGTH_LONG).show();
                }
            }
        });

        ArrayList<String> level_name_list = new ArrayList<String>(Arrays.asList(new String[]{"Débutant", "Intermédiaire", "Avancée"}));

        adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, level_name_list);
        reserve_level.setAdapter(adapter);
        reserve_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                if(!level_name_list.isEmpty()){
                    reserve_level.showDropDown();}
                else {
                    Toast.makeText(arg0.getContext(),"No Data",Toast.LENGTH_LONG).show();
                }
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
        singleTonProcess.show(TournementActivity.this);

//        apiInterface.getCoachDeatils(gmail).enqueue(new Callback<CoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                if(response.body().getStatus().equals("true")) {
//                    if(response.body().getData() != null && response.body().getData().getCoachDetailsModel() != null){
//                        if(response.body().getData().getCoachDetailsModel().get(0) != null){
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
System.out.println("coach_id,Event_id---> "+ coach_id +"   "+ Event_id);
        apiInterface.gettournament(coach_id,Event_id).enqueue(new Callback<TournamentDTO>() {
            @Override
            public void onResponse(@NonNull Call<TournamentDTO> call, @NonNull Response<TournamentDTO> response) {
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
//                            from_date_id.setText(response.body().getData().getCourse().get(0).getFrom_date());
//                            to_date_id.setText(response.body().getData().getCourse().get(0).getTo_date());
                            tv_describtion.setText(response.body().getData().getCourse().get(0).getDescription());
                            event_transport.setText(response.body().getData().getCourse().get(0).getMode_of_transport());
                            event_place.setText(response.body().getData().getCourse().get(0).getLocation());
                            user_coach_name.setText(response.body().getData().getCourse().get(0).getFirstName()+" "
                                    +response.body().getData().getCourse().get(0).getLastName());
                            user_coach_number.setText(response.body().getData().getCourse().get(0).getMobile());
                            event_email.setText(response.body().getData().getCourse().get(0).getEmail());
                            event_contact_number.setText(response.body().getData().getCourse().get(0).getMobile());
                            profilebase = response.body().getData().getCourse().get(0).getPhoto();
                            redirect_address = response.body().getData().getCourse().get(0).getLocation();
                            redirect_postal_code = response.body().getData().getCourse().get(0).getPostalCode();

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
            public void onFailure(Call<TournamentDTO> call, Throwable t) {
                singleTonProcess.dismiss();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

}

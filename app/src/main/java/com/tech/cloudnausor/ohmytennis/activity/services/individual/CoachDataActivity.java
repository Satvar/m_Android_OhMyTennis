package com.tech.cloudnausor.ohmytennis.activity.services.individual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.DnsResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.cosmocalender.DefaultCalendarActivity;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.activity.register.RegisterActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.animation.AnimationActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennis.model.GetIndiCoachDetailsModel;
import com.tech.cloudnausor.ohmytennis.model.postalCodeList;
import com.tech.cloudnausor.ohmytennis.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennis.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachDataActivity extends AppCompatActivity {

    String coach_id = new String();
    String Service_Type = new String();
    String frist_name = new String();
    String last_name = new String();
    String gmail = new String();
    String phone = new String();
    String profilebase = new String();
//    String postalcode = new String();
//    String postalcodecity = new String();
    String coordonnees_gps = new String();
    private ArrayList<postalCodeList> registerPostalCodeLists = new ArrayList<postalCodeList>();



    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
    GetIndiCoachDetailsModel coachDetails = new GetIndiCoachDetailsModel();
    ImageView GoBack,Profile_Image_Upload ;
    TextView User_Coach_Title,Home_Coach_Name,User_Coach_Email,User_Coach_Number,User_Coach_Desc,User_Experience_Details,
    User_Coach_Price_Hr,User_Coach_Price_Ten_Hr;
    Button User_coach_reserce,rediret_to_map;
    SingleTonProcess singleTonProcess;

    String redirect_address= "";

//    Bundle extras = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_coach_data);

        Toolbar toolbar = findViewById(R.id.toolbar_indi);

//        singleTonProcess = singleTonProcess.getInstance();

        coach_id = getIntent().getStringExtra("Coach_id");
        Service_Type = getIntent().getStringExtra("ServiceType");
        frist_name = getIntent().getStringExtra("f_name_coach");
        last_name = getIntent().getStringExtra("l_name_coach");
        gmail = getIntent().getStringExtra("gmail_coach");
        phone = getIntent().getStringExtra("number_coach");
        profilebase = getIntent().getStringExtra("profileimag");
//        postalcode = getIntent().getStringExtra("postal_code");

//        postalcodecity = getIntent().getStringExtra("postal_code_city");

        GoBack = (ImageView)toolbar.findViewById(R.id.back);
        User_Coach_Title =(TextView)toolbar.findViewById(R.id.user_coach_title);
        Home_Coach_Name =(TextView)findViewById(R.id.user_coach_name);
        User_Coach_Email = (TextView)findViewById(R.id.user_coach_email);
        User_Coach_Number = (TextView)findViewById(R.id.user_coach_number);
        User_Coach_Desc = (TextView)findViewById(R.id.user_coach_desc);
        User_Experience_Details = (TextView)findViewById(R.id.user_experience_details);
        User_Coach_Price_Hr = (TextView)findViewById(R.id.user_coach_price_hr);
        User_Coach_Price_Ten_Hr = (TextView)findViewById(R.id.user_coach_price_ten_hr);
        Profile_Image_Upload = (ImageView)findViewById(R.id.profile_image_upload);
        rediret_to_map =  (Button) findViewById(R.id.rediret_to_map);
//        singleTonProcess.show(getApplicationContext());
        ArrayList<postalCodeList> registerPostalCodeLists = new ArrayList<postalCodeList>();

//        if(postalcode.length() == 5){
//            System.out.println(postalcode);
//            registerPostalCodeLists.clear();
//            System.out.println("postalcode---> "+ postalcode);
//            apiRequest.getPostalField(postalcode).enqueue(new Callback<RegisterPostalCodeList>() {
//                @Override
//                public void onResponse(Call<RegisterPostalCodeList> call, Response<RegisterPostalCodeList> response) {
//                    if(response.body() != null){
//
//                        if(response.body().getData() != null){
//                            if(response.body().getData().getPostalCodeListArrayList() != null){
//                                registerPostalCodeLists.addAll(response.body().getData().getPostalCodeListArrayList());
//                                for(int i =0;i<registerPostalCodeLists.size();i++){
//                                    postalCodeList p = registerPostalCodeLists.get(i);
//                                    if(p.getCode_postal().equals(postalcode) && p.getId().equals(postalcodecity)){
//                                        coordonnees_gps = p.getCoordonnees_gps();
//                                        System.out.println("coordonnees_gps---> "+ coordonnees_gps);
//                                    }
//                                }
//                            }else {
//                                coordonnees_gps = "";
//                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.r_code_portal_errror),Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<RegisterPostalCodeList> call, Throwable t) {
//                    System.out.println("error 1 "+ call);
//                    System.out.println("error 2 "+ t);
//
//                }
//            });
//
//        }



        rediret_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+redirect_address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        User_coach_reserce = (Button)findViewById(R.id.user_coach_reserce);
        User_Coach_Title.setText(frist_name);
        Home_Coach_Name.setText(frist_name+" "+last_name);
        User_Coach_Email.setText(gmail);
        User_Coach_Number.setText(phone);

        if(profilebase != null && !profilebase.equals("") && !profilebase.matches("http") && !profilebase.contains("WWW.") && !profilebase.contains("https") &&
                !profilebase.contains(".jpeg") && !profilebase.contains(".png") && !profilebase.contains("undefined") ){
            String imageString = profilebase;
//            System.out.println("123456-->"+position);
            String ss = profilebase;
            ss = ss.replace("data:image/png;base64,","");
            ss = ss.replace("data:image/jpeg;base64,","");

            byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
//                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//            Drawable drawable = new BitmapDrawable(context.getResources(), decodedImage);
//                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
            Profile_Image_Upload.setImageBitmap(decodedImage);
//            System.out.println("123456-->"+position);
//                    Picasso.get().load("http://172.107.175.10:3000/upload/"+profilebase).fit().into(holder.Profile_Coach_image);
        } else{
//            System.out.println("12345622-->"+position);
            Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Profile_Image_Upload);
        }

        User_coach_reserce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachDataActivity.this, DefaultCalendarActivity.class);
                intent.putExtra("Coach_id",coach_id );
                intent.putExtra("ServiceType", Service_Type);
                intent.putExtra("f_name_coach", frist_name);
                intent.putExtra("l_name_coach", last_name);
                intent.putExtra("gmail_coach", gmail);
                intent.putExtra("number_coach", phone);
                startActivity(intent);
            }
        });

        apiRequest.getGetIndiCourseDetails(coach_id).enqueue(new Callback<GetIndiCoachDetailsResponse>() {
            @Override
            public void onResponse(Call<GetIndiCoachDetailsResponse> call, Response<GetIndiCoachDetailsResponse> response) {
                if(response.body() != null){
               if(response.body().getGetIndiCoachDetailsModel().getIndicouresedata() != null && response.body().getGetIndiCoachDetailsModel().getIndicouresedata().size() != 0){
                  coachDetails = response.body().getGetIndiCoachDetailsModel().getIndicouresedata().get(0);
                  User_Coach_Price_Hr.setText(coachDetails.getPrice_min()+" € / 1 heure");
                  User_Coach_Price_Ten_Hr.setText(coachDetails.getPrice_max()+" € / 10 heure");
                  User_Coach_Desc.setText(coachDetails.getDescription());
                  redirect_address = coachDetails.getLocation()+","+coachDetails.getPostalcode();
//                  singleTonProcess.dismiss();
              }else{
//                  singleTonProcess.dismiss();
              }
                }else {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
            @Override
            public void onFailure(Call<GetIndiCoachDetailsResponse> call, Throwable t) {
//                singleTonProcess.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(CoachDataActivity.this, IndividualCourseHomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }
}

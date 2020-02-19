package com.tech.cloudnausor.ohmytennis.activity.services.collectivecourseclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.cosmocalender.DefaultCalendarActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.animation.AnimationActivity;
import com.tech.cloudnausor.ohmytennis.activity.services.individual.CoachDataActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.model.OndemandCourseModel;
import com.tech.cloudnausor.ohmytennis.model.postalCodeList;
import com.tech.cloudnausor.ohmytennis.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennis.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectiveClubActivity extends AppCompatActivity {
    Button User_coach_reserce;
    String coach_id = new String();
    String Service_Type = new String();
    String frist_name = new String();
    String last_name = new String();
    String gmail = new String();
    String phone = new String();
    String profilebase = new String();
    ImageView GoBack,Profile_Image_Upload ;
    ImageView backk;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    OndemandCourseModel onDeamndModel = new OndemandCourseModel();
    TextView user_coach_title,user_coach_name,user_coach_email,user_coach_number,price_2_person,price_3_person
            ,price_4_person,price_5_person,price_6_person;
    Button rediret_to_map;
    String redirect_address= "";
    //    String postalcode = new String();
//    String postalcodecity = new String();
    String coordonnees_gps = new String();
    SingleTonProcess singleTonProcess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_collective_course);
        User_coach_reserce = (Button)findViewById(R.id.user_coach_reserce);
        singleTonProcess = singleTonProcess.getInstance();

        coach_id = getIntent().getStringExtra("Coach_id");
        Service_Type = getIntent().getStringExtra("ServiceType");
        frist_name = getIntent().getStringExtra("f_name_coach");
        last_name = getIntent().getStringExtra("l_name_coach");
        gmail = getIntent().getStringExtra("gmail_coach");
        phone = getIntent().getStringExtra("number_coach");
        profilebase = getIntent().getStringExtra("profileimag");
//        postalcode = getIntent().getStringExtra("postal_code");
//        postalcodecity = getIntent().getStringExtra("postal_code_city");

        user_coach_title =(TextView)findViewById(R.id.user_coach_title);
        user_coach_name=(TextView)findViewById(R.id.user_coach_name);
        user_coach_email=(TextView)findViewById(R.id.user_coach_email);
        user_coach_number=(TextView)findViewById(R.id.user_coach_number);
        price_2_person=(TextView)findViewById(R.id.price_2_person);
        price_3_person=(TextView)findViewById(R.id.price_3_person);
        price_4_person=(TextView)findViewById(R.id.price_4_person);
        price_5_person=(TextView)findViewById(R.id.price_5_person);
        price_6_person=(TextView)findViewById(R.id.price_6_person);
        Profile_Image_Upload = (ImageView)findViewById(R.id.profile_image_upload);
        backk = (ImageView)findViewById(R.id.back);
        rediret_to_map =  (Button) findViewById(R.id.rediret_to_map);
        ArrayList<postalCodeList> registerPostalCodeLists = new ArrayList<postalCodeList>();

//        if(postalcode.length() == 5){
//            System.out.println(postalcode);
//            registerPostalCodeLists.clear();
//            System.out.println("postalcode---> "+ postalcode);
//            apiInterface.getPostalField(postalcode).enqueue(new Callback<RegisterPostalCodeList>() {
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
//                }
//            });
//
//        }

        rediret_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+redirect_address);
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+redirect_address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });



        User_coach_reserce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectiveClubActivity.this, DefaultCalendarActivity.class);
                intent.putExtra("Coach_id",coach_id );
                intent.putExtra("ServiceType", Service_Type);
                intent.putExtra("f_name_coach", frist_name);
                intent.putExtra("l_name_coach", last_name);
                intent.putExtra("gmail_coach", gmail);
                intent.putExtra("number_coach", phone);
//                intent.putExtra("pl_one",onDeamndModel.getPrice_2pl_1hr());
//                intent.putExtra("pl_two",onDeamndModel.getPrice_3pl_1hr());
//                intent.putExtra("pl_three",onDeamndModel.getPrice_4pl_1hr());
//                intent.putExtra("pl_four",onDeamndModel.getPrice_5pl_1hr());
//                intent.putExtra("pl_five",onDeamndModel.getPrice_6pl_1hr());
                startActivity(intent);
            }
        });
        getOnDemand();


        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getOnDemand(){
        singleTonProcess.show(CollectiveClubActivity.this);

        apiInterface.getcousecollectivedemanad(coach_id).enqueue(new Callback<GetCoachCollectiveOnDemandResponseData>() {
            @Override
            public void onResponse(@NonNull Call<GetCoachCollectiveOnDemandResponseData> call, @NonNull Response<GetCoachCollectiveOnDemandResponseData> response) {
                assert response.body() != null;
                if(response.body().getIsSuccess().equals("true")) {
                    if (!response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().isEmpty()){
                        if (response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0) != null) {

                            onDeamndModel = response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0);
                            user_coach_title.setText(frist_name);
                            user_coach_name.setText(frist_name+" "+last_name);
                            user_coach_email.setText(gmail);
                            user_coach_number.setText(phone);
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

                            price_2_person.setText(onDeamndModel.getPrice_2pl_1hr()+" € / 1 heure");
                            price_3_person.setText(onDeamndModel.getPrice_3pl_1hr()+" € / 1 heure");
                            price_4_person.setText(onDeamndModel.getPrice_4pl_1hr()+" € / 1 heure");
                            price_5_person.setText(onDeamndModel.getPrice_5pl_1hr()+" € / 1 heure");
                            price_6_person.setText(onDeamndModel.getPrice_6pl_1hr()+" € / 1 heure");
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
            public void onFailure(Call<GetCoachCollectiveOnDemandResponseData> call, Throwable t) {
                singleTonProcess.dismiss();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

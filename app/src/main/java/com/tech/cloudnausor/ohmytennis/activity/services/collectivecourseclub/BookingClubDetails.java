package com.tech.cloudnausor.ohmytennis.activity.services.collectivecourseclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.services.animation.AnimationActivity;
import com.tech.cloudnausor.ohmytennis.adapter.ClubAvaibilityAdapter;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.model.ClubAvabilityModelData;
import com.tech.cloudnausor.ohmytennis.model.GetClubModel;
import com.tech.cloudnausor.ohmytennis.response.GetClubResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.CircleImageView;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingClubDetails extends AppCompatActivity {
    ClubAvaibilityAdapter clubAvaibilityAdapter ;
    ArrayList<ArrayList<ClubAvabilityModelData>> clubavabilityfull = new ArrayList<ArrayList<ClubAvabilityModelData>>();
    RecyclerView clubAvaibility;
    String enable = "1";
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_ = null;
    String location =null;
    ApiInterface apiInterface;
    String coach_id = new String();
    String Service_Type = new String();
    String frist_name = new String();
    String last_name = new String();
    String gmail = new String();
    String phone = new String();
    String profilebase = new String();
    GetClubModel getClubModel = new GetClubModel();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    String coach_name = "";
    SingleTonProcess singleTonProcess;
    CircleImageView profile_image_upload;
    TextView tv_describtion,user_coach_name,user_coach_title;
    ImageView back;
    Button rediret_to_map;
   String redirect_address = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_booking_club_details);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        if(sharedPreferences.contains("location")){
            location = sharedPreferences.getString("location","");
            System.out.println(" location--> " + location);
        }

        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");
        }

        singleTonProcess = singleTonProcess.getInstance();
        profile_image_upload = (CircleImageView)findViewById(R.id.profile_image_upload);
        user_coach_name = (TextView)findViewById(R.id.user_coach_name);
        tv_describtion = (TextView)findViewById(R.id.tv_describtion);
        user_coach_title = (TextView)findViewById(R.id.user_coach_title);
        back = (ImageView)findViewById(R.id.back);
        rediret_to_map = (Button)findViewById(R.id.rediret_to_map);

        coach_id = getIntent().getStringExtra("Coach_id");
        Service_Type = getIntent().getStringExtra("ServiceType");
        frist_name = getIntent().getStringExtra("f_name_coach");
        last_name = getIntent().getStringExtra("l_name_coach");
        gmail = getIntent().getStringExtra("gmail_coach");
        phone = getIntent().getStringExtra("number_coach");
        profilebase = getIntent().getStringExtra("profileimag");
         coach_name = frist_name+" "+last_name;


        user_coach_name.setText(coach_name);
        user_coach_title.setText(coach_name);

        rediret_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+redirect_address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});
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
            profile_image_upload.setImageBitmap(decodedImage);
            profile_image_upload.setBackgroundTintList(null);
//            System.out.println("123456-->"+position);
//                    Picasso.get().load("http://172.107.175.10:3000/upload/"+profilebase).fit().into(holder.Profile_Coach_image);
        } else{
//            System.out.println("12345622-->"+position);
            Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(profile_image_upload);
            profile_image_upload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        clubAvaibility=(RecyclerView)findViewById(R.id.clubAvaibility);
//        clubAvaibilityAdapter = new ClubAvaibilityAdapter(getApplicationContext(),clubavabilityfull,this,enable,coach_name,getClubModel.getPlace());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        clubAvaibility.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        clubAvaibility.setAdapter(clubAvaibilityAdapter);
        getClub();


    }
    private void getClub(){
        singleTonProcess.show(BookingClubDetails.this);

        apiInterface.getcousecollectiveclub(coach_id).enqueue(new Callback<GetClubResponseData>() {
            @Override
            public void onResponse(@NonNull Call<GetClubResponseData> call, @NonNull Response<GetClubResponseData> response) {
                assert response.body() != null;
                if(response.body().getIsSuccess().equals("true")) {
                    if (!response.body().getClubDataModel().getGetClubModels().isEmpty()){
                        if (response.body().getClubDataModel().getGetClubModels().get(0) != null) {

                            getClubModel = response.body().getClubDataModel().getGetClubModels().get(0);
                            tv_describtion.setText(getClubModel.getDescription());
                            redirect_address = getClubModel.getPostalcode();
                            clubAvaibilityAdapter = new ClubAvaibilityAdapter(BookingClubDetails.this,clubavabilityfull,BookingClubDetails.this,enable,coach_name,getClubModel.getPlace());
                            clubAvaibility.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            clubAvaibility.setAdapter(clubAvaibilityAdapter);
                        }
                        if(response.body().getClubDataModel().getClubAvabilityModelData() != null){
                            Intil(response.body().getClubDataModel().getClubAvabilityModelData());
                        }
                        singleTonProcess.dismiss();
                    }else {
                        singleTonProcess.dismiss();
                    }
                }else {
                    singleTonProcess.dismiss();
                }
            }
            @Override
            public void onFailure(Call<GetClubResponseData> call, Throwable t) {
                singleTonProcess.dismiss();
                System.out.println("---> "+ call +" "+ t);
            }
        });
    }

    public void Intil(ArrayList<ClubAvabilityModelData> clubAvabilityModelData){
//        ArrayList<ClubAvabilityModelData> clubAvabilityModelData = new ArrayList<ClubAvabilityModelData>();
//
//        clubAvabilityModelData.add(new ClubAvabilityModelData("","bala","1","1","1","1","Tennis",""));
//        clubAvabilityModelData.add(new ClubAvabilityModelData("","bala","1","1","1","1","Tennis",""));
//        clubAvabilityModelData.add(new ClubAvabilityModelData("","bala","1","1","1","1","Tennis1",""));

        for(int i=0;i<clubAvabilityModelData.size();i++){
            ClubAvabilityModelData clubAvabilityModelData1 = clubAvabilityModelData.get(i);
            if(clubavabilityfull.size() != 0){
                for (int k=0;k < clubavabilityfull.size();k++){
                    ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList = clubavabilityfull.get(k);
                    System.out.println("ArrayList---> "+new Gson().toJson(clubAvabilityModelDataArrayList));
                    ClubAvabilityModelData clubAvabilityModelData2 = clubAvabilityModelDataArrayList.get(0);
                    if(clubAvabilityModelData2.getCourse().equals(clubAvabilityModelData1.getCourse()) && clubAvabilityModelData2.getWeekday().equals(clubAvabilityModelData1.getWeekday())){
                        clubAvabilityModelDataArrayList.add(clubAvabilityModelData1);
                        clubavabilityfull.set(k,clubAvabilityModelDataArrayList);
                        break;
                    }else if(clubavabilityfull.size() == k+1){
                        ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
                        clubAvabilityModelDataArrayList1.add(clubAvabilityModelData1);
                        clubavabilityfull.add(clubAvabilityModelDataArrayList1);
                        break;
                    }
                }
            }else {
                ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
                clubAvabilityModelDataArrayList1.add(clubAvabilityModelData1);
                clubavabilityfull.add(clubAvabilityModelDataArrayList1);
            }
        }

        clubAvaibilityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

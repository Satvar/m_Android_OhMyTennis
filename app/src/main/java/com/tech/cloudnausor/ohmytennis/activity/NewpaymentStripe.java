package com.tech.cloudnausor.ohmytennis.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.Settings;
import com.tech.cloudnausor.ohmytennis.activity.homepage.NewHomePage;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.SetPaymentDTO;
import com.tech.cloudnausor.ohmytennis.model.PaymentAvaibilityData;
import com.tech.cloudnausor.ohmytennis.response.PaymentResponseData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewpaymentStripe extends AppCompatActivity  implements View.OnClickListener {

    CardMultilineWidget cardMultilineWidget;
    Button save_payment;
    String booking_id = "",amount ="";
    ImageView back;
    TextView payment_form_title;

    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_multiline);
        initView();
    }

    private void initView() {

        PaymentConfiguration.init(this, Settings.PUBLISHABLE_KEY);

        cardMultilineWidget = findViewById(R.id.card_multiline_widget);
        save_payment = findViewById(R.id.save_payment);
        payment_form_title=  findViewById(R.id.payment_form_title);
        back =  findViewById(R.id.back);
        save_payment.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        booking_id = getIntent().getStringExtra("bookvalue");

        apiRequest.bookingDetail(booking_id).enqueue(new Callback<PaymentResponseData>() {
            @Override
            public void onResponse(Call<PaymentResponseData> call, Response<PaymentResponseData> response) {
                if (response.body().getIsSuccess().equals("true")) {
                    if(response.body().getData() != null){
                        if(response.body().getData().getPaymentAvaibilityData() != null){
                            PaymentAvaibilityData bookingData = response.body().getData().getPaymentAvaibilityData().get(0);
                            if(bookingData.getBookingCourse().equals("CoursCollectifClub")){
                                String sss = "Payer "+bookingData.getDiscount_club()+" €";
                                save_payment.setText(sss);
                                amount = bookingData.getDiscount_club();
                                payment_form_title.setText("Booking ID : "+bookingData.getBooking_Id());
                            }else {
                            String sss = "Payer "+bookingData.getAmount()+" €";
                            save_payment.setText(sss);
                            amount = bookingData.getAmount();
                            payment_form_title.setText("Booking ID : "+bookingData.getBooking_Id());
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"No booking data found", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), NewHomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), NewHomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), NewHomePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PaymentResponseData> call, Throwable t) {

            }
        });





//        cardPaymenrStripeBinding.paymentCard.setOnClickListener((View view) -> {
//            Card card = cardPaymenrStripeBinding.cardMultilineWidget.getCard();
//            boolean validation = card.validateCard();
//            if(validation){
//
//                stripe =new Stripe(getContext(),"pk_test_LdDO8f12bgdmxn0YyvrDhh9y00RYrrksxL");
//                stripe.createToken(card, new ApiResultCallback<Token>() {
//
//                    @Override
//                    public void onSuccess(@android.support.annotation.NonNull Token result) {
//                        System.out.println("result --->"+ result.getId());
//                        callOrderBookApi();
//                    }
//
//                    @Override
//                    public void onError(@android.support.annotation.NonNull Exception e) {
//                        System.out.println("result --->"+ e);
//                    }
//                });
//
//            }else {
//
//            }
//
//    });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_payment:
                if(cardMultilineWidget.getCard() != null ){

                    Card card = cardMultilineWidget.getCard();
                boolean validation = card.validateCard();
                if(validation){

//                    stripe =new Stripe(getContext(),"pk_test_LdDO8f12bgdmxn0YyvrDhh9y00RYrrksxL");
                Stripe   stripe =new Stripe(getApplicationContext(),Settings.PUBLISHABLE_KEY);
                System.out.println("booking_id-->"+booking_id+"amount--->"+amount);
                stripe.createToken(card, new ApiResultCallback<Token>() {
                    @Override
                    public void onSuccess(@NonNull Token result) {

                        apiRequest.setpayment("B",booking_id,amount,result.getId()).enqueue(new Callback<SetPaymentDTO>() {
                            @Override
                            public void onResponse(Call<SetPaymentDTO> call, Response<SetPaymentDTO> response) {
                                System.out.println("response-->" +new Gson().toJson(response.body()));

                                if (response.body().getIsSuccess().toString() == "true") {
                                    Toast.makeText(getApplicationContext(), "Paiement réussi", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), NewHomePage.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Paiement échoué", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(getApplicationContext(), NewHomePage.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
                                }

                            }


                            @Override
                            public void onFailure(Call<SetPaymentDTO> call, Throwable t) {
                          System.out.println(""+t.toString());
                            }
                        });

                    }

                    @Override
                    public void onError(@NonNull Exception e) {

                    }
                });

                }
        }else {
                    Toast.makeText(getApplicationContext(),"Veuillez entrer une carte valide",Toast.LENGTH_LONG).show();

                }
//                callOrderBookApi();
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

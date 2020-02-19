package com.tech.cloudnausor.ohmytennis.activity.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennis.activity.register.RegisterActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {
    TextView GoLogin,AlertMessage;
    TextInputLayout F_User_Id_Error;
    Button F_Cancel,F_Send_Mail,AlertPositive;
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextInputEditText F_User_Id;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SingleTonProcess singleTonProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_forgot);
        singleTonProcess = singleTonProcess.getInstance();

        GoLogin =(TextView)findViewById(R.id.gofloginid);
        F_User_Id =(TextInputEditText)findViewById(R.id.f_user_id);
        F_Cancel =(Button) findViewById(R.id.f_cancel);
        F_Send_Mail = (Button) findViewById(R.id.f_find);
        F_User_Id_Error =(TextInputLayout)findViewById(R.id.f_user_id_error) ;

        GoLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



        F_User_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Objects.requireNonNull(F_User_Id.getText()).toString().trim().matches(emailPattern) && F_User_Id.getText().toString().trim().length()>0){
                    F_User_Id_Error.setErrorEnabled(false);

                }else{
                    F_User_Id_Error.setErrorEnabled(true);
                    F_User_Id_Error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        F_User_Id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(Objects.requireNonNull(F_User_Id.getText()).toString().trim().matches(emailPattern) && F_User_Id.getText().toString().trim().length()>0){
                    F_User_Id_Error.setErrorEnabled(false);

                }else{
                    F_User_Id_Error.setErrorEnabled(true);
                    F_User_Id_Error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        F_Cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        F_Send_Mail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                singleTonProcess.show(ForgotActivity.this);
                if(validationForgot()) {
                    apiInterface.getForgotFieldDetails(F_User_Id.getText().toString().trim()).enqueue(new Callback<ForgotResponseData>() {
                        @Override
                        public void onResponse(Call<ForgotResponseData> call, Response<ForgotResponseData> response) {
                            switch (response.body().getIsSuccess()){
                                case "true":
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ForgotActivity.this);
                                    LayoutInflater factory = ForgotActivity.this.getLayoutInflater();
                                    View view1  = factory.inflate(R.layout.alert_dialog, null);
                                    AlertMessage = (TextView)view1.findViewById(R.id.alertMessage);
                                    AlertPositive =(Button)view1.findViewById(R.id.alertPositive);
                                    AlertMessage.setText("Réinitialiser le lien du mot de passe, envoyer avec succès à l’email");
                                    AlertPositive.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    dialogBuilder.setView(view1);
                                    AlertDialog alertDialog = dialogBuilder.create();
                                    alertDialog.show();
                                    singleTonProcess.dismiss();
                                    break;
//                                case "500":
//                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
//                                    break;
                                default:
                                    singleTonProcess.dismiss();
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                        @Override
                        public void onFailure(Call<ForgotResponseData> call, Throwable t) {
                            singleTonProcess.dismiss();

                        }
                    });
                }
            }
        });


    }

    public Boolean validationForgot(){
        if(F_User_Id.getText().toString().trim().matches(emailPattern) && F_User_Id.getText().toString().trim().length()>0){
            F_User_Id_Error.setErrorEnabled(false);
            return true;
        }else{
            F_User_Id_Error.setErrorEnabled(true);
            F_User_Id_Error.setError(getResources().getString(R.string.user_error));
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

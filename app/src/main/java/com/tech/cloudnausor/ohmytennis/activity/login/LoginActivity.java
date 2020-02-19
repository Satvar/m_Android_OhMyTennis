package com.tech.cloudnausor.ohmytennis.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.NewpaymentStripe;
import com.tech.cloudnausor.ohmytennis.activity.NewpaymentStripe;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.homepage.HomeActivity;
import com.tech.cloudnausor.ohmytennis.activity.homepage.NewHomePage;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseActivity;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.activity.register.RegisterActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennis.dto.ProfileEditDTO;
import com.tech.cloudnausor.ohmytennis.model.LoginModel;
import com.tech.cloudnausor.ohmytennis.model.postalCodeList;
import com.tech.cloudnausor.ohmytennis.response.LoginResponseData;
import com.tech.cloudnausor.ohmytennis.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.CustomEditText;
import com.tech.cloudnausor.ohmytennis.utils.DrawableClickListener;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    TextView ForgotId, Goreigister;
    TextInputLayout L_User_Pass_Error, L_User_ID_Error;
    SingleTonProcess singleTonProcess;
    Button L_Button;
    TextInputEditText L_User_ID;
    String password_status = "show";
    CustomEditText L_User_Pass;
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    LinearLayout content;
    LoginModel loginModel;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
        singleTonProcess = singleTonProcess.getInstance();
        content = (LinearLayout) findViewById(R.id.content_shake);
        ForgotId = (TextView) findViewById(R.id.forgotid);
        Goreigister = (TextView) findViewById(R.id.goregisterid);
        L_Button = (Button) findViewById(R.id.l_button);
        L_User_ID = (TextInputEditText) findViewById(R.id.l_user_id);
        L_User_Pass = (CustomEditText) findViewById(R.id.l_user_pass);
        L_User_ID_Error = (TextInputLayout) findViewById(R.id.l_user_id_error);
        L_User_Pass_Error = (TextInputLayout) findViewById(R.id.l_user_pass_error);
        L_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());
        
        if(!session.checkLogin()){

            Intent intent22 = getIntent();
            String action = intent22.getAction();
            Uri data = intent22.getData();
//            http://172.107.175.10:4001/login?id=Y2FsbWFuQHlvcG1haWwuY29t

            if(data != null) {
                String uriString = data.toString();
                if (uriString.startsWith("http://172.107.175.10:4001/login?id=")) {
                    String stt = uriString.replace("http://172.107.175.10:4001/login?id=", "");

                    byte[] datazz = Base64.decode(stt, Base64.DEFAULT);
                    String text = new String(datazz, StandardCharsets.UTF_8);

                    System.out.println("intent.getData-->" + text);

                    apiRequest.userVerification(text).enqueue(new Callback<DeleteClubDTO>() {
                        @Override
                        public void onResponse(Call<DeleteClubDTO> call, Response<DeleteClubDTO> response) {
                            if(response.body() != null){
                                if(response.body().getIsSuccess().equals("true")){
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteClubDTO> call, Throwable t) {

                        }
                    });
                }else  if(uriString.startsWith("http://172.107.175.10:4001/setnewpassword?hash=")){
                    Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                    String stt = uriString.replace("http://172.107.175.10:4001/setnewpassword?hash=","");
                    intent.putExtra("hashkey", stt);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

            ForgotId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
            });

            Goreigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            });

            L_User_ID.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(Objects.requireNonNull(L_User_ID.getText()).toString().trim().matches(emailPattern) && L_User_ID.getText().toString().trim().length()>0){
                        L_User_ID_Error.setErrorEnabled(false);

                    }else{
                        L_User_ID_Error.setErrorEnabled(true);
                        L_User_ID_Error.setError(getResources().getString(R.string.user_error));
                    }
                }
            });
            L_User_ID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(Objects.requireNonNull(L_User_ID.getText()).toString().trim().matches(emailPattern) && L_User_ID.getText().toString().trim().length()>0){
                        L_User_ID_Error.setErrorEnabled(false);

                    }else{
                        L_User_ID_Error.setErrorEnabled(true);
                        L_User_ID_Error.setError(getResources().getString(R.string.user_error));
                    }
                }
            });
            L_User_Pass.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if( L_User_Pass.getText().toString().trim().length()>0){
                        L_User_Pass_Error.setErrorEnabled(false);

                    }else{
                        L_User_Pass_Error.setErrorEnabled(true);
                        L_User_Pass_Error.setError(getResources().getString(R.string.pass_error));
                    }
                }
            });
            L_User_Pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if( L_User_Pass.getText().toString().trim().length()>0){
                        L_User_Pass_Error.setErrorEnabled(false);

                    }else{
                        L_User_Pass_Error.setErrorEnabled(true);
                        L_User_Pass_Error.setError(getResources().getString(R.string.pass_error));
                    }
                }
            });
            L_User_Pass.setDrawableClickListener(new DrawableClickListener() {
                public void onClick(DrawablePosition target) {
                    if (target == DrawablePosition.RIGHT) {//Do something here
                        if (password_status.equals("show")) {
                            password_status = "hide";
                            L_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT);
                            L_User_Pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off_black_24dp, 0);
                        } else if (password_status.equals("hide")) {
                            password_status = "show";
                            L_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            L_User_Pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic__eye_black_24dp, 0);
                        }
                    }
                }
            });
            L_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(LoginActivity.this, IndividualCourseHomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                Intent intent = new Intent(LoginActivity.this, IndividualCourseHomeActivity.class);
////              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                content.startAnimation(shake);

                if(validation()) {

                    singleTonProcess.show(LoginActivity.this);
                    apiRequest.getLoginFieldDetails(L_User_ID.getText().toString().trim(), L_User_Pass.getText().toString().trim()).enqueue(new Callback<LoginResponseData>() {
                        @Override
                        public void onResponse(retrofit2.Call<LoginResponseData> call, Response<LoginResponseData> response) {
                            if(response.body() != null) {
                                System.out.println(" login response body ---> "+ new Gson().toJson(response.body()));
                                if ("true".equals(response.body().getIsSuccess())) {
                                    singleTonProcess.dismiss();
                                    if (response.body().getMessage().equals("Connectez- vous avec succès")) {
                                            loginModel = response.body().getData();
                                        if (loginModel.getRoleId().equals("1")) {
//                                            if (loginModel.getRoleId().equals("1")) {
//                                                if (loginVerification()) {
                                                    editor.putString("KEY_User_ID", loginModel.getId());
                                                    editor.putString("Email", loginModel.getEmail());
                                                    editor.putBoolean("IsUserLogIn", true);
                                                    editor.putString("location",loginModel.getPostalCode());
                                                    editor.commit();




//                                                    Toast.makeText(getApplicationContext(), "true " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                                                    Intent intent = new Intent(LoginActivity.this, NewHomePage.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);

                                                } else {
                                                    singleTonProcess.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Entrez le bon ID utilisateur", Toast.LENGTH_LONG).show();
                                                }
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                                            }
                                        }

//                                    case "201":
//                                        editor.putString("KEY_User_ID", response.body().getCoachlist().get(0).getUser_ID());
//                                        editor.putString("Email",response.body().getCoachlist().get(0).getUser_ID());
//                                        editor.putBoolean("IsUserLogIn",true);
//                                        editor.commit();
//                                        Intent intent = new Intent(LoginActivity.this, IndividualCourseHomeActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                        break;
//                                    case "500":
//                                        content.startAnimation(shake);
//                                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                                        break;
//                                    case "501":
//                                                        content.startAnimation(shake);
//                                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                                        break;
//                                    case "502":
//                                                        content.startAnimation(shake);
//                                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                                        break;

                                } else {
                                    singleTonProcess.dismiss();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponseData> call, Throwable t) {
                            singleTonProcess.dismiss();
                            Log.e("e_getLoginFieldDetails", t.toString());
                        }
                    });
                }
            }
        });

        }else {
            Intent intent22 = getIntent();
            String action = intent22.getAction();
            Uri data = intent22.getData();
            System.out.println("intent.getData-->" + data);
            if(data != null){
            String uriString = data.toString();
                System.out.println("intent.uriString-->" + uriString);
            if(uriString.startsWith("http://172.107.175.10:4001/?bookid=")){
                Intent intent = new Intent(LoginActivity.this, NewpaymentStripe.class);
                String stt = uriString.replace("http://172.107.175.10:4001/?bookid=","");
                intent.putExtra("bookvalue", stt);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else if(uriString.startsWith("http://localhost:4001/?bookid=")){
                Intent intent = new Intent(LoginActivity.this, NewpaymentStripe.class);
                String stt = uriString.replace("http://localhost:4001/?bookid=","");
                intent.putExtra("bookvalue", stt);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
            Intent intent = new Intent(LoginActivity.this, NewHomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            }}else {
                Intent intent = new Intent(LoginActivity.this, NewHomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Boolean validation() {
        if (L_User_ID.getText().toString().trim().matches(emailPattern) && L_User_ID.getText().toString().trim().length() > 0) {
            if (L_User_Pass.getText().toString().trim().length() != 0) {
                L_User_Pass_Error.setErrorEnabled(false);
                return true;
            } else {
                L_User_ID_Error.setErrorEnabled(true);
                L_User_Pass_Error.setError(getResources().getString(R.string.pass_error));
                return false;
            }
        } else {
            L_User_ID_Error.setErrorEnabled(true);
            L_User_ID_Error.setError(getResources().getString(R.string.user_error));
            return false;
        }
      }
      public  Boolean loginVerification(){
        if(loginModel.getIsEmailConfirmed().equals("1")){
            if(loginModel.getIsActive().equals("1")){
               System.out.println("check-->"+"1");
                return true;
            }else {
                System.out.println("check-->"+"2");
                return false;
            }
        }else {
            System.out.println("check-->"+"3");
            return false;
        }
      }
}

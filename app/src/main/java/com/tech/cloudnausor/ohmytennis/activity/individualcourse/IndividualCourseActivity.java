package com.tech.cloudnausor.ohmytennis.activity.individualcourse;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Short3;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.homepage.HomeActivity;
import com.tech.cloudnausor.ohmytennis.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennis.activity.myaccount.MyAccountPreview;
import com.tech.cloudnausor.ohmytennis.adapter.IndividualCourseAdapter;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.model.AllCoachDetails;
import com.tech.cloudnausor.ohmytennis.response.AllCoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.HidingScrollListener;
import com.tech.cloudnausor.ohmytennis.utils.MyAutoCompleteTextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IndividualCourseActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    IndividualCourseAdapter individualCourseAdapter;
    ArrayList<AllCoachDetails> allCoachDetailsArrayList = new ArrayList<AllCoachDetails>();
    ArrayList<String> ICname = new ArrayList<String>();
    ArrayList<String> R_Frist_Name_Array = new ArrayList<String>();
    ArrayList<String> R_Second_Name_Array = new ArrayList<String>();
    ArrayList<String> R_Price_hr_Array = new ArrayList<String>();
    ArrayList<String> R_Price_ten_Array = new ArrayList<String>();
    ArrayList<String> R_Desc_Array = new ArrayList<String>();


    MyAutoCompleteTextView autocomplete;
    EditText User_coach_search;
    MenuItem itema;
    ViewGroup viewGroup;
    View dialogView;
    ArrayAdapter<String> adapter;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
    LinearLayout headerTool;
    TextView filterIndivi;
    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"};
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_individual_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        filterIndivi =(TextView)findViewById(R.id.filterindividual);
        User_coach_search =(EditText)findViewById(R.id.user_coach_search) ;
        headerTool =(LinearLayout)findViewById(R.id.headertool);
        recyclerView =(RecyclerView)findViewById(R.id.indicidaulcoachrecyl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Bala","","","","","","",""
//        ,"","","","","","","","","","","","",""
//        ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Bala","","","","","","",""
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        individualCourseAdapter = new IndividualCourseAdapter(IndividualCourseActivity.this,getApplicationContext(),allCoachDetailsArrayList);
//        individualCourseAdapter = new IndividualCourseAdapter(getApplicationContext(),R_Frist_Name_Array,R_Second_Name_Array,R_Price_hr_Array,R_Price_ten_Array,R_Desc_Array);
        recyclerView.setAdapter(individualCourseAdapter);
        individualCourseAdapter.notifyDataSetChanged();
//        apiRequest.getGetallcoaches().enqueue(new Callback<AllCoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<AllCoachDetailsResponseData> call, Response<AllCoachDetailsResponseData> response) {
//                Log.e("bala hi",new Gson().toJson(response.body()).toString());
//                if(response.body() != null){
//                    if(response.body().getIsSuccess().equals("200")){
//                        Log.e("bala hi",new Gson().toJson(response.body().getData().toString()));
//                        allCoachDetailsArrayList.addAll(response.body().getData());
//                        individualCourseAdapter= new IndividualCourseAdapter(IndividualCourseActivity.this,getApplicationContext(),allCoachDetailsArrayList);
//                        recyclerView.setAdapter(individualCourseAdapter);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AllCoachDetailsResponseData> call, Throwable t) {
//
//            }
//        });
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);

        User_coach_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                individualCourseAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
//                headerTool.setVisibility(View.GONE);
         }

            @Override
            public void onShow() {
//                headerTool.setVisibility(View.VISIBLE);
            }
        });

//        recyclerView.OnScrollListener
        adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, arr);

        filterIndivi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                Rect displayRectangle = new Rect();
                Window window = IndividualCourseActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(IndividualCourseActivity.this,R.style.DialogTheme);
                 viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.filter_individual_course, viewGroup, false);
                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//                autocomplete =(MyAutoCompleteTextView)dialogView.findViewById(R.id.autoCompleteTextView1);
//                autocomplete.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        autocomplete.showDropDown();
//                        return false;
//                    }
//                });
                autocomplete.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        ((MyAutoCompleteTextView)view).showDropDown();
                        return false;
                    }
                });
                autocomplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        MyAutoCompleteTextView v = ((MyAutoCompleteTextView)view);
                        if(b && v.getText().length() == 0) {
                            v.showDropDown();
                        }
                    }
                });

//                autocomplete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(final View arg0) {
//                        autocomplete.setAdapter(adapter);
//                        autocomplete.showDropDown();
//                    }
//                });


//                autocomplete.setAdapter(adapter);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        itema = menu.findItem(R.id.action_notifications);
        itema.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            System.out.println("dgssfdxhgbdfh");
            return true;
        }else if(id == R.id.action_notifications1){
            itema.setVisible(false);
            System.out.println("dgssfdxhgbdfh00000");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(IndividualCourseActivity.this, MyAccountPreview.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(IndividualCourseActivity.this, IndividualCourseActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_tools1) {

        } else if (id == R.id.nav_tools2) {
            Intent intent = new Intent(IndividualCourseActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            editor.putBoolean("IsUserLoggedIn",false);
            editor.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

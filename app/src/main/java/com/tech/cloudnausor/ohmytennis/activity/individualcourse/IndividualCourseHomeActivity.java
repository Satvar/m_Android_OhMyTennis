package com.tech.cloudnausor.ohmytennis.activity.individualcourse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.listpartner.ListPartnerActivity;
import com.tech.cloudnausor.ohmytennis.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennis.activity.myaccount.MyAccountPreview;
import com.tech.cloudnausor.ohmytennis.activity.reservation.UserCoachReservation;
import com.tech.cloudnausor.ohmytennis.activity.sparringpartner.SparringPartnerActivity;
import com.tech.cloudnausor.ohmytennis.adapter.IndividualCourseAdapter;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.calenderactivity.BasicActivity;
import com.tech.cloudnausor.ohmytennis.forgot.ChangePasswordActivity;
import com.tech.cloudnausor.ohmytennis.model.LoacationBaseCoachDataDetails;
import com.tech.cloudnausor.ohmytennis.model.LocationBaseCoachData;
import com.tech.cloudnausor.ohmytennis.response.AllCoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.HidingScrollListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualCourseHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    IndividualCourseAdapter individualCourseAdapter;
    ArrayList<LoacationBaseCoachDataDetails> allCoachDetailsArrayList = new ArrayList<LoacationBaseCoachDataDetails>();
    EditText location_edit_post;

    AutoCompleteTextView autocomplete;
    EditText User_coach_search;
    MenuItem itema;
    ViewGroup viewGroup;
    View dialogView;
    String userid_ = null;
    String location =null;
    ArrayAdapter<String> adapter;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
    LinearLayout headerTool;
    TextView filterIndivi;
    String[] arr = { "Cours individual","Cours collectif",
            "Cours collectif ondemand","Cours collectif club",
            "Team Building","Stage","Animations"};
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        filterIndivi =(TextView)findViewById(R.id.filterindividual);
        User_coach_search =(EditText)findViewById(R.id.user_coach_search) ;
        headerTool =(LinearLayout)findViewById(R.id.headertool);
        recyclerView =(RecyclerView)findViewById(R.id.indicidaulcoachrecyl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        location_edit_post =(EditText)findViewById(R.id.location_base);

        if(sharedPreferences.contains("location")){
            location = sharedPreferences.getString("location","");
            System.out.println(" location--> " + location);
        }

        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");

        }
        location_edit_post.setText(location);

        location_edit_post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String code = charSequence.toString();
                if(code.length() == 5){
                    getcoach(code);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        individualCourseAdapter = new IndividualCourseAdapter(IndividualCourseHomeActivity.this,getApplicationContext(),allCoachDetailsArrayList);
//        individualCourseAdapter = new IndividualCourseAdapter(getApplicationContext(),R_Frist_Name_Array,R_Second_Name_Array,R_Price_hr_Array,R_Price_ten_Array,R_Desc_Array);
        recyclerView.setAdapter(individualCourseAdapter);
        getcoach(location);
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
                Window window = IndividualCourseHomeActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(IndividualCourseHomeActivity.this,R.style.DialogTheme);
                viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.filter_individual_course, viewGroup, false);
                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                autocomplete =(AutoCompleteTextView)dialogView.findViewById(R.id.autoCompleteTextView1);
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
                        ((AutoCompleteTextView)view).showDropDown();
                        return false;
                    }
                });
                autocomplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        AutoCompleteTextView v = ((AutoCompleteTextView)view);
                        if(b && v.getText().length() == 0) {
                            v.showDropDown();
                        }
                    }
                });

                autocomplete.setAdapter(adapter);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_my_account) {
            Intent intent = new Intent(IndividualCourseHomeActivity.this, MyAccountPreview.class);
            startActivity(intent);
        } else if (id == R.id.nav_calenderpage) {
            Intent i = new Intent(IndividualCourseHomeActivity.this, BasicActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_resevation) {
            Intent i = new Intent(IndividualCourseHomeActivity.this, UserCoachReservation.class);
            startActivity(i);

        } else if (id == R.id.nav_command) {
            Intent i = new Intent(IndividualCourseHomeActivity.this, CommandListActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_partner) {
            Intent i = new Intent(IndividualCourseHomeActivity.this, ListPartnerActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_sparring) {
            Intent i = new Intent(IndividualCourseHomeActivity.this, SparringPartnerActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_change_pass) {
            Intent intent = new Intent(IndividualCourseHomeActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools2) {
            editor.putBoolean("IsUserLogIn",false).apply();
            editor.commit();
            Intent i = new Intent(IndividualCourseHomeActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getcoach(String postalcode){

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        apiRequest.getGetallcoaches(postalcode).enqueue(new Callback<AllCoachDetailsResponseData>() {
            @Override
            public void onResponse(Call<AllCoachDetailsResponseData> call, Response<AllCoachDetailsResponseData> response) {
                Log.e("bala hi",new Gson().toJson(response.body()).toString());
                if(response.body() != null){
                    if(response.body().getIsSuccess().equals("true")){
                        LocationBaseCoachData locationBaseCoachData = response.body().getData();
                        allCoachDetailsArrayList.clear();
                        allCoachDetailsArrayList.addAll(locationBaseCoachData.getLoacationBaseCoachDataDetailsArrayList());
                        individualCourseAdapter= new IndividualCourseAdapter(IndividualCourseHomeActivity.this,getApplicationContext(),allCoachDetailsArrayList);
                        recyclerView.setAdapter(individualCourseAdapter);
                        individualCourseAdapter.notifyDataSetChanged();
                    }
                }else {

                }

            }

            @Override
            public void onFailure(Call<AllCoachDetailsResponseData> call, Throwable t) {
                Log.e("bala hi",t.toString());
            }
        });

    }
}


//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Bala","","","","","","",getResources().getString(R.string.services_1)
//                ,"","","","","","","Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco labori nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse est laborum.",
//                "","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Guna","","","","","","",getResources().getString(R.string.services_2)
//                ,"","","","","","","Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco labori nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse est laborum.",
//                "","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Chandru","","","","","","",getResources().getString(R.string.services_3)
//                ,"","","","","","","Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco labori nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse est laborum.","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","sekran","","","","","","",getResources().getString(R.string.services_4)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Sekarguna","","","","","","",getResources().getString(R.string.services_1)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Chandrubala","","","","","","",getResources().getString(R.string.services_7)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Durai","","","","","","",getResources().getString(R.string.services_6)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Murukan","","","","","","",getResources().getString(R.string.services_5)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Sathish","","","","","","",getResources().getString(R.string.services_7)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Sathish T","","","","","","",getResources().getString(R.string.services_3)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Bala","","","","","","",getResources().getString(R.string.services_1)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Guna","","","","","","",getResources().getString(R.string.services_2)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Chandru","","","","","","",getResources().getString(R.string.services_3)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","sekran","","","","","","",getResources().getString(R.string.services_4)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Sekarguna","","","","","","",getResources().getString(R.string.services_1)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Chandrubala","","","","","","",getResources().getString(R.string.services_7)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Durai","","","","","","",getResources().getString(R.string.services_6)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Murukan","","","","","","",getResources().getString(R.string.services_5)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Sathish","","","","","","",getResources().getString(R.string.services_7)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
//        allCoachDetailsArrayList.add(new AllCoachDetails("","","Sathish T","","","","","","",getResources().getString(R.string.services_3)
//                ,"","","","","","","","","","","","",""
//                ,"","","","",""));
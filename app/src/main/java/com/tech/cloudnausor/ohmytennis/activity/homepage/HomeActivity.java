package com.tech.cloudnausor.ohmytennis.activity.homepage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.tech.cloudnausor.ohmytennis.MainActivity;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseActivity;
import com.tech.cloudnausor.ohmytennis.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennis.activity.myaccount.MyAccountPreview;
import com.tech.cloudnausor.ohmytennis.adapter.CoachHomeProfileAdapter;
import com.tech.cloudnausor.ohmytennis.adapter.HomeBannerAdapter;
import com.tech.cloudnausor.ohmytennis.adapter.HomeEventBannerAdapter;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.searchspinner.SearchableListDialog;
import com.tech.cloudnausor.ohmytennis.utils.searchspinner.SearchableSpinner;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    MenuItem itema;
    private int dotscount;
    private ImageView[] dots;
    HomeBannerAdapter homeBannerAdapter;
    ArrayList<Drawable> arrayList=new ArrayList<Drawable>(),arrayEventImage=new ArrayList<Drawable>();
    ArrayList<String> arrayUrlEventImage=new ArrayList<String>();
    ArrayList<String> arrayList1=new ArrayList<String>(),arrayEventAbout=new ArrayList<String>();
    ImageButton left,right;
    AppCompatImageView Coachphoto;
    TextView CoachName;
    RecyclerView eventBannerRecyclerView,profileimage ;
    HomeEventBannerAdapter homeEventBannerAdapter;
    CoachHomeProfileAdapter coachHomeProfileAdapter;
    int i = 0;
    String url = ApiClient.BASE_URL;
    ArrayList<Drawable> profilet=new ArrayList<Drawable>();
    ArrayList<String> namet=new ArrayList<String>();
    int a=0;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date1;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        Spinner spinner1 = (Spinner) findViewById(R.id.searchableSpinner);
        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.mobile_manufacturers, android.R.layout.simple_spinner_item);
        // Specify layout to be used when list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);


        left =(ImageButton)findViewById(R.id.moveleft) ;
        right =(ImageButton)findViewById(R.id.moveright) ;
        Coachphoto =(AppCompatImageView)findViewById(R.id.coachphoto);
        CoachName =(TextView)findViewById(R.id.coachname);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout)findViewById(R.id.SliderDots);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        arrayList.add(getResources().getDrawable(R.drawable.bannerone));
        arrayList.add(getResources().getDrawable(R.drawable.roundinst));
        arrayList1.add("bala");
        arrayList1.add("guna");


        arrayUrlEventImage.add(url+"/views/images/event1.png");
        arrayUrlEventImage.add(url+"/views/images/event2.png");
        arrayUrlEventImage.add(url+"/views/images/event3.png");
        arrayUrlEventImage.add(url+"/views/images/event1.png");
        arrayUrlEventImage.add(url+"/views/images/event2.png");


        profilet.add(getResources().getDrawable(R.drawable.coach_playerimg1));
        profilet.add(getResources().getDrawable(R.drawable.coach_playerimg2));
        profilet.add(getResources().getDrawable(R.drawable.coach_playerimg3));
        profilet.add(getResources().getDrawable(R.drawable.coach_playerimg4));
        namet.add("bala");
        namet.add("bala");
        namet.add("bala");
        namet.add("bala");
        a=4;

        profileimage = (RecyclerView)findViewById(R.id.profileimage);
        profileimage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        homeEventBannerAdapter = new HomeEventBannerAdapter(getApplicationContext(),arrayEventImage,arrayEventAbout);
        coachHomeProfileAdapter = new CoachHomeProfileAdapter(getApplicationContext(),profilet,namet,a);
        profileimage.setAdapter(coachHomeProfileAdapter);

//        arrayEventImage.add(getResources().getDrawable(R.drawable.bannerone));
//        arrayEventImage.add(getResources().getDrawable(R.drawable.bannertwo));
//        arrayEventImage.add(getResources().getDrawable(R.drawable.bannerthree));
//        arrayEventImage.add(getResources().getDrawable(R.drawable.bannerone));
//        arrayEventImage.add(getResources().getDrawable(R.drawable.bannertwo));

        arrayEventAbout.add("bala");
        arrayEventAbout.add("durai");
        arrayEventAbout.add("thiru");
        arrayEventAbout.add("jeeva");
        arrayEventAbout.add("nisha");

        String[] data = {"Cours"};
        String[] data1 = {"Session"};
        String[] data2 = {"Rayon"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_text, data);
        adapter.setDropDownViewResource(R.layout.spinner_item_list);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_text, data1);
        adapter.setDropDownViewResource(R.layout.spinner_item_list);
        ArrayAdapter adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_text, data2);
        adapter.setDropDownViewResource(R.layout.spinner_item_list);


        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        Spinner spinner2 = findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        Spinner spinner3 = findViewById(R.id.spinner4);
        spinner3.setAdapter(adapter3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeActivity.this,parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Coachphoto.setImageDrawable(arrayList.get(0));
        CoachName.setText(arrayList1.get(0));

        homeBannerAdapter = new HomeBannerAdapter(this);
        viewPager.setAdapter(homeBannerAdapter);

        eventBannerRecyclerView = (RecyclerView)findViewById(R.id.eventbannerrecycler);
        eventBannerRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

//        homeEventBannerAdapter = new HomeEventBannerAdapter(getApplicationContext(),arrayEventImage,arrayEventAbout);

        homeEventBannerAdapter = new HomeEventBannerAdapter(getApplicationContext(),arrayUrlEventImage,arrayEventAbout);


        eventBannerRecyclerView.setAdapter(homeEventBannerAdapter);



        edittext= (EditText) findViewById(R.id.spinner3);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(HomeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(i!=0 ) {
                      i--;
                   if (i >= 0) {
                       Coachphoto.setImageDrawable(arrayList.get(i));
                       CoachName.setText(arrayList1.get(i));
                   }
               }
            }

        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(i!=arrayList.size()-1){ i++;
                if(i<=arrayList.size()-1){
                    Coachphoto.setImageDrawable(arrayList.get(i));
                    CoachName.setText(arrayList1.get(i));
                }else{

                }

            }}
        });


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        dotscount = homeBannerAdapter.getCount();
        homebannerdots();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    public void homebannerdots(){
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(HomeActivity.this, MyAccountPreview.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(HomeActivity.this, IndividualCourseActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_tools1) {

        } else if (id == R.id.nav_tools2) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            editor.putBoolean("IsUserLoggedIn",false);
            editor.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        edittext.setFocusable(false);
        hideKeyboard(this);
        switch (selectedItem) {
            case "Select one Item":
                break;
            case "Samsung":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Foxconn":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Apple":
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                break;
            case "Oppo":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Nokia":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "LYF":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Xiaomi":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Huawei":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Asus":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
            case "Lenovo":
                Toast.makeText(getApplicationContext(), selectedItem , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

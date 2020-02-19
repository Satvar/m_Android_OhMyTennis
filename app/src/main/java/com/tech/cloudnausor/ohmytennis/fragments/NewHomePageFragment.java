package com.tech.cloudnausor.ohmytennis.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.homepage.NewHomePage;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.adapter.IndividualCourseAdapter;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.model.LoacationBaseCoachDataDetails;
import com.tech.cloudnausor.ohmytennis.model.LocationBaseCoachData;
import com.tech.cloudnausor.ohmytennis.response.AllCoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.HidingScrollListener;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Constant;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Menus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHomePageFragment extends Fragment {

    private boolean mSearchCheck;
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
    View homepageview;
    ArrayAdapter<String> adapter;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
    LinearLayout headerTool;
    TextView filterIndivi;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String[] arr = { "Cours individual","Cours collectif",
            "Cours collectif ondemand","Cours collectif club",
            "Team Building","Stage","Animations"};
    SingleTonProcess singleTonProcess;

    public  NewHomePageFragment newInstance(String text) {
        NewHomePageFragment mFragment = new NewHomePageFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homepageview = inflater.inflate(R.layout.content_individual_course_home, container, false);
        sharedPreferences = getContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getContext());
        DrawerLayout drawer = homepageview.findViewById(R.id.drawer_layout);
        NavigationView navigationView = homepageview.findViewById(R.id.nav_view);
        singleTonProcess = singleTonProcess.getInstance();


        filterIndivi =(TextView)homepageview.findViewById(R.id.filterindividual);
        User_coach_search =(EditText)homepageview.findViewById(R.id.user_coach_search) ;
        headerTool =(LinearLayout)homepageview.findViewById(R.id.headertool);
        recyclerView =(RecyclerView)homepageview.findViewById(R.id.indicidaulcoachrecyl);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        location_edit_post =(EditText)homepageview.findViewById(R.id.location_base);

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

//        individualCourseAdapter = new IndividualCourseAdapter(getActivity(),getContext(),allCoachDetailsArrayList);
//        individualCourseAdapter = new IndividualCourseAdapter(getApplicationContext(),R_Frist_Name_Array,R_Second_Name_Array,R_Price_hr_Array,R_Price_ten_Array,R_Desc_Array);
//        recyclerView.setAdapter(individualCourseAdapter);
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
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, arr);

        filterIndivi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                Rect displayRectangle = new Rect();
                Window window = getActivity().getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.DialogTheme);
                viewGroup = homepageview.findViewById(android.R.id.content);
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
        return homepageview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) (menu.findItem(Menus.SEARCH).getActionView());
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(OnQuerySearchView);
        menu.findItem(Menus.ADD).setVisible(true);
        menu.findItem(Menus.EDIT).setVisible(false);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(false);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:
//                Rect displayRectangle = new Rect();
//                Window window = getActivity().getWindow();
//                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.DialogTheme);
//                viewGroup = getView().findViewById(android.R.id.content);
//                dialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_avaibility_calender, viewGroup, false);
//                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
//                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//
//                builder.setView(dialogView);
//                final AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                break;


            case Menus.UPDATE:
                break;

            case Menus.SEARCH:
                mSearchCheck = true;
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onQueryTextChange(String arg0) {
            // TODO Auto-generated method stub
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };


    
    public void getcoach(String postalcode){
        ((NewHomePage)getContext()).showprocess();

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
                        individualCourseAdapter= new IndividualCourseAdapter(((NewHomePage)getActivity()),((NewHomePage)getContext()),allCoachDetailsArrayList);
                        recyclerView.setAdapter(individualCourseAdapter);
                        ((NewHomePage)getContext()).dismissprocess();
                        individualCourseAdapter.notifyDataSetChanged();

                    }else {
                         ((NewHomePage)getContext()).dismissprocess();
                    }
                }else {
                    ((NewHomePage)getContext()).dismissprocess();
                }

            }

            @Override
            public void onFailure(Call<AllCoachDetailsResponseData> call, Throwable t) {
                 ((NewHomePage)getContext()).dismissprocess();
                Log.e("bala hi",t.toString());
            }
        });

    }

}

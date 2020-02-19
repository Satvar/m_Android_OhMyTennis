package com.tech.cloudnausor.ohmytennis.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.homepage.NewHomePage;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.adapter.IndividualCourseAdapter;
import com.tech.cloudnausor.ohmytennis.adapter.OhMyEventAdapter;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;
import com.tech.cloudnausor.ohmytennis.dto.OhMyEventsDTO;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;
import com.tech.cloudnausor.ohmytennis.model.LoacationBaseCoachDataDetails;
import com.tech.cloudnausor.ohmytennis.model.LocationBaseCoachData;
import com.tech.cloudnausor.ohmytennis.response.AllCoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.HidingScrollListener;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OhMyEventSevice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "param2";
    private boolean mSearchCheck;
    Menu menumain;
    String coachid_ = null;
    String uPassword =null;
    ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    SingleTonProcess singleTonProcess;


    ArrayList<EventDTO.AnimationCourse> animationCourseArrayList = new  ArrayList<EventDTO.AnimationCourse>();
    ArrayList<EventDTO.AnimationCourse> stageCourseArrayList = new ArrayList<EventDTO.AnimationCourse>();
    ArrayList<EventDTO.AnimationCourse> tournamentCourseArrayList = new ArrayList<EventDTO.AnimationCourse>();

    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewEvent;
    View eventview;
    OhMyEventAdapter ohMyEventAdapter;
    ArrayList<EventDTO.AnimationCourse> teamBindingCourseArrayList = new  ArrayList<EventDTO.AnimationCourse> ();
    String typeevent;
    ArrayList<OhMyEventsDTO.OhMyEventData> ohMyEventDataArrayList = new ArrayList<OhMyEventsDTO.OhMyEventData>();


    private OnFragmentInteractionListener mListener;

//    public OhMyEventSevice() {
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CoachUserReservationFragment.
     */
    // TODO: Rename and change types and number of parameters

    public  OhMyEventSevice newInstance(String text,String type) {
        OhMyEventSevice mFragment = new OhMyEventSevice();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mBundle.putString("type",type);
//        typeevent = type;
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            sharedPreferences = getContext().getSharedPreferences("RegUser", 0);
            editor = sharedPreferences.edit();
            session = new SessionManagement(getContext());

            if (sharedPreferences.contains("KEY_Coach_ID"))
            {
                coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

            }
            if (sharedPreferences.contains("Email"))
            {
                uPassword = sharedPreferences.getString("Email", "");
            }

            mParam1 = getArguments().getString(ARG_PARAM1);
            System.out.println("mParam1--->"+ mParam1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventview = inflater.inflate(R.layout.fragment_oh_my_event_sevice, container, false);
        initview();
        return eventview;
    }


    public void initview(){
        recyclerViewEvent = (RecyclerView)eventview.findViewById(R.id.eventRecycleview);
        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1,teamBindingCourseArrayList);
        recyclerViewEvent.setAdapter(ohMyEventAdapter);
//        adddata();
        if(mParam1.equals("animation")){
            getAnimation();
        }else if(mParam1.equals("stage")){
            getStage();
        }else if(mParam1.equals("tournament")){
            getTournament();
        }else if(mParam1.equals("teambuilding")){
            getTeamBinding();
        }
    }

    //    public  void adddata(){
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("Guna","25 DEC","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("Bala","11 OCT","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("King","12 JAN","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("Won","15 AUG","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventAdapter.notifyDataSetChanged();
//    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void getAnimation()
    {
        ((NewHomePage)getContext()).showprocess();
        apiInterface.getallcourse("Animation").enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDTO> call, @NonNull Response<EventDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        animationCourseArrayList = response.body().getData().getCourse();
                        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1,teamBindingCourseArrayList);
                        recyclerViewEvent.setAdapter(ohMyEventAdapter);
                         ((NewHomePage)getContext()).dismissprocess();
                    }
                }else {
                     ((NewHomePage)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<EventDTO> call, Throwable t) {
                 ((NewHomePage)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });
    }

    public void getStage() {
        ((NewHomePage)getContext()).showprocess();
        apiInterface.getallcourse("Stage").enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDTO> call, @NonNull Response<EventDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        stageCourseArrayList = response.body().getData().getCourse();
                        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1,teamBindingCourseArrayList);
                        recyclerViewEvent.setAdapter(ohMyEventAdapter);
                         ((NewHomePage)getContext()).dismissprocess();
                    }
                }else {
                     ((NewHomePage)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<EventDTO> call, Throwable t) {
                 ((NewHomePage)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }



    public void getTournament()
    {
        ((NewHomePage)getContext()).showprocess();
        apiInterface.getallcourse("Tournament").enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDTO> call, @NonNull Response<EventDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        tournamentCourseArrayList = response.body().getData().getCourse();
                        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1,teamBindingCourseArrayList);
                        recyclerViewEvent.setAdapter(ohMyEventAdapter);
                         ((NewHomePage)getContext()).dismissprocess();
                    }
                }else {
                     ((NewHomePage)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<EventDTO> call, Throwable t) {
                 ((NewHomePage)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

    public void getTeamBinding()
    {
        ((NewHomePage)getContext()).showprocess();
        apiInterface.getallcourse("TeamBuilding").enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDTO> call, @NonNull Response<EventDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        teamBindingCourseArrayList = response.body().getData().getCourse();
                        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1,teamBindingCourseArrayList);
                        recyclerViewEvent.setAdapter(ohMyEventAdapter);
                         ((NewHomePage)getContext()).dismissprocess();
                    }
                }else {
                     ((NewHomePage)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<EventDTO> call, Throwable t) {
                 ((NewHomePage)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }




}



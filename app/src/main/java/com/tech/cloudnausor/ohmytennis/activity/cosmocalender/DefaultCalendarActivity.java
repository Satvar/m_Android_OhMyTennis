package com.tech.cloudnausor.ohmytennis.activity.cosmocalender;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.WeekDayCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.CurrentMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.NextMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.PreviousMonthCriteria;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteria;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.login.ForgotActivity;
import com.tech.cloudnausor.ohmytennis.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.OnDemandDTO;
import com.tech.cloudnausor.ohmytennis.model.BookArray;
import com.tech.cloudnausor.ohmytennis.model.DayValueShow;
import com.tech.cloudnausor.ohmytennis.model.GetIndiCoachDetailsModel;
import com.tech.cloudnausor.ohmytennis.model.IndiCourseData;
import com.tech.cloudnausor.ohmytennis.model.OndemandCourseModel;
import com.tech.cloudnausor.ohmytennis.model.RequestToBook;
import com.tech.cloudnausor.ohmytennis.model.SelectionTimeDetails;
import com.tech.cloudnausor.ohmytennis.model.gettimeslot.SetAvaibilityData;
import com.tech.cloudnausor.ohmytennis.model.gettimeslot.SetAvaibilityTime;
import com.tech.cloudnausor.ohmytennis.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennis.response.ResevertionTimeResponseData;
import com.tech.cloudnausor.ohmytennis.response.SetTimeSot;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DefaultCalendarActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private CalendarView calendarView;

    private List<BaseCriteria> threeMonthsCriteriaList;
    private WeekDayCriteria fridayCriteria;
    OndemandCourseModel onDeamndModel = new OndemandCourseModel();
    String dateSelected = "";
    String currentDated;
    ApiInterface apiInterface;
    NestedScrollView nestedScrollView;
    Button floatingActionButton;
    String coach_id = new String();
    String Service_Type = new String();
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    View dialogView;
    LinearLayout placelayout,pricelayout;
    LayoutInflater inflater;
    String timedatestring = "";
    TextView placeShow,TimeandDate,TypeofService,priceShow,coachname;
    Button avaibility_main_ok,avaibility_main_cancel;
    IndiCourseData indiCourseData = new IndiCourseData();
    GetIndiCoachDetailsModel getIndiCoachDetailsModel = new GetIndiCoachDetailsModel();
    Boolean secondtime = false;
    Boolean tenhourture = false;
    ArrayList<String> ondemadnameValue = new ArrayList<String>();
    int indi_course_cout = 0;
    int ondemand_course_cout = 0;

    int amount =0;
    int amountTen = 0;
//    String Pl_One = new String();
//    String Pl_two = new String();
//    String Pl_three = new String();
//    String Pl_four = new String();
//    String Pl_five = new String();


    private boolean fridayCriteriaEnabled;
    private boolean threeMonthsCriteriaEnabled;
    SingleSelectionManager singleSelectionManager;
    private MenuItem menuFridays;
    private MenuItem menuThreeMonth;
    ArrayList<String> timeShow = new ArrayList<String>();
    ArrayList<String> timeStatus = new ArrayList<String >();
    RecyclerView recyclerViewTime,recylerTimeSelection;
    TimeViewAdapter timeViewAdapter;
    SelectionTimeListAdapter selectionTimeListAdapter;
    SetAvaibilityTime setAvaibilityTime = new SetAvaibilityTime("","","");
    ArrayList<String> dateSever = new ArrayList<String>();
    ArrayList<ArrayList<String>> timeSever = new ArrayList<ArrayList<String>>();
    ArrayList<SelectionTimeDetails> selectionTimeDetails =  new ArrayList<SelectionTimeDetails>();
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_ = null;
    String location =null;
    ImageView backk;
    String frist_name = new String();
    String last_name = new String();
    ListView listView;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_default_calendar);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_calender));
//        if(getIntent().getExtras() != null){
//         b = getIntent().getExtras();
//        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        coach_id = getIntent().getStringExtra("Coach_id");
        Service_Type = getIntent().getStringExtra("ServiceType");
        frist_name = getIntent().getStringExtra("f_name_coach");
        last_name = getIntent().getStringExtra("l_name_coach");

//        assert b != null;
//        if(b.containsKey("pl_one")){
//            Pl_One = b.getString("pl_one");
//        }
//        if(b.containsKey("pl_two")){
//            Pl_two = b.getString("pl_two");
//        }
//        if(b.containsKey("pl_three")){
//            Pl_three = b.getString("pl_three");
//        }
//        if(b.containsKey("pl_four")){
//            Pl_four = b.getString("pl_four");
//        }
//        if(b.containsKey("pl_five")){
//            Pl_five = b.getString("pl_five");
//        }


        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        if(sharedPreferences.contains("location")){
            location = sharedPreferences.getString("location","");
            System.out.println("location --->"+location);
        }

        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");

        }

        System.out.println("userid_--->  "+userid_);
        System.out.println("coach_id_--->  "+coach_id);
        System.out.println("Service_Type--->  "+Service_Type);

//        apiInterface.getTimeslot("","").enqueue(new Callback<SetTimeSot>() {
//            @Override
//            public void onResponse(@NonNull Call<SetTimeSot> call, @NonNull Response<SetTimeSot> response) {
//                assert response.body() != null;
//                if(response.body().getIsSuccess().equals("true")){
//                    Toast.makeText(DefaultCalendarActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(DefaultCalendarActivity.this,response.body().getMessage()+"...",Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<SetTimeSot> call, Throwable t) {
//                System.out.println("---> "+ call +" "+ t);
//            }
//        });

        initViews();
        createCriterias();
//        Calendar calendar = Calendar.getInstance();
//        Set<Long> days = new TreeSet<>();
//        days.add(calendar.getTimeInMillis());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        currentDated= df.format(c);
        dateSelected = currentDated;
//        ConnectedDays connectedDays = new ConnectedDays(days, Color.parseColor("#ffffff"),Color.parseColor("#e75b00"));
        calendarView.setCalendarOrientation(0);
        calendarView.setCurrentDayTextColor(Color.parseColor("#0000ff"));
//        calendarView.addConnectedDays(connectedDays);
        singleSelectionManager = new SingleSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                System.out.println("calendarView.getSelectedDays()---> "+calendarView.getSelectedDays());
                Day dayrrr = calendarView.getSelectedDays().get(0);
                String dayt = dayrrr.toString();
                dayt = dayt.replace("Day{day=","");
                dayt = dayt.replace("}","");
                System.out.println("calendarView.getSelectedDays()---> "+dayt);
                SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT+05:30' yyyy");
                inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat apidateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null,date1 =null;
                try {
                    date = inputFormat.parse(dayt);
                    dateSelected = apidateFormat.format(date);
                    Date s = apidateFormat.parse(dateSelected);
                    Date c1 = apidateFormat.parse(currentDated);
                    if(c1.after(s)){
                        calendarView.clearSelections();
                        calendarView.setCurrentDayTextColor(Color.parseColor("#000000"));
                        Toast.makeText(DefaultCalendarActivity.this,"Veuillez sélectionner la prochaine date disponible",Toast.LENGTH_SHORT).show();
                        dateSelected = "";
                    }
                    String aa  = apidateFormat.format(date);
                    System.out.println("calendarView.getSelectcccedDays()---> "+aa);
                    selectiondategettime(dateSelected);
                    System.out.println("calendarView.getSelectedDays()---> "+dateSelected);
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("calendarView.getSelectedDays()---> "+e);
                }




            }
        });
        calendarView.setSelectionManager(singleSelectionManager);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        backk = (ImageView)findViewById(R.id.back);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        ((RadioGroup) findViewById(R.id.rg_orientation)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_selection_type)).setOnCheckedChangeListener(this);
        recyclerViewTime = (RecyclerView)findViewById(R.id.recylerTimeShow);
        recylerTimeSelection =(RecyclerView)findViewById(R.id.recylerTimeSelection);
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedview);
        floatingActionButton =(Button)findViewById(R.id.floating);
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (i1 > i3) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }


            }
        });

        initArray();
        if(Service_Type.equals("CoursIndividuel")) {
            Summerydetails();
        }
        if(Service_Type.equals("CoursCollectifOndemand")){
            Summerydetailsondemand();
        }

        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        dialogBuilder = new AlertDialog.Builder(this);
        inflater = LayoutInflater.from(getApplicationContext());
        dialogView = inflater.inflate(R.layout.bookingindiviconfirm, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        listView=(ListView)dialogView.findViewById(R.id.ondemandNameList);
        placeShow= (TextView)dialogView.findViewById(R.id.placeShow) ;
        TimeandDate= (TextView)dialogView.findViewById(R.id.TimeandDate) ;
        placelayout= (LinearLayout) dialogView.findViewById(R.id.placelayout) ;
        pricelayout= (LinearLayout) dialogView.findViewById(R.id.pricelayout) ;
        TypeofService= (TextView)dialogView.findViewById(R.id.TypeofService) ;
        priceShow= (TextView)dialogView.findViewById(R.id.priceShow) ;
        coachname= (TextView)dialogView.findViewById(R.id.coachname) ;
        avaibility_main_ok = (Button) dialogView.findViewById(R.id.avaibility_main_ok) ;
        avaibility_main_cancel = (Button) dialogView.findViewById(R.id.avaibility_main_cancel) ;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectionTimeDetails.size() !=0){
             int   AmountMultti = 0;
                    if(Service_Type.equals("CoursIndividuel")) {
//                        placeShow.setText(getIndiCoachDetailsModel.getLocation());
                        if(!tenhourture){
                            amount= Integer.parseInt(getIndiCoachDetailsModel.getPrice_min());
                        }else {
                            amountTen= Integer.parseInt(getIndiCoachDetailsModel.getPrice_max());
                        }
//                        amount= Integer.parseInt(getIndiCoachDetailsModel.getPrice_min());
//                        amount = 100;
                        pricelayout.setVisibility(View.VISIBLE);
                        placelayout.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                    }else if(Service_Type.equals("CoursCollectifOndemand")){


                        placeShow.setText(onDeamndModel.getLocation());
                        pricelayout.setVisibility(View.GONE);
                        placelayout.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);

                    }

                for(int d =0;d<selectionTimeDetails.size();d++){
                    String setdate = selectionTimeDetails.get(d).dateTitte;
                    if(d==0){
                        timedatestring = setdate;
                    }else{
                        timedatestring = timedatestring+ "\n\n"+setdate;
                    }

                    ArrayList<String> timeee = new ArrayList<String>();
                    timeee.addAll(selectionTimeDetails.get(d).arraySelectionTime);
                    AmountMultti = AmountMultti+timeee.size();
                    String s = TextUtils.join(",", timeee);
                    timedatestring = timedatestring+ "\n" + s;
                }
                    if(Service_Type.equals("CoursIndividuel")) {
                        if(!tenhourture){
                    amount = amount*AmountMultti;
                        }else {
                            amount = amountTen;
                        }
                    }else if(Service_Type.equals("CoursCollectifOndemand")){
//                        placeShow.setText(onDeamndModel.getLocation());
//                        priceShow.setVisibility(View.GONE);
//                        placelayout.setVisibility(View.VISIBLE);

                    }

                System.out.println("timedatestring--> "+timedatestring);
                TimeandDate.setText(timedatestring);
                if(Service_Type.equals("CoursIndividuel")) {
                    TypeofService.setText("Cours individuel");
                }else if(Service_Type.equals("CoursCollectifOndemand")){
                    TypeofService.setText("Cours collectif ondemand");
                }
                coachname.setText(frist_name+" "+last_name);
                placeShow.setText("Paris");
                priceShow.setText(String.valueOf(amount));

                    String sendamout = new String();
                    if(!tenhourture) {
                        if (Service_Type.equals("CoursIndividuel")) {
                            sendamout = getIndiCoachDetailsModel.getPrice_min().toString();
                        } else if (Service_Type.equals("CoursCollectifOndemand")) {
                            sendamout = onDeamndModel.getPrice_6pl_1hr();
//                        placeShow.setText(onDeamndModel.getLocation());
//                        priceShow.setVisibility(View.GONE);
//                        placelayout.setVisibility(View.VISIBLE);
                        }
                    }else {
                        sendamout = getIndiCoachDetailsModel.getPrice_max();
                    }
                    ArrayList<RequestToBook> requestToBooks = new ArrayList<RequestToBook>();

                    String ondemandstring = "";

                    for(int d =0;d<selectionTimeDetails.size();d++){
                        String setdate = selectionTimeDetails.get(d).dateTitte;
                        ArrayList<String> timeee = new ArrayList<String>();
                        timeee.addAll(selectionTimeDetails.get(d).arraySelectionTime);

                        for(int t=0;t<timeee.size();t++){
                            if("08h à 09h".equals(timeee.get(t))) {
                                ondemandstring = "8h - 9h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "8h - 9h", userid_, sendamout.toString(),""));
                            }else if("09h à 10h".equals(timeee.get(t))){
                                ondemandstring = "9h - 10h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "9h - 10h", userid_, sendamout,""));
                            }else if("10h à 11h".equals(timeee.get(t))){
                                ondemandstring = "10h - 11h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "10h - 11h", userid_, sendamout,""));
                            }else if("11h à 12h".equals(timeee.get(t))){
                                ondemandstring = "11h - 12h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "11h - 12h", userid_, sendamout,""));
                            }else if("12h à 13h".equals(timeee.get(t))){
                                ondemandstring = "12h - 13h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "12h - 13h", userid_, sendamout,""));
                            }else if("13h à 14h".equals(timeee.get(t))){
                                ondemandstring = "13h - 14h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "13h - 14h", userid_, sendamout,""));
                            }else if("14h à 15h".equals(timeee.get(t))){
                                ondemandstring = "14h - 15h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "14h - 15h", userid_, sendamout,""));
                            }else if("15h à 16h".equals(timeee.get(t))){
                                ondemandstring = "15h - 16h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "15h - 16h", userid_, sendamout,""));
                            }else if("16h à 17h".equals(timeee.get(t))){
                                ondemandstring = "16h - 17h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "16h - 17h", userid_, sendamout,""));
                            }else if("17h à 18h".equals(timeee.get(t))){
                                ondemandstring = "17h - 18h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "17h - 18h", userid_, sendamout,""));
                            }else if("18h à 19h".equals(timeee.get(t))){
                                ondemandstring = "18h - 19h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "18h - 19h", userid_, sendamout,""));
                            }else if("19h à 20h".equals(timeee.get(t))){
                                ondemandstring = "19h - 20h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "19h - 20h", userid_, sendamout,""));
                            }else if("20h à 21h".equals(timeee.get(t))){
                                ondemandstring = "20h - 21h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "20h - 21h", userid_, sendamout,""));
                            }else if("21h à 22h".equals(timeee.get(t))){
                                ondemandstring = "21h - 22h";
                                requestToBooks.add(new RequestToBook(coach_id, Service_Type, setdate, "21h - 22h", userid_, sendamout,""));
                            }
                        }
                    }





                    avaibility_main_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(),selectionTimeDetails.get(0).getArraySelectionTime(),Toast.makeText())
//                    selectionTimeDetails.get(0).getArraySelectionTime();



                BookArray bookArray = new BookArray();
                bookArray.setRequestToBooks(requestToBooks);
                System.out.println("bookarray "+new Gson().toJson(bookArray));


                        apiInterface.setreservation(bookArray).enqueue(new Callback<ResevertionTimeResponseData>() {
                    @Override
                    public void onResponse(Call<ResevertionTimeResponseData> call, Response<ResevertionTimeResponseData> response) {
                            if(response.body().getIsSuccess().equals("true")){
                                selectiondategettime(dateSelected);
                                selectionTimeDetails.clear();
                                selectionTimeListAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                                ondemand_course_cout = 0;
                                indi_course_cout = 0;
                                secondtime = false;
                                tenhourture = false;

                                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                    }
                    @Override
                    public void onFailure(Call<ResevertionTimeResponseData> call, Throwable t) {

                    }
                });
                    }
                });
                avaibility_main_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });


                    if(Service_Type.equals("CoursCollectifOndemand")){
                        String[] listItem = ondemadnameValue.toArray(new String[ondemadnameValue.size()]);
                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listItem);
                        listView.setAdapter(adapter);
//                        if( ondemandList(ondemandstring)){
                        alertDialog.show();
//                        }
                    }else {
                        alertDialog.show();
                    }


//

            }else{
                    Toast.makeText(getApplicationContext()," Veuillez sélectionner la date et l'heure",Toast.LENGTH_LONG).show();

                }
            }
        });


//        Dialog_user_name = (TextView)dialogView.findViewById(R.id.dialog_user_name) ;
//        Dialog_user_course = (TextView)dialogView.findViewById(R.id.dialog_user_course) ;
//        Dialog_user_time_date = (TextView)dialogView.findViewById(R.id.dialog_date_time) ;
//        Dialog_set_status =(Button)dialogView.findViewById(R.id.dialog_set_status);
//        Dialog_close =(Button)dialogView.findViewById(R.id.dialog_close);
//        Reserve_dialog_heading = (TextView)dialogView.findViewById(R.id.reserve_dialog_heading);

    }

    private void createCriterias() {
        fridayCriteria = new WeekDayCriteria(Calendar.FRIDAY);

        threeMonthsCriteriaList = new ArrayList<>();
        threeMonthsCriteriaList.add(new CurrentMonthCriteria());
        threeMonthsCriteriaList.add(new NextMonthCriteria());
        threeMonthsCriteriaList.add(new PreviousMonthCriteria());
    }

    private void initArray(){
        timeShow.add("08h à 09h");
        timeShow.add("09h à 10h");
        timeShow.add("10h à 11h");
        timeShow.add("11h à 12h");
        timeShow.add("12h à 13h");
        timeShow.add("13h à 14h");
        timeShow.add("14h à 15h");
        timeShow.add("15h à 16h");
        timeShow.add("16h à 17h");
        timeShow.add("17h à 18h");
        timeShow.add("18h à 19h");
        timeShow.add("19h à 20h");
        timeShow.add("20h à 21h");
        timeShow.add("21h à 22h");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");
        timeStatus.add("unavailable");

        System.out.println("timeStatus.size();--." +timeStatus.size());

        AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(this,140);

        //   GridLayoutManager gridHorizontal = new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DefaultCalendarActivity.this,RecyclerView.HORIZONTAL,false);
        recyclerViewTime.setLayoutManager(autoFitGridLayoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        timeViewAdapter = new TimeViewAdapter(this,timeShow,timeStatus);
        recyclerViewTime.setAdapter(timeViewAdapter);
        recyclerViewTime.setHasFixedSize(true);
        recyclerViewTime.setNestedScrollingEnabled(false);
        recylerTimeSelection.setLayoutManager(linearLayoutManager);
        selectionTimeListAdapter = new SelectionTimeListAdapter(this,selectionTimeDetails,this);
        recylerTimeSelection.setAdapter(selectionTimeListAdapter);
        snapHelper.attachToRecyclerView(recylerTimeSelection);


        timeViewAdapter.setClickListener(new TimeViewAdapter.ItemClickListener() {
            @Override
            public void itemClickListerner(View view, int postion) {

                if(!dateSelected.equals("")) {
                    int sizearray = selectionTimeDetails.size();
                    System .out.println("Service_Type.equals(CoursIndividuel) && sizearray == 2 && !secondtime"
                    +Service_Type+"  "+sizearray+ "    " + secondtime);

                    System.out.println("12124");



                        for (int i = 0; selectionTimeDetails.size() >= 0; i++) {
                            int z = i;
                            if (selectionTimeDetails.size() != i) {
//                                if(Service_Type.equals("CoursIndividuel") && tenhourture  &&  indi_course_cout ==10 ) {
//                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            switch (which) {
//                                                case DialogInterface.BUTTON_POSITIVE:
//                                                    //Yes button clicked
////                                                            tenhourture = true;
//
//
//                                                    break;
//
//                                            }
//                                        }
//                                    };
//
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                                    builder.setCancelable(false);
//                                    builder.setMessage("Impossible de sélectionner plus de 10 heures.").setPositiveButton("OUI", dialogClickListener).show();
//
//                                }else if(Service_Type.equals("CoursCollectifOndemand")  && ondemand_course_cout ==1 ){
//                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            switch (which) {
//                                                case DialogInterface.BUTTON_POSITIVE:
//                                                    //Yes button clicked
////                                                            tenhourture = true;
//                                                    break;
//
//                                            }
//                                        }
//                                    };
//
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                                    builder.setCancelable(false);
//                                    builder.setMessage("La sélection sur plusieurs heures n'est pas possible.").setPositiveButton("OUI", dialogClickListener).show();
//
//                                }else


                                    if (selectionTimeDetails.get(i).getDateTitte().equals(dateSelected)){

                                    if (!selectionTimeDetails.get(i).getArraySelectionTime().contains(timeShow.get(postion))) {
                                        ArrayList<String> stringArrayList = selectionTimeDetails.get(i).getArraySelectionTime();

                                        if(Service_Type.equals("CoursIndividuel") && tenhourture  &&  indi_course_cout ==10){
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which) {
                                                        case DialogInterface.BUTTON_POSITIVE:

                                                            if(selectionTimeDetails.get(z).arraySelectionTime.isEmpty()){
                                                                selectionTimeDetails.remove(z);
                                                                selectionTimeListAdapter.notifyDataSetChanged();
                                                                recylerTimeSelection.scrollToPosition(selectionTimeDetails.size()-1);
                                                            }
                                                            //Yes button clicked
//                                                            tenhourture = true;
                                                            break;

                                                    }
                                                }
                                            };

                                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                            builder.setCancelable(false);
                                            builder.setMessage("Impossible de sélectionner plus de 10 heures.").setPositiveButton("OUI", dialogClickListener).show();

                                        }else if(Service_Type.equals("CoursIndividuel")){
                                            stringArrayList.add(timeShow.get(postion));
                                            indi_course_cout = indi_course_cout +1;
                                        }

                                        if(Service_Type.equals("CoursCollectifOndemand")  && ondemand_course_cout ==1 ){
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which) {
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            if(selectionTimeDetails.get(z).arraySelectionTime.isEmpty()){
                                                                selectionTimeDetails.remove(z);
                                                                selectionTimeListAdapter.notifyDataSetChanged();
                                                                recylerTimeSelection.scrollToPosition(selectionTimeDetails.size()-1);
                                                            }
                                                            //Yes button clicked
//                                                            tenhourture = true;
                                                            break;

                                                    }
                                                }
                                            };

                                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                            builder.setCancelable(false);
                                            builder.setMessage("La sélection sur plusieurs heures n'est pas possible.").setPositiveButton("OUI", dialogClickListener).show();

                                        }else if(Service_Type.equals("CoursCollectifOndemand")){
                                            stringArrayList.add(timeShow.get(postion));
                                            ondemandList(timeShow.get(postion));
                                            ondemand_course_cout = ondemand_course_cout +1;
                                        }


                                        if (Service_Type.equals("CoursIndividuel") && stringArrayList.size() == 2 && !secondtime) {

                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which) {
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            //Yes button clicked
                                                            tenhourture = true;
                                                            break;

                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                            //No button clicked
                                                            tenhourture = false;
                                                            break;
                                                    }
                                                }
                                            };

                                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                            builder.setCancelable(false);
                                            builder.setMessage("Un forfait de 10 heures est disponible.").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                                            System.out.println("1212433");
                                            secondtime = true;
                                        }



                                        if (!Service_Type.equals("CoursIndividuel") || selectionTimeDetails.size() != 10 || !tenhourture){
                                        System.out.println("stringArrayList--> " + stringArrayList);
                                        selectionTimeDetails.set(i, new SelectionTimeDetails(dateSelected, stringArrayList));
                                        selectionTimeListAdapter.notifyDataSetChanged();
                                        recylerTimeSelection.scrollToPosition(i);
                                        }
//                            Toast.makeText(DefaultCalendarActivity.this,"testing 2",Toast.LENGTH_LONG).show();
                                        break;
                                    } else {
                                        recylerTimeSelection.scrollToPosition(i);
                                        Toast.makeText(DefaultCalendarActivity.this, "Déjà ajouté", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                } else if (i == sizearray) {
                                    ArrayList<String> stringArrayList = new ArrayList<String>();

                                    if(Service_Type.equals("CoursIndividuel") && tenhourture  &&  indi_course_cout ==10){
                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        if(selectionTimeDetails.get(z).arraySelectionTime.isEmpty()){
                                                            selectionTimeDetails.remove(z);
                                                            selectionTimeListAdapter.notifyDataSetChanged();
                                                            recylerTimeSelection.scrollToPosition(selectionTimeDetails.size()-1);
                                                        }
                                                        //Yes button clicked
//                                                            tenhourture = true;
                                                        break;

                                                }
                                            }
                                        };

                                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                        builder.setCancelable(false);
                                        builder.setMessage("Impossible de sélectionner plus de 10 heures.").setPositiveButton("OUI", dialogClickListener).show();

                                    }else if(Service_Type.equals("CoursIndividuel")){
                                        stringArrayList.add(timeShow.get(postion));
                                        indi_course_cout = indi_course_cout +1;
                                    }

                                    if(Service_Type.equals("CoursCollectifOndemand")  && ondemand_course_cout ==1 ){
                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        //Yes button clicked
//                                                            tenhourture = true;
                                                        break;

                                                }
                                            }
                                        };

                                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                        builder.setCancelable(false);
                                        builder.setMessage("La sélection sur plusieurs heures n'est pas possible.").setPositiveButton("OUI", dialogClickListener).show();

                                    }else if(Service_Type.equals("CoursCollectifOndemand")){
                                        stringArrayList.add(timeShow.get(postion));
                                        ondemandList(timeShow.get(postion));
                                        ondemand_course_cout = ondemand_course_cout +1;
                                    }


                                    if (Service_Type.equals("CoursIndividuel") && stringArrayList.size() == 2 && !secondtime) {

                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        //Yes button clicked
                                                        tenhourture = true;
                                                        break;

                                                    case DialogInterface.BUTTON_NEGATIVE:
                                                        //No button clicked
                                                        tenhourture = false;
                                                        break;
                                                }
                                            }
                                        };

                                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                        builder.setCancelable(false);
                                        builder.setMessage("Un forfait de 10 heures est disponible.").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                                        System.out.println("1212433");
                                        secondtime = true;
                                    }
//                                    stringArrayList.add(timeShow.get(postion));
                                    if (!Service_Type.equals("CoursIndividuel") || selectionTimeDetails.size() != 10 || !tenhourture){
                                    selectionTimeDetails.add(new SelectionTimeDetails(dateSelected, stringArrayList));
                                    selectionTimeListAdapter.notifyDataSetChanged();
                                    recylerTimeSelection.scrollToPosition(selectionTimeDetails.size() - 1);
                                    }
//                        Toast.makeText(DefaultCalendarActivity.this,"testing",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            } else {
                                ArrayList<String> stringArrayList = new ArrayList<String>();
                                if(Service_Type.equals("CoursIndividuel") && tenhourture&&  indi_course_cout ==10){
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    if(selectionTimeDetails.get(z).arraySelectionTime.isEmpty()){
                                                        selectionTimeDetails.remove(z);
                                                        selectionTimeListAdapter.notifyDataSetChanged();
                                                        recylerTimeSelection.scrollToPosition(selectionTimeDetails.size()-1);
                                                    }
                                                    //Yes button clicked
//                                                            tenhourture = true;
                                                    break;

                                            }
                                        }
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setCancelable(false);
                                    builder.setMessage("Impossible de sélectionner plus de 10 heures.").setPositiveButton("OUI", dialogClickListener).show();

                                }else if(Service_Type.equals("CoursIndividuel")){
                                    stringArrayList.add(timeShow.get(postion));
                                    indi_course_cout = indi_course_cout +1;
                                }

                                if(Service_Type.equals("CoursCollectifOndemand") && ondemand_course_cout ==1 ){
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    //Yes button clicked
//                                                            tenhourture = true;
                                                    break;

                                            }
                                        }
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setCancelable(false);
                                    builder.setMessage("La sélection sur plusieurs heures n'est pas possible.").setPositiveButton("OUI", dialogClickListener).show();

                                }else if(Service_Type.equals("CoursCollectifOndemand")){
                                    stringArrayList.add(timeShow.get(postion));
                                    ondemandList(timeShow.get(postion));
                                    ondemand_course_cout = ondemand_course_cout +1;
                                }


                                if (Service_Type.equals("CoursIndividuel") && stringArrayList.size() == 2 && !secondtime) {

                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    //Yes button clicked
                                                    tenhourture = true;
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    //No button clicked
                                                    tenhourture = false;
                                                    break;
                                            }
                                        }
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setCancelable(false);
                                    builder.setMessage("Un forfait de 10 heures est disponible.").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                                    System.out.println("1212433");
                                    secondtime = true;
                                }
//                                stringArrayList.add(timeShow.get(postion));
                                if (!Service_Type.equals("CoursIndividuel") || selectionTimeDetails.size() != 10 || !tenhourture){
                                selectionTimeDetails.add(new SelectionTimeDetails(dateSelected, stringArrayList));
                                selectionTimeListAdapter.notifyDataSetChanged();
                                recylerTimeSelection.scrollToPosition(selectionTimeDetails.size() - 1);
                                }
//                        Toast.makeText(DefaultCalendarActivity.this,"testing",Toast.LENGTH_LONG).show();
                                break;
                            }
                        }

                }else{
                    Toast.makeText(getApplicationContext(),"Veuillez sélectionner la date",Toast.LENGTH_LONG).show();
                }
            }
        });

        selectionTimeListAdapter.setClickListener(new SelectionTimeListAdapter.ItemClickListenerRemoveSelection() {
            @Override
            public void itemClickListerner(View view, int postionDate, int postionTime) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_default_calendar_activity, menu);
//        menuFridays = menu.findItem(R.id.select_all_fridays);
//        menuThreeMonth = menu.findItem(R.id.select_three_months);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_all_fridays:
                fridayMenuClick();
                return true;

            case R.id.select_three_months:
                threeMonthsMenuClick();
                return true;

            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.log_selected_days:
                logSelectedDaysMenuClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fridayMenuClick() {
        if (fridayCriteriaEnabled) {
            menuFridays.setTitle(getString(R.string.select_all_fridays));
            unselectAllFridays();
        } else {
            menuFridays.setTitle(getString(R.string.unselect_all_fridays));
            selectAllFridays();
        }
        fridayCriteriaEnabled = !fridayCriteriaEnabled;
    }

    private void threeMonthsMenuClick() {
        if (threeMonthsCriteriaEnabled) {
            menuThreeMonth.setTitle(getString(R.string.select_three_months));
            unselectThreeMonths();
        } else {
            menuThreeMonth.setTitle(getString(R.string.unselect_three_months));
            selectThreeMonths();
        }
        threeMonthsCriteriaEnabled = !threeMonthsCriteriaEnabled;
    }

    private void selectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void unselectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void selectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void unselectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

        fridayCriteriaEnabled = false;
        threeMonthsCriteriaEnabled = false;
        menuFridays.setTitle(getString(R.string.select_all_fridays));
        menuThreeMonth.setTitle(getString(R.string.select_three_months));
    }


    private void logSelectedDaysMenuClick() {
        Toast.makeText(this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        clearSelectionsMenuClick();
        switch (checkedId) {
            case R.id.rb_horizontal:
                calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
                break;

            case R.id.rb_vertical:
                calendarView.setCalendarOrientation(OrientationHelper.VERTICAL);
                break;

            case R.id.rb_single:
                calendarView.setSelectionType(SelectionType.SINGLE);
                menuFridays.setVisible(false);
                menuThreeMonth.setVisible(false);
                break;

            case R.id.rb_multiple:
                calendarView.setSelectionType(SelectionType.MULTIPLE);
                menuFridays.setVisible(true);
                menuThreeMonth.setVisible(true);
                break;

            case R.id.rb_range:
                calendarView.setSelectionType(SelectionType.RANGE);
                menuFridays.setVisible(false);
                menuThreeMonth.setVisible(false);
                break;

            case R.id.rb_none:
                calendarView.setSelectionType(SelectionType.NONE);
                menuFridays.setVisible(false);
                menuThreeMonth.setVisible(false);
                break;
        }
    }

public void selectiondategettime(String aa){

    apiInterface.getTimeslot(coach_id, aa,Service_Type).enqueue(new Callback<SetTimeSot>() {
        @Override
        public void onResponse(@NonNull Call<SetTimeSot> call, @NonNull Response<SetTimeSot> response) {

            if (response.body().getIsSuccess().equals("true")) {

                SetAvaibilityData setAvaibilityData = response.body().getData();
                ArrayList<SetAvaibilityTime> setAvaibilityTimes = new ArrayList<SetAvaibilityTime>(setAvaibilityData.getSetAvaibilityTimes());
                if (!setAvaibilityTimes.isEmpty()) {


                    for (int i = 0;i<setAvaibilityData.getSetAvaibilityTimes().size();i++) {
                        setAvaibilityTime.setAvailability(setAvaibilityData.getSetAvaibilityTimes().get(i).getAvailability());
                        setAvaibilityTime.setDescription(setAvaibilityData.getSetAvaibilityTimes().get(i).getDescription());
                        setAvaibilityTime.setSlotId(setAvaibilityData.getSetAvaibilityTimes().get(i).getSlotId());
                        if ("8h - 9h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(0, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(0, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(0, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(0, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("9h - 10h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(1, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(1, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(1, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(1, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("10h - 11h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(2, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(2, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(2, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(2, "booked");
                            }
                            System.out.println(i);
                        }
                        if ("11h - 12h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(3, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(3, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(3, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(3, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("12h - 13h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(4, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(4, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(4, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(4, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("13h - 14h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(5, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(5, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(5, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(5, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("14h - 15h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(6, "available");
                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(6, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(6, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(6, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("15h - 16h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(7, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(7, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(7, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(7, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("16h - 17h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(8, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(8, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(8, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(8, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("17h - 18h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(9, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(9, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(9, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(9, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("18h - 19h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(10, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(10, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(10, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(10, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("19h - 20h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(11, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(11, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(11, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(11, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("20h - 21h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(12, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(12, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(12, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(12, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                        if ("21h - 22h".equals(setAvaibilityTime.getDescription())) {
                            if (setAvaibilityTime.getAvailability().equals("Y")) {
                                timeStatus.set(13, "available");
                                System.out.println(i);

                            } else if (setAvaibilityTime.getAvailability().equals("N")) {
                                timeStatus.set(13, "unavailable");
                            } else if (setAvaibilityTime.getAvailability().equals("R")) {
                                timeStatus.set(13, "wait");
                            } else if (setAvaibilityTime.getAvailability().equals("B")) {
                                timeStatus.set(13, "booked");
                            }
                            System.out.println("timeStatus--> ff"+ timeStatus);
                        }
                    }
                    timeViewAdapter.notifyDataSetChanged();
//                                    Toast.makeText(DefaultCalendarActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                                    System.out.println("timeslotgggg " + new Gson().toJson(response.body()));
                } else {

                    timeStatus.set(0,"unavailable");
                    timeStatus.set(1,"unavailable");
                    timeStatus.set(2,"unavailable");
                    timeStatus.set(3,"unavailable");
                    timeStatus.set(4,"unavailable");
                    timeStatus.set(5,"unavailable");
                    timeStatus.set(6,"unavailable");
                    timeStatus.set(7,"unavailable");
                    timeStatus.set(8,"unavailable");
                    timeStatus.set(9,"unavailable");
                    timeStatus.set(10,"unavailable");
                    timeStatus.set(11,"unavailable");
                    timeStatus.set(12,"unavailable");
                    timeStatus.set(13,"unavailable");
                    timeViewAdapter.notifyDataSetChanged();
                    Toast.makeText(DefaultCalendarActivity.this, "Indisponible", Toast.LENGTH_LONG).show();
                    System.out.println("Array_empty " + new Gson().toJson(response.body()));
                }


            } else {
                System.out.println("timeslot " + new Gson().toJson(response.body()));
                Toast.makeText(DefaultCalendarActivity.this, response.body().getMessage() + "...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<SetTimeSot> call, Throwable t) {
            System.out.println("---> " + call + " " + t);
        }
    });

//    indi_course_cout = indi_course_cout;
//    ondemand_course_cout = ondemand_course_cout;

}

public void Summerydetails() {
    System.out.println("- --> " + " jjjjjjj ");

    apiInterface.getGetIndiCourseDetails(coach_id).enqueue(new Callback<GetIndiCoachDetailsResponse>() {
        @Override
        public void onResponse(@NonNull Call<GetIndiCoachDetailsResponse> call, @NonNull Response<GetIndiCoachDetailsResponse> response) {
            assert response.body() != null;
            System.out.println("getIndiCoachDetailsModel--> " + new Gson().toJson(response.body()));
            if (response.body().getIsSuccess().equals("true")) {
                System.out.println("- --> " + new Gson().toJson(response.body()));
                indiCourseData = response.body().getGetIndiCoachDetailsModel();
                getIndiCoachDetailsModel = indiCourseData.getIndicouresedata().get(0);
                System.out.println("getIndiCoachDetailsModel--> " + new Gson().toJson(getIndiCoachDetailsModel));
            }else {
                System.out.println("getIndiCoachDetailsModel--> " + new Gson().toJson(response.body()));
            }
        }

        @Override
        public void onFailure(Call<GetIndiCoachDetailsResponse> call, Throwable t) {
            System.out.println("---> " + call + " " + t);
        }
    });


}

public void ondemandList(String value){
System.out.println("value-->"+ value);
    ondemadnameValue.clear();

    apiInterface.getdemandavailability(coach_id,value,dateSelected).enqueue(new Callback<OnDemandDTO>() {
        @Override
        public void onResponse(Call<OnDemandDTO> call, Response<OnDemandDTO> response) {
            System.out.println("response---> "+new Gson().toJson(response.body()));
            int j = 0;
            for(int i=0;response.body().getData().getAvailabilty().size()>i;i++){
                System.out.println("getdemandavailability--> "+response.body().getData().getAvailabilty().get(i).getFirstName());
                ondemadnameValue.add(response.body().getData().getAvailabilty().get(i).getFirstName());
            }

        }
        @Override
        public void onFailure(Call<OnDemandDTO> call, Throwable t) {
            System.out.println("error---> "+t);
        }
    });
//    return true;
    }


public void Summerydetailsondemand(){

    apiInterface.getcousecollectivedemanad(coach_id).enqueue(new Callback<GetCoachCollectiveOnDemandResponseData>() {
        @Override
        public void onResponse(@NonNull Call<GetCoachCollectiveOnDemandResponseData> call, @NonNull Response<GetCoachCollectiveOnDemandResponseData> response) {
            assert response.body() != null;
            if (response.body().getIsSuccess().equals("true")) {
                if (!response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().isEmpty()) {
                    if (response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0) != null) {
                        onDeamndModel = response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0);
                    }
                }
            }
        }
        @Override
        public void onFailure(Call<GetCoachCollectiveOnDemandResponseData> call, Throwable t) {
            System.out.println("---> " + call + " " + t);
        }
    });

}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

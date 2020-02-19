package com.tech.cloudnausor.ohmytennis.activity.homepage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.BuildConfig;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.OhMyEventsDTO;
import com.tech.cloudnausor.ohmytennis.fragments.NewHomePageFragment;
import com.tech.cloudnausor.ohmytennis.fragments.OhMyEventSevice;
import com.tech.cloudnausor.ohmytennis.fragments.ProfileDetailsFragment;
import com.tech.cloudnausor.ohmytennis.fragments.coachuserreservation.CoachUserReservationFragment;
import com.tech.cloudnausor.ohmytennis.fragments.realhomefragment.RealHomeFragment;
import com.tech.cloudnausor.ohmytennis.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennis.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Constant;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Menus;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.NavigationAdapter;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.NavigationList;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Utils;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHomePage extends AppCompatActivity {
    private int mLastPosition = 0;
    private ListView mListDrawer;
    private DrawerLayout mLayoutDrawer;
    private RelativeLayout mUserDrawer;
    private RelativeLayout mRelativeDrawer;
    TextView title,tvVersion;
    private FragmentManager mFragmentManager;
    private NavigationAdapter mNavigationAdapter;
    private ActionBarDrawerToggleCompat mDrawerToggle;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uPassword =null;
    List<Integer> mListHeader;
    List<Integer> mListHide;
    SparseIntArray mCounter;
    String countVisible = "0";
    SingleTonProcess singleTonProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setIcon(R.drawable.ic_launcher_background);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.activity_new_home_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sharedPreferences = getApplicationContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        singleTonProcess = singleTonProcess.getInstance();

        mListDrawer = (ListView) findViewById(R.id.listDrawer);
        mRelativeDrawer = (RelativeLayout) findViewById(R.id.relativeDrawer);
        mLayoutDrawer = (DrawerLayout) findViewById(R.id.layoutDrawer);
        title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        mUserDrawer = (RelativeLayout) findViewById(R.id.userDrawer);
        mUserDrawer.setOnClickListener(userOnClick);
        tvVersion = (TextView)findViewById(R.id.version_code);

        String versionName = BuildConfig.VERSION_NAME;
        tvVersion.setText("Version : " + versionName);




        if (mListDrawer != null) {
//            System.out.println("ffhf");

            // All header menus should be informed here
            // listHeader.add(MENU POSITION)
             mListHeader = new ArrayList<Integer>();

//            mListHeader.add(4);

            mListHide = new ArrayList<Integer>();
            mListHide.add(4);
            mListHide.add(5);
            mListHide.add(6);
            mListHide.add(7);
//            mListHide.add(2);
//            mListHeader.add(6);
//            mListHeader.add(10);

            // All menus which will contain an accountant should be informed here
            // Counter.put ("POSITION MENU", "VALUE COUNTER");

            mCounter = new SparseIntArray();
//            mCounter.put(Constant.MENU_RESERVATION,10);
            String[] mMenuItems = getResources().getStringArray(R.array.nav_menu_items);

//            mCounter.put(Constant.MENU_LOGOUT,10);
            mNavigationAdapter = new NavigationAdapter(this, NavigationList.getNavigationAdapter(this, mListHeader, mCounter, mListHide));
        }

        mListDrawer.setAdapter(mNavigationAdapter);
        mListDrawer.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggleCompat(this, mLayoutDrawer);
        mLayoutDrawer.addDrawerListener(mDrawerToggle);

        if (savedInstanceState != null) {
            setLastPosition(savedInstanceState.getInt(Constant.LAST_POSITION));
            setTitleFragments(mLastPosition);
            mNavigationAdapter.resetarCheck();
            mNavigationAdapter.setChecked(mLastPosition, true);
        }else{
            setLastPosition(mLastPosition);
            setFragmentList(mLastPosition);
        }

        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");
        }

    }

    private void setFragmentList(int posicao){
        Fragment mFragment = null;
        mFragmentManager = getSupportFragmentManager();
        switch (posicao) {
            case Constant.MENU_DASHBOARD:
                mFragment = new RealHomeFragment().newInstance(Utils.getTitleItem(this, Constant.MENU_DASHBOARD));
                break;
            case Constant.MENU_OH_MY_COACH:
                mFragment = new NewHomePageFragment().newInstance(Utils.getTitleItem(this, Constant.MENU_DASHBOARD));
                break;
            case Constant.MENU_MY_ACCOUNT:
                mFragment = new ProfileDetailsFragment().newInstance(Utils.getTitleItem(this, Constant.MENU_MY_ACCOUNT));
                break;
            case Constant.MENU_OH_MY_EVENT:

                if (countVisible.equals("0")) {
                    mListHide.clear();
                    mNavigationAdapter = new NavigationAdapter(this, NavigationList.getNavigationAdapter(this, mListHeader, mCounter, mListHide));
                    mListDrawer.setAdapter(mNavigationAdapter);
//                    mNavigationAdapter.notifyDataSetChanged();
                    countVisible="1";
                }else if(countVisible.equals("1")){
                    mListHide.clear();
                    mListHide.add(4);
                    mListHide.add(5);
                    mListHide.add(6);
                    mListHide.add(7);
                    mNavigationAdapter = new NavigationAdapter(this, NavigationList.getNavigationAdapter(this, mListHeader, mCounter, mListHide));
                    mListDrawer.setAdapter(mNavigationAdapter);
//                    mNavigationAdapter.notifyDataSetChanged();
                    countVisible="0";
                }

//                mFragment =  new CoachUserReservationFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_RESERVATION));
                break;
            case Constant.MENU_STAGE:
//                Bundle mBundle = new Bundle();
//                mBundle.putString("text", "stage");
//                mFragment.setArguments(mBundle);
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_STAGE),"stage");
//                mFragment.setArguments(mBundle);
                break;
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
//                mFragment =  new StageFragmentPage().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_STAGE));
//                break;
            case Constant.MENU_TEAMBUILDING:
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_TEAMBUILDING),"teambuilding");

//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
//                mFragment =  new TeamBuildingFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_TEAMBUILDING));
                break;
            case Constant.MENU_ANIMATION:
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_ANIMATION),"animation");

//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
//                mFragment =  new AnimationFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_ANIMATION));
                break;
            case Constant.MENU_TOURNAMENT:
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_TOURNAMENT),"tournament");
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
//                mFragment =  new TournamentFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_TOURNAMENT));
                break;

            case Constant.MENU_RESERVATION:
                mFragment =  new CoachUserReservationFragment().newInstance(Utils.getTitleItem(NewHomePage.this, Constant.MENU_RESERVATION));
                break;


//            case Constant.MENU_CHANGE_PASS:
////                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
////                mFragment =  new ChangePasswordActivity().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_CHANGE_PASS));
//                break;

        }
        if (mFragment != null){
            setTitleFragments(mLastPosition);
            mNavigationAdapter.resetarCheck();
            mNavigationAdapter.setChecked(posicao, true);
            mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragment).commit();
        }
    }
    private void hideMenus(Menu menu, int posicao) {
        boolean drawerOpen = mLayoutDrawer.isDrawerOpen(mRelativeDrawer);
        switch (posicao) {
            case Constant.MENU_DASHBOARD:
//                menu.findItem(Menus.HOME).setVisible(!drawerOpen);
//                menu.findItem(Menus.ADD).setVisible(!drawerOpen);
//                menu.findItem(Menus.UPDATE).setVisible(!drawerOpen);
//                menu.findItem(Menus.SEARCH).setVisible(!drawerOpen);
                break;

            case Constant.MENU_MY_ACCOUNT:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_RESERVATION:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;

            case Constant.MENU_TEAMBUILDING:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_ANIMATION:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_TOURNAMENT:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
//            case Constant.MENU_CHANGE_PASS:
////                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
//                break;
            case Constant.MENU_LOGOUT:

                editor.putBoolean("IsUserLogIn",false);
                editor.commit();
                Intent intent = new Intent(NewHomePage.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

//                Intent intent = new Intent(RealMainPageActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);

                break;
            default:
                break;
        }
    }

    private void setTitleFragments(int position){
        setIconActionBar(Utils.iconNavigation[position]);
        title.setText((Utils.getTitleItem(NewHomePage.this, position)).replaceAll("  ",""));
        title.setText((Utils.getTitleItem(NewHomePage.this, position)).replaceAll("\t",""));
        setTitleActionBar(Utils.getTitleItem(NewHomePage.this, position).replace("  ",""));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.LAST_POSITION, mLastPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menus.HOME:
                if (mLayoutDrawer.isDrawerOpen(mRelativeDrawer)) {
                    mLayoutDrawer.closeDrawer(mRelativeDrawer);
                } else {
                    mLayoutDrawer.openDrawer(mRelativeDrawer);
                }
                return true;
            case Menus.ADD:
                Toast.makeText(this,"",Toast.LENGTH_LONG).show();
                return true;
            default:
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        hideMenus(menu, mLastPosition);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void setTitleActionBar(CharSequence informacao) {
        getSupportActionBar().setTitle(informacao);
    }

    public void setSubtitleActionBar(CharSequence informacao) {
        getSupportActionBar().setSubtitle(informacao);
    }

    public void setIconActionBar(int icon) {
        getSupportActionBar().setIcon(icon);
    }

    public void setLastPosition(int posicao){
        this.mLastPosition = posicao;
    }

    private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle
    {
        public ActionBarDrawerToggleCompat(Activity mActivity, DrawerLayout mDrawerLayout){
            super(
                    mActivity,
                    mDrawerLayout,
                    R.string.drawer_open,
                    R.string.drawer_close);
        }

        @Override
        public void onDrawerClosed(View view) {
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            mNavigationAdapter.notifyDataSetChanged();
            supportInvalidateOptionsMenu();

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
            setLastPosition(posicao);

            setFragmentList(mLastPosition);
            if(posicao != 3){
            mLayoutDrawer.closeDrawer(mRelativeDrawer);
            }

        }
    }

    private View.OnClickListener userOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//            mLayoutDrawer.closeDrawer(mRelativeDrawer);
        }
    };

    @Override
    public void onBackPressed() {
        if(mLastPosition != Constant.MENU_DASHBOARD){
            setLastPosition(Constant.MENU_DASHBOARD);
            setFragmentList(Constant.MENU_DASHBOARD);
        }else {
            super.onBackPressed();
            finish();
        }
    }

    public void showprocess(){
        singleTonProcess.show(NewHomePage.this);
    }

    public void dismissprocess(){
        singleTonProcess.dismiss();
    }


}

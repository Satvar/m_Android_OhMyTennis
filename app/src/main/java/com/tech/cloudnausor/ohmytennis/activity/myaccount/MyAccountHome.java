package com.tech.cloudnausor.ohmytennis.activity.myaccount;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.fragments.PersonalInfoFragment;
import com.tech.cloudnausor.ohmytennis.fragments.TransactionDetailsFragment;

public class MyAccountHome extends AppCompatActivity {
Class fragmentClass;
TextView My_A_Personal_Details,My_A_Transaction_Details;
ImageView One_Dot,Second_dot;
ProgressBar progressBarLine;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_account_home);
        My_A_Personal_Details =(TextView)findViewById(R.id.persaonaldetails);
        My_A_Transaction_Details =(TextView)findViewById(R.id.tradetails);
        Second_dot =(ImageView)findViewById(R.id.second_dot);
        progressBarLine =(ProgressBar)findViewById(R.id.progressline);
        ImageView GoBack =(ImageView)findViewById(R.id.back);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        My_A_Personal_Details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine.setProgress(0);
                fragmentClass = PersonalInfoFragment.class;
                loadFragment(fragmentClass);
            }
        });

        My_A_Transaction_Details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                progressBarLine.setProgress(100);
                fragmentClass = TransactionDetailsFragment.class;
                loadFragment(fragmentClass);
            }
        });
        fragmentClass = PersonalInfoFragment.class;
        loadFragment(fragmentClass);
    }

    public void loadFragment(Class fragmentClass) {

        System.out.println("current fragment--------->"+fragmentClass.getSimpleName());

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.myaccountpage, fragment, fragment.getClass().getSimpleName()).commit();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

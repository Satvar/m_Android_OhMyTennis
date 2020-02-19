package com.tech.cloudnausor.ohmytennis.activity.listpartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.adapter.CommandListAdapter;
import com.tech.cloudnausor.ohmytennis.adapter.ListPartnerAdapter;
import com.tech.cloudnausor.ohmytennis.model.CommandListModel;
import com.tech.cloudnausor.ohmytennis.model.ListPartnerModel;

import java.util.ArrayList;

public class ListPartnerActivity extends AppCompatActivity {
    RecyclerView Listpartner_cycle;
    ListPartnerAdapter listPartnerAdapter;
    ArrayList<ListPartnerModel> listPartnerModelArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list_partner);
        ImageView GoBack =(ImageView)findViewById(R.id.back);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Listpartner_cycle = (RecyclerView)findViewById(R.id.listpartner_cycle);
        Listpartner_cycle.setLayoutManager(new LinearLayoutManager(this));
        listPartnerAdapter = new ListPartnerAdapter(ListPartnerActivity.this,listPartnerModelArrayList);
        Listpartner_cycle.setAdapter(listPartnerAdapter);
        liatPartnerData();
    }

    public void liatPartnerData(){
        listPartnerModelArrayList.add(new ListPartnerModel("Bala","5","Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        listPartnerModelArrayList.add(new ListPartnerModel("Chandran",
                "3","Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        listPartnerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListPartnerActivity.this, IndividualCourseHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

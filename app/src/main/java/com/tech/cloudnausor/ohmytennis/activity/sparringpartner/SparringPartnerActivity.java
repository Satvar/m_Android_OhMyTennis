package com.tech.cloudnausor.ohmytennis.activity.sparringpartner;

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
import com.tech.cloudnausor.ohmytennis.adapter.SparringPartnerAdapter;
import com.tech.cloudnausor.ohmytennis.model.CommandListModel;
import com.tech.cloudnausor.ohmytennis.model.SparringPartnerModel;

import java.util.ArrayList;

public class SparringPartnerActivity extends AppCompatActivity {
    RecyclerView Sparring_partner_cycle;
    SparringPartnerAdapter sparringPartnerAdapter;
    ArrayList<SparringPartnerModel>  sparringPartnerModelArrayList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sparring_partner);
        Sparring_partner_cycle = (RecyclerView)findViewById(R.id.sparring_partner_cycle);
        ImageView GoBack =(ImageView)findViewById(R.id.back);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Sparring_partner_cycle.setLayoutManager(new LinearLayoutManager(this));
        sparringPartnerAdapter = new SparringPartnerAdapter(this,sparringPartnerModelArrayList);
        Sparring_partner_cycle.setAdapter(sparringPartnerAdapter);
        sparringPartnerData();
    }
    public void sparringPartnerData(){
        sparringPartnerModelArrayList.add(new SparringPartnerModel("Bala","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "5","Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        sparringPartnerModelArrayList.add(new SparringPartnerModel("Chandran","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "3","Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        sparringPartnerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SparringPartnerActivity.this, IndividualCourseHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

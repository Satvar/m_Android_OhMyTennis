package com.tech.cloudnausor.ohmytennis.activity.command;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;
import com.tech.cloudnausor.ohmytennis.adapter.CommandListAdapter;
import com.tech.cloudnausor.ohmytennis.dto.OhMyEventsDTO;
import com.tech.cloudnausor.ohmytennis.model.CommandListModel;

import java.util.ArrayList;

public class CommandListActivity extends AppCompatActivity {
    RecyclerView Command_cycle;
    CommandListAdapter commandListAdapter;
    ArrayList<CommandListModel> commandListModelArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_command_list);
        ImageView GoBack =(ImageView)findViewById(R.id.back);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Command_cycle = (RecyclerView)findViewById(R.id.command_cycle);
        Command_cycle.setLayoutManager(new LinearLayoutManager(this));
        commandListAdapter = new CommandListAdapter(this,commandListModelArrayList);
        Command_cycle.setAdapter(commandListAdapter);
        commandData();



    }

    public void commandData(){
        commandListModelArrayList.add(new CommandListModel("Bala","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "5","Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        commandListModelArrayList.add(new CommandListModel("Chandran","Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "3","Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        commandListAdapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CommandListActivity.this, IndividualCourseHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

package com.tech.cloudnausor.ohmytennis.activity.myaccount;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.command.CommandListActivity;
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity;

public class MyAccountPreview extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_account_preview);
        toolbar = (Toolbar)findViewById(R.id.tool_myaccout);
        imageView = (ImageView)toolbar.findViewById(R.id.edit_tool);
        ImageView GoBack =(ImageView)findViewById(R.id.back);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountPreview.this,MyAccountHome.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyAccountPreview.this, IndividualCourseHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

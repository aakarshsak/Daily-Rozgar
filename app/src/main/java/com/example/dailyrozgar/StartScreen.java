package com.example.dailyrozgar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartScreen extends AppCompatActivity {
    ImageView userImage,workerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        userImage=findViewById(R.id.userImage);
        workerImage=findViewById(R.id.workerImage);


        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreen.this,MainActivity.class));
            }
        });

        workerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreen.this,WorkersListActivity.class));
            }
        });
    }
}

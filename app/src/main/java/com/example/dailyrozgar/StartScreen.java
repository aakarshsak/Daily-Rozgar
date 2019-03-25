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


        ///username and password image buttons to enter into username or password section
        userImage=findViewById(R.id.userImage);
        workerImage=findViewById(R.id.workerImage);

//
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starting login page to sign in as a user
                startActivity(new Intent(StartScreen.this, CustomerLogin.class));
            }
        });

        workerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting login page to sign in as a worker
                startActivity(new Intent(StartScreen.this,WorkerMainActivity.class));
            }
        });
    }
}

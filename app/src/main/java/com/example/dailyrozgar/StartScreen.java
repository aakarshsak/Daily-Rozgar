package com.example.dailyrozgar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.dailyrozgar.Developers.DevCustomAdapter;
import com.example.dailyrozgar.Developers.DevData;
import com.example.dailyrozgar.Developers.DevDataModel;

import java.util.ArrayList;

public class StartScreen extends AppCompatActivity {
    ImageView userImage,workerImage;
//    RecyclerView recyclerView;
//    RecyclerView.Adapter adapter;
//    RecyclerView.LayoutManager layoutManager;
//    static ArrayList<DevDataModel> dataModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        userImage=findViewById(R.id.userImage);
        workerImage=findViewById(R.id.workerImage);
//        recyclerView=findViewById(R.id.startRecycler);
//
//        layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        dataModels=new ArrayList<DevDataModel>();
//
//        for(int i=0;i<3;i++)
//        {
//            dataModels.add(new DevDataModel(DevData.devNameArray[i],DevData.devDescArray[i],DevData.devImageArray[i]));
//        }
//
//        adapter=new DevCustomAdapter(dataModels);
//        recyclerView.setAdapter(adapter);
//
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

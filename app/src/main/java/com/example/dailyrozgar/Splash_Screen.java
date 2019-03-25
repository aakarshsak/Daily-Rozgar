package com.example.dailyrozgar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class Splash_Screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(3000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(Splash_Screen.this,StartScreen.class));
                }
            }
        });
        t.start();
    }
}

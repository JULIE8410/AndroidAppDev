package com.example.finalproject_group7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        TimerTask timer=new TimerTask() {
            @Override
            public void run() {

                finish();
                startActivity(new Intent(splash_activity.this,MainActivity.class));
            }
        };

        Timer timerObj=new Timer();
        timerObj.schedule(timer,1000);


    }
}
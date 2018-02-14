package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.projects.android.MyNotes.R;

public class Splash extends AppCompatActivity {
    private static int splash_timeout = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Splash.this, Home.class);
                startActivity(homeIntent);
                finish();
            }
        }, splash_timeout);
    }
}
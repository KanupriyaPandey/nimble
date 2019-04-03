package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.projects.android.MyNotes.R;

public class Splash extends AppCompatActivity {
    private static int splash_timeout = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Splash.this, Main.class);
                startActivity(homeIntent);
                finish();
            }
        }, splash_timeout);
    }
}

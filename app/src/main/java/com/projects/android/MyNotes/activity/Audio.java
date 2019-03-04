package com.projects.android.MyNotes.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;

public class Audio extends AppCompatActivity {
Chronometer chronometer;
TextView recordMessage;
Button btnRecord;
private int recordCount = 0;
Boolean startRecord = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        recordMessage = findViewById(R.id.record_message);
        chronometer = findViewById(R.id.chronometer);
        btnRecord = findViewById(R.id.btnRecord);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 10);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(startRecord);
                startRecord = !startRecord;
            }
        });
    }
    public void record(Boolean startRecord)
    {
        Intent intent = new Intent(getApplicationContext(), RecordService.class);
        if (startRecord)
        {
            recordCount = 0;
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (recordCount == 0)
                        recordMessage.setText("Recording.");
                    else  if(recordCount == 1)
                        recordMessage.setText("Recording..");
                    else if(recordCount == 2){
                        recordCount = -1;
                        recordMessage.setText("Recording...");
                    }
                    recordCount++;
                }
            });
            try {
                startService(intent);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                recordCount++;
            }
            catch (Exception e)
            {
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            recordMessage.setText(getString(R.string.start_recording));
            stopService(intent);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}

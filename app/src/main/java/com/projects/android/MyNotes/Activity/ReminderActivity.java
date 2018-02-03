package com.projects.android.MyNotes.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.projects.android.MyNotes.R;

public class ReminderActivity extends AppCompatActivity {
    NotificationCompat.Builder notification;
    private static final int uniqueID=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        notification=new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);


    }

    public void displayNotification(String title,String body){
        notification.setSmallIcon(R.mipmap.mynotesicon);
        notification.setTicker("Reminder");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(title);
        notification.setContentText(body);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        notification.setVibrate(pattern);

        Intent intent=new Intent(getApplicationContext(),ReminderActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pi);

        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }
}

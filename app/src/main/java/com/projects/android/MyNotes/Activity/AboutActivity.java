package com.projects.android.MyNotes.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.projects.android.MyNotes.R;

public class AboutActivity extends AppCompatActivity {
    TextView git_tv,gmail_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        git_tv=(TextView)findViewById(R.id.github);
        gmail_tv=(TextView)findViewById(R.id.mail);

        git_tv.setMovementMethod(LinkMovementMethod.getInstance());
        gmail_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to="kanupriya.pandey76@gmail.com";
                Intent i3=new Intent(Intent.ACTION_SEND, Uri.fromParts("MailTo","kanupriya.pandey76@gmail.com",null));
                i3.setType("message/rfc822");
                i3.putExtra(Intent.EXTRA_EMAIL,new String[]{ to});
                i3.putExtra(Intent.EXTRA_SUBJECT,"About Notes App");
                startActivity(Intent.createChooser(i3,"Chose Mail App"));
            }
        });


    }



}

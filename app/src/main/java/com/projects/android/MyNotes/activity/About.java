package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Shared_Preferences;

public class About extends AppCompatActivity {
    TextView git, gmail, git2, gmail2;
    Shared_Preferences shared_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared_preferences = new Shared_Preferences(getApplicationContext());
        setTheme(shared_preferences.getTheme());
        setContentView(R.layout.activity_about);
        git = (TextView) findViewById(R.id.github);
        gmail = (TextView) findViewById(R.id.mail);
        git2 = (TextView) findViewById(R.id.github2);
        gmail2 = (TextView) findViewById(R.id.mail2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
       // CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_about);
       // collapsingToolbarLayout.setTitle("About");
       // collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        git.setMovementMethod(LinkMovementMethod.getInstance());
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "kanupriya.pandey76@gmail.com";
                Intent i3 = new Intent(Intent.ACTION_SEND, Uri.fromParts("MailTo", "kanupriya.pandey76@gmail.com", null));
                i3.setType("message/rfc822");
                i3.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                i3.putExtra(Intent.EXTRA_SUBJECT, "About Notes App");
                startActivity(Intent.createChooser(i3, "Choose Mail App"));
            }
        });

        git2.setMovementMethod(LinkMovementMethod.getInstance());
        gmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "aayush.coer15@gmail.com";
                Intent i3 = new Intent(Intent.ACTION_SEND, Uri.fromParts("MailTo", "aayush.coer15@gmail.com", null));
                i3.setType("message/rfc822");
                i3.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                i3.putExtra(Intent.EXTRA_SUBJECT, "About Notes App");
                startActivity(Intent.createChooser(i3, "Choose Mail App"));
            }
        });
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_about);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
    }
}

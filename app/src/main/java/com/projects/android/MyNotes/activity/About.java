package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Preference;


public class About extends AppCompatActivity {
    TextView name, name2, support, license, role, role2, git, gmail, git2, gmail2, bug_title, github_title, license_title;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preference = new Preference(getApplicationContext());
        setTheme(preference.getTheme());

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);

        name = findViewById(R.id.name);
        name2 = findViewById(R.id.name2);
        support = findViewById(R.id.support);
        license = findViewById(R.id.license);
        role = findViewById(R.id.role);
        role2 = findViewById(R.id.role2);
        git = findViewById(R.id.github);
        gmail = findViewById(R.id.mail);
        git2 = findViewById(R.id.github2);
        gmail2 = findViewById(R.id.mail2);
        bug_title = findViewById(R.id.bug_title);
        github_title = findViewById(R.id.github_title);
        license_title = findViewById(R.id.license_title);

        Toolbar toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("About");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        setColor();
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

        findViewById(R.id.about_report_bug).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        findViewById(R.id.about_support_github).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        findViewById(R.id.license).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    private void setColor() {
        name.setTextColor(preference.get_primaryColor());
        name2.setTextColor(preference.get_primaryColor());
        support.setTextColor(preference.get_primaryColor());
        license.setTextColor(preference.get_primaryColor());

        role.setTextColor(preference.get_accentColor());
        role2.setTextColor(preference.get_accentColor());
        git.setTextColor(preference.get_accentColor());
        git2.setTextColor(preference.get_accentColor());
        gmail.setLinkTextColor(preference.get_accentColor());
        gmail2.setLinkTextColor(preference.get_accentColor());
        bug_title.setTextColor(preference.get_accentColor());
        github_title.setTextColor(preference.get_accentColor());
        license_title.setTextColor(preference.get_accentColor());
    }

}

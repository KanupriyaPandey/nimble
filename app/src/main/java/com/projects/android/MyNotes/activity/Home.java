package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.projects.android.MyNotes.R;
import com.github.clans.fab.FloatingActionButton;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton camera=(FloatingActionButton)findViewById(R.id.menu_item);
        final FloatingActionButton audio=(FloatingActionButton)findViewById(R.id.menu_item2);
        final FloatingActionButton attachment=(FloatingActionButton)findViewById(R.id.menu_item3);
        final FloatingActionButton reminder=(FloatingActionButton)findViewById(R.id.menu_item4);
        final FloatingActionButton text=(FloatingActionButton)findViewById(R.id.menu_item5);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text();
            }
        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder();
            }
        });
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachment();
            }
        });
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
  }

  public void text(){
      Intent i=new Intent(Home.this,Notes.class);
      startActivity(i);
  }
    public void reminder(){
        Intent i=new Intent(Home.this,Reminder.class);
        startActivity(i);
    }
    public void attachment(){
        Intent i=new Intent(Home.this,Notes.class);
        startActivity(i);
    }
    public void audio(){
        Intent i=new Intent(Home.this,Notes.class);
        startActivity(i);
    }
    public void camera(){
        Intent i=new Intent(Home.this,Notes.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_notes) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        } else if (id == R.id.nav_notebooks) {

        } else if (id == R.id.nav_reminders) {
            Intent i3 = new Intent(getApplicationContext(), Reminder.class);
            startActivity(i3);
        } else if (id == R.id.nav_trash) {

        } else if (id == R.id.nav_about) {
            Intent i5 = new Intent(getApplicationContext(), About.class);
            startActivity(i5);
        } else if (id == R.id.nav_settings) {
            Intent i6 = new Intent(getApplicationContext(), Settings.class);
            startActivity(i6);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

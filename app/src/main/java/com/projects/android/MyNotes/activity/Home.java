package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.projects.android.MyNotes.R;
import com.github.clans.fab.FloatingActionButton;
import com.projects.android.MyNotes.adapter.CardAdapter;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public RecyclerView recyclerView;
    public CardAdapter cardAdapter;
    //public RowAdapter rowAdapter;
    public List<Data> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);
        list = new ArrayList<>();
        cardAdapter = new CardAdapter(this, list);
        //rowAdapter = new RowAdapter(this, list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

        prepareAlbums();
        recyclerView.addOnItemTouchListener(new RecyclerTouch(getApplicationContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Data data = list.get(position);
                Intent i=new Intent(getApplicationContext(), Notes.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    private void prepareAlbums() {
        Data a = new Data("MEAN stands for: empowering businesses to be more agile and scalable.","kldsl","7/6/8");
        list.add(a);

        a = new Data("Xscpae", "8dagvasa","6/7/8");
        list.add(a);

        a = new Data("Maroon 5", "11, covers[2]","6/7/8");
        list.add(a);

        a = new Data("Born to Die empowering businesses to be more agile and s", " covers[3]","6/7/8");
        list.add(a);

        a = new Data("Honeymoonempowering businesses to be more agile and s","covers[4]","6/7/8");
        list.add(a);

        a = new Data("I Need a Doctor","5","6/7/8");
        list.add(a);

        a = new Data("Loud", "11","6/7/8");
        list.add(a);

        a = new Data("Legend", "14","6/7/8");
        list.add(a);

        a = new Data("Hello", "11","6/7/8");
        list.add(a);

        a = new Data("Greatest Hits empowering businesses to be more agile and s", "17","6/7/8");
        list.add(a);

        cardAdapter.notifyDataSetChanged();
    }

}
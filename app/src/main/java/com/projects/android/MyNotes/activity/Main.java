package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.github.clans.fab.FloatingActionButton;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;
import com.projects.android.MyNotes.fragment.Home;
import com.projects.android.MyNotes.fragment.Notebooks;
import com.projects.android.MyNotes.fragment.Trash;
import com.projects.android.MyNotes.listener.FragmentInteraction;

import butterknife.OnClick;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentInteraction {
    private NavigationView navigationView;
    private View navHeader;
    private ImageView imgProfile;
    LinearLayout bottomSheet;
    Dbhelper2 dbhelper2;
    DbHelper help;
    SQLiteDatabase db,db2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbhelper2=new Dbhelper2(getApplicationContext());
        help=new DbHelper(getApplicationContext());
        db2=help.getReadableDatabase();
        db=dbhelper2.getWritableDatabase();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        imgProfile = (ImageView) navHeader.findViewById(R.id.nav_image);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Account.class);
                startActivity(i);
            }
        });

        final FloatingActionButton camera=(FloatingActionButton)findViewById(R.id.menu_item);
        final FloatingActionButton audio=(FloatingActionButton)findViewById(R.id.menu_item2);
        final FloatingActionButton attachment=(FloatingActionButton)findViewById(R.id.menu_item3);
        final FloatingActionButton text=(FloatingActionButton)findViewById(R.id.menu_item4);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text();
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

        displayFragment(R.id.nav_notes); //To display the notes(home) fragment at on create.
    }

    public void text(){
        Intent i=new Intent(Main.this,Notes.class);
        startActivity(i);
    }
    public void attachment(){
        Intent i=new Intent(Main.this,Notes.class);
        startActivity(i);
    }
    public void audio(){
        Intent i=new Intent(Main.this,Notes.class);
        startActivity(i);
    }
    public void camera(){
        Intent i=new Intent(Main.this,Notes.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")   //??
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        displayFragment(item.getItemId());

        if (id == R.id.nav_about) {
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

    private void displayFragment(int itemId){
        Fragment fragment = null;  //Data Abstraction; fragment is the parent class which is called by the constructor of the Home,Notebooks etc.
         switch (itemId) {
            case R.id.nav_notes:
                fragment = new Home(); //Initiate the fragment
                break;
            case R.id.nav_notebooks:
                fragment = new Notebooks();
                break;
            case R.id.nav_trash:
                fragment = new Trash();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    public void editCalled(View view)
    {   try {
        Home home = new Home(); //To fetch the global variable from that class.
        Intent i = new Intent(getApplicationContext(), Notes.class);
        String k1 = "v1";
        i.putExtra(k1, String.valueOf(home.position));   //To pass the position of the selected card across the intent.
        startActivity(i);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit(); //Just coz i like using shared preferences now.
        edit.putBoolean("Update", true);
        edit.apply();
    }
    catch (Exception e)
    {
        Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
    }
    }
    public void previewNote(View view)
    {
        try {
            Home home=new Home();
            Intent i=new Intent(getApplicationContext(), Preview.class);
            String k1="v1";
            i.putExtra(k1,String.valueOf(home.position));   //To pass the position of the selected card across the intent.
            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }
    public void deleteNote(View view)
    {   Home home=new Home();
        int position=home.position;
        Cursor data=help.getAll(db2);
        int i=0;
        if(data.moveToLast())
        {
            while(i<position)
            {
                data.moveToPrevious();
                i++;
            }
        }
        String Title=data.getString(0);
        String content=data.getString(1);
        try {
            dbhelper2.addInfo(Title, content, db);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "DataBase"+String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        help.delete(Title,db2);
        try {
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "DataBase"+String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }
    public void shareNote(View view)
    {

    }
    public void mailNote(View view)
    {

    }
    public void addNote(View view)
    {

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.github.clans.fab.FloatingActionButton;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;
import com.projects.android.MyNotes.fragment.BottomSheetTrash;
import com.projects.android.MyNotes.fragment.Home;
import com.projects.android.MyNotes.fragment.Notebooks;
import com.projects.android.MyNotes.fragment.Trash;
import com.projects.android.MyNotes.helper.Shared_Preferences;
import com.projects.android.MyNotes.listener.OnFragmentInteraction;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteraction {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int REQUEST_AUDIO_ATTACHMENT = 3;
    static final int REQUEST_FILE_ATTACHMENT = 4;
    String photoPath;
    Dbhelper2 dbhelper2;
    DbHelper help;
    SQLiteDatabase db,db2;
    Shared_Preferences shared_preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared_preferences = new Shared_Preferences(getApplicationContext());
        setTheme(shared_preferences.getTheme());
        setContentView(R.layout.activity_home);
        dbhelper2=new Dbhelper2(getApplicationContext());
        help=new DbHelper(getApplicationContext());
        db2=help.getReadableDatabase();
        db=dbhelper2.getWritableDatabase();
        //recreate();

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackground(new ColorDrawable(shared_preferences.get_primaryColor()));

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View navHeader = navigationView.getHeaderView(0);
        final ImageView imageProfile = navHeader.findViewById(R.id.nav_image);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.menu_item5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text();
            }
        });
        findViewById(R.id.menu_item4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachment();
            }
        });
        findViewById(R.id.menu_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio();
            }
        });
        findViewById(R.id.menu_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image();
            }
        });
        findViewById(R.id.menu_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        displayFragment(R.id.nav_notes);
    }

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void image() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK) {
                try{
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    try {
                        Intent intent = new Intent(getApplicationContext(), ImActivity.class);
                        intent.putExtra("ImageURI", imageUri.toString());
                        /*ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                        intent.putExtra("byteArray", bs.toByteArray());*/
                        startActivity(intent);
                    }
                     catch(Exception e)
                     {
                         Toast.makeText(this, String.valueOf(e)+" INTENT", Toast.LENGTH_SHORT).show();
                     }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }

        }else if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK) {
                try {
                    final Bitmap capture = (Bitmap) data.getExtras().get("data");
                    Intent intent = new Intent(getApplicationContext(), ImActivity.class);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    capture.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                    intent.putExtra("byteArray", bs.toByteArray());
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void audio() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,REQUEST_AUDIO_ATTACHMENT);
        }
    }


    public void attachment() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,REQUEST_FILE_ATTACHMENT);
        }
    }


    public void text() {
        Intent intent = new Intent(Main.this, Notes.class);
        intent.putExtra(Intent.EXTRA_TEXT, "Create");
        startActivity(intent);
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
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(int itemId){
        Fragment fragment = null;
         switch (itemId) {
            case R.id.nav_notes:
                fragment = new Home();
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
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE );
            ft.commit();
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
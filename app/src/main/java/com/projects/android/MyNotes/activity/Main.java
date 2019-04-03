package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;
import com.projects.android.MyNotes.fragment.Home;
import com.projects.android.MyNotes.fragment.Notebook;
import com.projects.android.MyNotes.fragment.Trash;
import com.projects.android.MyNotes.helper.Preference;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int REQUEST_AUDIO_ATTACHMENT = 3;
    static final int REQUEST_FILE_ATTACHMENT = 4;
    NavigationView navigationView;
    Toolbar toolbar;
    Dbhelper2 dbhelper2;
    DbHelper help;
    SQLiteDatabase db, db2;
    Preference preference;
    FloatingActionMenu menuButton;
    FloatingActionButton textButton, imageButton, voiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preference = new Preference(getApplicationContext());
        setTheme(preference.getTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper2 = new Dbhelper2(getApplicationContext());
        help = new DbHelper(getApplicationContext());
        db2 = help.getReadableDatabase();
        db = dbhelper2.getWritableDatabase();

        menuButton = findViewById(R.id.menu);
        menuButton.setClosedOnTouchOutside(false);
        textButton = findViewById(R.id.menu_item);
        imageButton = findViewById(R.id.menu_item2);
        voiceButton = findViewById(R.id.menu_item3);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_notes);

        toolbar.setTitle(R.string.notes);
        setSupportActionBar(toolbar);
        setColor();

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image();
            }
        });
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        displayFragment(R.id.nav_notes);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("home");
                if (fragment != null && fragment.isVisible()) {
                    navigationView.setCheckedItem(R.id.nav_notes);
                    toolbar.setTitle(R.string.notes);
                }
                fragment = getSupportFragmentManager().findFragmentByTag("notebook");
                if (fragment != null && fragment.isVisible()) {
                    navigationView.setCheckedItem(R.id.nav_notebooks);
                    toolbar.setTitle(R.string.notebooks);
                }
                fragment = getSupportFragmentManager().findFragmentByTag("trash");
                if (fragment != null && fragment.isVisible()) {
                    navigationView.setCheckedItem(R.id.nav_trash);
                    toolbar.setTitle(R.string.trash);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setColor();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("home");
        if (fragment != null && fragment.isVisible()) {
            navigationView.setCheckedItem(R.id.nav_notes);
        }
        fragment = getSupportFragmentManager().findFragmentByTag("notebook");
        if (fragment != null && fragment.isVisible()) {
            navigationView.setCheckedItem(R.id.nav_notebooks);
        }
        fragment = getSupportFragmentManager().findFragmentByTag("trash");
        if (fragment != null && fragment.isVisible()) {
            navigationView.setCheckedItem(R.id.nav_trash);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    try {
                        Intent intent = new Intent(getApplicationContext(), ImageNote.class);
                        intent.putExtra("ImageURI", imageUri.toString());
                        /*ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                        intent.putExtra("byteArray", bs.toByteArray());*/
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, String.valueOf(e) + " INTENT", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    final Bitmap capture = (Bitmap) data.getExtras().get("data");
                    Intent intent = new Intent(getApplicationContext(), ImageNote.class);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    capture.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                    intent.putExtra("byteArray", bs.toByteArray());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1 || count == 0) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new Home())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayFragment(item.getItemId());
        if (id == R.id.nav_about) {
            Intent i5 = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(i5);
        } else if (id == R.id.nav_settings) {
            Intent i6 = new Intent(getApplicationContext(), Settings.class);
            startActivity(i6);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(int itemId) {
        Fragment fragment = null;
        String TAG = "home";
        switch (itemId) {
            case R.id.nav_notes:
                fragment = new Home();
                break;
            case R.id.nav_notebooks:
                fragment = new Notebook();
                TAG = "notebook";
                break;
            case R.id.nav_trash:
                fragment = new Trash();
                TAG = "trash";
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment, TAG)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void image() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        }
    }

    private void audio() {
        Intent intent = new Intent(getApplicationContext(), NoteVoice.class);
        startActivity(intent);
    }

    private void attachment() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_FILE_ATTACHMENT);
        }
    }

    private void text() {
        Intent intent = new Intent(Main.this, NoteText.class);
        intent.putExtra(Intent.EXTRA_TEXT, "Create");
        startActivity(intent);
    }

    private void setColor() {
        menuButton.setMenuButtonColorNormal(preference.get_accentColor());
        menuButton.setMenuButtonColorPressed(preference.get_primaryColor());
        textButton.setColorNormal(preference.get_accentColor());
        textButton.setColorPressed(preference.get_primaryColor());
        imageButton.setColorNormal(preference.get_accentColor());
        imageButton.setColorPressed(preference.get_primaryColor());
        voiceButton.setColorNormal(preference.get_accentColor());
        voiceButton.setColorPressed(preference.get_primaryColor());
        toolbar.setBackground(new ColorDrawable(preference.get_primaryColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(preference.get_primaryDarkColor());
        }
    }

}

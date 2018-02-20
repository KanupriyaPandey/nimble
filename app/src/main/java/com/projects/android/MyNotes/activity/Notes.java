package com.projects.android.MyNotes.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.projects.android.MyNotes.R;

public class Notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        BottomNavigationView bottomNavigation=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_undo:
                        break;
                    case R.id.navigation_redo:
                        Toast.makeText(getApplicationContext(), "Redo clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_attach:
                        break;
                    case R.id.navigation_background:
                        break;
                }
                return true;
            }
        });

    }
}
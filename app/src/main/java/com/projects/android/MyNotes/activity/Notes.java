package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.fragment.Home;

public class Notes extends AppCompatActivity {
    EditText notes;
    DbHelper help;
    SQLiteDatabase db;
    SQLiteDatabase dbRead;
    int position;
    String primary_title;
    int id;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
            help = new DbHelper(getApplicationContext());
            db = help.getWritableDatabase();
            dbRead = help.getReadableDatabase();
        notes = (EditText) findViewById(R.id.note_content);
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (preferences.contains("Update")) {
            Intent i = getIntent();
            String k1 = "v1";
            position = Integer.parseInt(i.getStringExtra(k1));
            setData(position);
        }
    }
    public void setData(int position)
    {
        Cursor data=help.getAll(dbRead);
        int i=0;
        if(data.moveToLast())
        {
            while(i<position)
            {
                data.moveToPrevious();
                i++;
            }
        }
        primary_title=data.getString(0);
        id=data.getInt(3);
        String Contents=data.getString(0)+"\n"+data.getString(1);
        notes.setText(Contents);
    }
    public void updateNote()    //Updating the saved notes
    {
        String Content = notes.getText().toString();
        if (Content.equals("")) {
            super.onBackPressed();
        }
        else
        {
            String Title = "";
            String content = "";
            int p = 0;
            do {
                if (Content.charAt(p) == '\n') {
                    p++;
                    do {
                        content += Content.charAt(p);
                        p++;
                    }
                    while (p < Content.length());
                } else {
                    Title = Title + Content.charAt(p);
                }
                p++;
            }
            while (p < Content.length());
            try {
                int success = help.updateNote(String.valueOf(id),Title, content, db);
                if (success > 0) {
                    Toast.makeText(getApplicationContext(), primary_title+" Update Success", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
        }
    }
    @Override
    public void onBackPressed() {
        if (preferences.contains("Update")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("Update");
            editor.apply();
            updateNote();
        } else {
            String Content = notes.getText().toString();
            if (Content.equals("")) {
                super.onBackPressed();
            } else {
                String Title = "";
                String content = "";
                int p = 0;
                do {
                    if (Content.charAt(p) == '\n') {
                        p++;
                        do {
                            content += Content.charAt(p);
                            p++;
                        }
                        while (p < Content.length());
                    } else {
                        Title = Title + Content.charAt(p);
                    }
                    p++;
                }
                while (p < Content.length());
                help.addInfo(Title, content, db);
                try {
                    Intent i = new Intent(getApplicationContext(), Main.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(this, "DataBase" + String.valueOf(e), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
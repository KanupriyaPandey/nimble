package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.fragment.BackgroundSheet;
import com.projects.android.MyNotes.fragment.Home;

public class Notes extends AppCompatActivity {
    EditText editNote;
    TextView displayNote, date, done, attachment;
    String attachmentText;
    RelativeLayout rv;
    DbHelper help;
    SQLiteDatabase db;
    SQLiteDatabase dbRead;
    public static int position;
    String primary_title, contents;
    int id;
    SharedPreferences preferences;
    String k = "v2";
    Intent intent;
    int resource;
    Drawable default_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        help = new DbHelper(getApplicationContext());
        db = help.getWritableDatabase();
        dbRead = help.getReadableDatabase();
        editNote = (EditText) findViewById(R.id.editNote);
        displayNote = (TextView) findViewById(R.id.displayNote);
        attachment = (TextView) findViewById(R.id.file_attachment);
        date = (TextView) findViewById(R.id.upDate);
        done = (TextView) findViewById(R.id.upDone);
        rv = findViewById(R.id.notes_layout);
        intent=getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String extra = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (extra.equalsIgnoreCase("Create")) {
                try {
                    displayNote.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                    date.setVisibility(View.GONE);
                    default_background=editNote.getBackground();
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            } else if (extra.equalsIgnoreCase("Preview")) {
                String k1 = "v1";
                date.setVisibility(View.VISIBLE);
                position = Integer.parseInt(intent.getStringExtra(k1));
                editNote.setVisibility(View.GONE);
                displayNote.setVisibility(View.VISIBLE);
                try {
                    setData(position);
                } catch (Exception e) {
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
                }
                displayNote.setText(contents);
                displayNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displayNote.setVisibility(View.GONE);
                        editNote.setVisibility(View.VISIBLE);
                        done.setVisibility(View.VISIBLE);
                        editNote.setText(contents);
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }else if ((extra.equalsIgnoreCase("Capture"))||(extra.equalsIgnoreCase("Image"))) {
                attachmentText="Image Attached";
                attachment.setText(attachmentText);
                attachment.setVisibility(View.VISIBLE);
            }else if ((extra.equalsIgnoreCase("Audio"))||(extra.equalsIgnoreCase("Attachment"))) {
                attachmentText="File Attached";
                attachment.setText(attachmentText);
                attachment.setVisibility(View.VISIBLE);
            }
        }
        if(intent.hasExtra(k)) {
            resource = Integer.parseInt(intent.getStringExtra(k));
            rv.setBackgroundResource(resource);
        }
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
                        dataAdd();
                        new BackgroundSheet().show(getSupportFragmentManager(), "BackgroundSheet");
                        break;
                }
                return true;
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
        if(data.getString(1).length()<=1)
            contents=data.getString(0)+"\n";
        else
            contents=data.getString(0)+"\n"+data.getString(1);
        editNote.setText(contents);
        String date_time[]=data.getString(2).split(" ");
        date.setText(date_time[0]);
        resource=Integer.parseInt(data.getString(5));
        if(Integer.parseInt(data.getString(5))!=0)
        {
            rv.setBackgroundResource(resource);
        }
    }
    public void updateNote()    //Updating the saved notes
    {
        String Content = editNote.getText().toString();
        if (Content.equals("")&&editNote.getVisibility()==View.VISIBLE) {
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
        }
        else
        {
            try {
                    dataUpdate();
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
        if(intent.getStringExtra(Intent.EXTRA_TEXT).equalsIgnoreCase("Preview"))
        {
            updateNote();
        }
        else
            {
                dataAdd();
                try {
                    Intent i = new Intent(getApplicationContext(), Main.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(this, "DataBase" + String.valueOf(e), Toast.LENGTH_LONG).show();
                }
            }
        }
    public void dataAdd() {
        if (editNote.getVisibility() == View.VISIBLE) {
            if (intent.getStringExtra(Intent.EXTRA_TEXT).equalsIgnoreCase("Preview")) {
                dataUpdate();
            } else {
                String Content = editNote.getText().toString();
                if (Content.equals("")) {
                    Toast.makeText(this, "Sorry! You can not save or edit an empty text space.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Main.class);
                    startActivity(i);
                } else {
                    Content += "\n";
                    String[] split = Content.split("\n", 2);
                    help.addInfo(split[0], split[1], String.valueOf(resource), db);
                }
            }
        }
    }
    public void dataUpdate() {
        String Content = editNote.getText().toString();
        if (Content.equals("") && editNote.getVisibility() == View.VISIBLE) {
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
        } else {
            String[] split = Content.split("\n", 2);
            try {
                    help.updateNote(String.valueOf(id), split[0], split[1], String.valueOf(resource), db);
            } catch (Exception e) {
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showAttachment(View view) {

    }
}

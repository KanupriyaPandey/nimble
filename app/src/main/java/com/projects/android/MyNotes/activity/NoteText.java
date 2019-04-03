package com.projects.android.MyNotes.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.fragment.BackgroundSheet;
import com.projects.android.MyNotes.helper.Preference;

import java.util.ArrayList;
import java.util.Locale;

public class NoteText extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    public static int position;
    RelativeLayout relativeLayout;
    TextView displayNote, date, done;
    EditText editNote;
    DbHelper help;
    SQLiteDatabase db;
    SQLiteDatabase dbRead;
    String primary_title, contents, k = "v2";
    int id, resource;
    Intent intent;
    Drawable default_background;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        preference = new Preference(getApplicationContext());
        help = new DbHelper(getApplicationContext());
        db = help.getWritableDatabase();
        dbRead = help.getReadableDatabase();
        editNote = findViewById(R.id.editNote);
        displayNote = findViewById(R.id.displayNote);
        date = findViewById(R.id.upDate);
        done = findViewById(R.id.upDone);
        relativeLayout = findViewById(R.id.notes_layout);

        Toolbar toolbar = findViewById(R.id.notes_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        findViewById(R.id.microphone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String extra = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (extra.equalsIgnoreCase("Create")) {
                try {
                    displayNote.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                    date.setVisibility(View.GONE);
                    default_background = editNote.getBackground();
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            } else if (extra.equalsIgnoreCase("Preview")) {
                String k1 = "v1";
                //v1
                date.setVisibility(View.VISIBLE);
                position = Integer.parseInt(intent.getStringExtra(k1));
                editNote.setVisibility(View.GONE);
                displayNote.setVisibility(View.VISIBLE);
                try {
                    setData(position);
                } catch (Exception e) {
                    Toast.makeText(this, String.valueOf(e) + "SetData", Toast.LENGTH_LONG).show();
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
            }
        }

        if (intent.hasExtra(k)) {
            resource = Integer.parseInt(intent.getStringExtra(k));
            relativeLayout.setBackgroundResource(resource);
        }

        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_notes);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_attach:
                        break;
                    case R.id.navigation_background:
                        dataAdd();
                        new BackgroundSheet().show(getSupportFragmentManager(), "BackgroundSheet");
                        break;
                    case R.id.navigation_text:
                        //new BottomSheetText().show(getSupportFragmentManager(), "BottomSheetText");
                        break;
                    case R.id.navigation_customize:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (intent.getStringExtra(Intent.EXTRA_TEXT).equalsIgnoreCase("Preview")) {
            updateNote();
        } else {
            dataAdd();
            try {
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(this, "DataBase" + String.valueOf(e), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setData(int position) {
        Cursor data = help.getAll(dbRead);
        Cursor img = help.getImg(dbRead);
        img.moveToLast();
        int i = 0;
        if (data.moveToLast()) {
            while (i < position) {
                data.moveToPrevious();
                img.moveToPrevious();
                i++;
            }
        }
        if (img.getBlob(0) != null) {
            Intent intent = new Intent(getApplicationContext(), ImageNote.class);
            intent.putExtra("byteArrayPreview", img.getBlob(0));
            intent.putExtra(Intent.EXTRA_TEXT, "PREVIEW");
            startActivity(intent);
        } else {
            primary_title = data.getString(0);
            id = data.getInt(3);
            if (data.getString(1).length() <= 1)
                contents = data.getString(0) + "\n";
            else
                contents = data.getString(0) + "\n" + data.getString(1);
            editNote.setText(contents);
            String date_time[] = data.getString(2).split(" ");
            date.setText(date_time[0]);
            resource = Integer.parseInt(data.getString(5));
            if (Integer.parseInt(data.getString(5)) != 0) {
                relativeLayout.setBackgroundResource(resource);
            }
        }
    }

    public void updateNote() {
        String Content = editNote.getText().toString();
        if (Content.equals("") && editNote.getVisibility() == View.VISIBLE) {
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
        } else {
            try {
                dataUpdate();
            } catch (Exception e) {
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
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
                    //Background
                    Intent i = new Intent(getApplicationContext(), Main.class);
                    startActivity(i);
                } else {
                    Content += "\n";
                    String[] split = Content.split("\n", 2);
                    help.addInfo(split[0], null, split[1], String.valueOf(resource), db);
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

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    //Update content(voice)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (editNote.getVisibility() == View.VISIBLE){
                        editNote.setText(result.get(0));
                    }else if (displayNote.getVisibility() == View.VISIBLE){
                        editNote.setText(result.get(0));
                    }
                }
                break;
            }
        }
    }

}

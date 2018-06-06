package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.fragment.AccentPicker;
import com.projects.android.MyNotes.fragment.Home;
import com.projects.android.MyNotes.fragment.PrimaryPicker;
import com.projects.android.MyNotes.fragment.ThemeDialog;

public class Settings extends AppCompatActivity {
    Home home;
    RadioButton grid,list;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home = new Home();
        setContentView(R.layout.activity_settings);
        grid =  findViewById(R.id.radio_grid);
        list =  findViewById(R.id.radio_list);
        toolbar=findViewById(R.id.toolbar_settings);

        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrid();
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyList();
            }
        });

        findViewById(R.id.base_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThemeDialog();
            }
        });

        findViewById(R.id.primary_title).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaletteDialog();
            }
        });

        findViewById(R.id.accent_title).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccentDialog();
            }
        });
    }

    public void setCheckedRadio() {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        if(sharedPreferences.contains("checkbox_1")&&sharedPreferences.contains("checkbox_2")) {
            grid.setChecked(sharedPreferences.getBoolean("checkbox_1", false));
            list.setChecked(sharedPreferences.getBoolean("checkbox_2", false));
        }
    }

    public void applyGrid() {
        boolean checked = grid.isChecked();
        if (checked) {
            try {
                editor.putBoolean("checkbox_1", grid.isChecked());
                editor.putBoolean("checkbox_2", false);
                editor.apply();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("k", "Grid"); //Changing the value of the Shared Preference
                editor.apply();
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void applyList() {
        boolean checked=list.isChecked();
        if(checked)        {
            try{
                editor.putBoolean("checkbox_2", list.isChecked());
                editor.putBoolean("checkbox_1",false);
                editor.apply();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("k","List");   //Changing the value of the Shared Preference
                editor.apply();
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), "LIST", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showPaletteDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PrimaryPicker primaryPicker = PrimaryPicker.newInstance();
        primaryPicker.show(fragmentManager, "Primary");
    }

    private void showAccentDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AccentPicker accent = AccentPicker.newInstance();
        accent.show(fragmentManager, "Accent");
    }


    private void showThemeDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ThemeDialog themeDialog = ThemeDialog.newInstance();
        themeDialog.show(fragmentManager, "Accent");
    }
}

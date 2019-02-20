package com.projects.android.MyNotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.fragment.AccentPicker;
import com.projects.android.MyNotes.fragment.Home;
import com.projects.android.MyNotes.fragment.PrimaryPicker;
import com.projects.android.MyNotes.fragment.ThemeDialog;
import com.projects.android.MyNotes.helper.Shared_Preferences;

public class Settings extends AppCompatActivity {
    RadioButton grid,list;
    Toolbar toolbar;
    Shared_Preferences shared_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared_preferences=new Shared_Preferences(getApplicationContext());
        setTheme(shared_preferences.getTheme());
        setContentView(R.layout.activity_settings);
        grid =  findViewById(R.id.radio_grid);
        list =  findViewById(R.id.radio_list);
        toolbar=findViewById(R.id.toolbar_settings);
        toolbar.setBackground(new ColorDrawable(shared_preferences.get_primaryColor()));
        setCheckedRadio();
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

        findViewById(R.id.base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThemeDialog();
            }
        });

        findViewById(R.id.primary).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaletteDialog();
            }
        });

        findViewById(R.id.accent).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccentDialog();
            }
        });
    }

    public void setCheckedRadio() {
        shared_preferences.setCheckbox(grid, list);
    }

    public void applyGrid() {
        boolean checked = grid.isChecked();
        if (checked) {
            try {
                shared_preferences.apply_grid();
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
                shared_preferences.apply_list();
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
        ThemeDialog themeDialog = ThemeDialog.newInstance(this);
        themeDialog.show(fragmentManager, "Theme");
    }
}

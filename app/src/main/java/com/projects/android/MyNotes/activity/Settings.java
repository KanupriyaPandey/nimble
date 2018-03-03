package com.projects.android.MyNotes.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.fragment.Home;

public class Settings extends AppCompatActivity {
    Home home;
    RadioButton grid,list;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor; //to store the radio buttons state
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home = new Home();
        setContentView(R.layout.activity_settings);
        grid=(RadioButton)findViewById(R.id.radio_grid);
        list=(RadioButton)findViewById(R.id.radio_list);
        setCheckedRadio();
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrid(); //To change the Layout to grid
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyList(); //To change the layout to List
            }
        });

    }
    public void setCheckedRadio()
    {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shEditor = sharedPreferences.edit();
        if(sharedPreferences.contains("checkbox_1")&&sharedPreferences.contains("checkbox_2")) {
            grid.setChecked(sharedPreferences.getBoolean("checkbox_1", false));
            list.setChecked(sharedPreferences.getBoolean("checkbox_2", false));
        }
    }
    public void applyGrid() {
        boolean checked = grid.isChecked();
        if (checked) {
            try {
                shEditor.putBoolean("checkbox_1", grid.isChecked());
                shEditor.putBoolean("checkbox_2", false);
                shEditor.apply();
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
    public void applyList()
    {
        boolean checked=list.isChecked();
        if(checked)
        {
            try{
                shEditor.putBoolean("checkbox_2", list.isChecked());
                shEditor.putBoolean("checkbox_1",false);
                shEditor.apply();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("k","List");   //Changing the value of the Shared Preference
                editor.apply();
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "LIST", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

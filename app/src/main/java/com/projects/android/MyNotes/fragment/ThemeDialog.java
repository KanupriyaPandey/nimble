package com.projects.android.MyNotes.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Settings;
import com.projects.android.MyNotes.helper.Shared_Preferences;

import java.util.Calendar;

public class ThemeDialog extends DialogFragment {
    int themeValue = 1;
    TextView dialogTitle;
    Shared_Preferences shared_preferences;
    Settings settings;
    public static Activity activity;

    public ThemeDialog() {
    }

    public static ThemeDialog newInstance(Activity activity) {
        ThemeDialog.activity=activity;
        return new ThemeDialog();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_theme, null);
        settings = new Settings();
        shared_preferences = new Shared_Preferences(getContext());
        dialogTitle = view.findViewById(R.id.theme_dialog);
        dialogTitle.setBackgroundColor(shared_preferences.get_primaryColor());
        shared_preferences.setCheckbox2((RadioButton)view.findViewById(R.id.light_theme), (RadioButton)view.findViewById(R.id.dark_theme));
        view.findViewById(R.id.light_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeValue = 1;
            }
        });


        view.findViewById(R.id.dark_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeValue = 2;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shared_preferences.setTheme(themeValue);
                        //activity.recreate();
                        Toast.makeText(getContext(), "Updating the theme", Toast.LENGTH_SHORT).show();
                        restartSelf();

                        /*try {
                            settings.implementTheme(shared_preferences);
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }
    private void restartSelf() {
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        getActivity().finish();
        startActivity(i);
    }
}

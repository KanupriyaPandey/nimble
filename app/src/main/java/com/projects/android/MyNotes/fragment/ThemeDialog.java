package com.projects.android.MyNotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.projects.android.MyNotes.R;

public class ThemeDialog extends DialogFragment {
    int themeValue = 1;
    TextView dialogTitle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ThemeDialog() {
    }

    public static ThemeDialog newInstance() {
        return new ThemeDialog();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_theme, null);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        dialogTitle = view.findViewById(R.id.theme_dialog);
        dialogTitle.setBackgroundColor(sharedPreferences.getInt("primary",R.color.colorPrimary));
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
                        if(themeValue==1) {
                            editor.putInt("theme", R.style.Theme).apply();
                        }else if(themeValue==2){
                            editor.putInt("theme", R.style.ThemeDark).apply();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }
}

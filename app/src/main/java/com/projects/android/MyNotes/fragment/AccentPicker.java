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

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class AccentPicker extends DialogFragment {
    LineColorPicker picker;
    int[] array;
    int colorMain, index;
    TextView dialogTitle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AccentPicker() {
    }

    public static AccentPicker newInstance() {
        return new AccentPicker();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_accent, null);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        dialogTitle = view.findViewById(R.id.accent_title);

        picker = view.findViewById(R.id.accent);
        array = view.getResources().getIntArray(R.array.main);
        picker.setColors(array);
        index=sharedPreferences.getInt("index", 3);
        picker.setSelectedColor(array[index]);
        dialogTitle.setBackgroundColor(sharedPreferences.getInt("accent", array[3]));

        picker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                colorMain = picker.getColor();
                dialogTitle.setBackgroundColor(colorMain);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt("accent", colorMain).apply();
                        index=getArrayIndex(array, colorMain);
                        editor.putInt("index", index).apply();
                        picker.setSelectedColor(array[index]);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

    private int getArrayIndex(int[] arr, int value) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

}



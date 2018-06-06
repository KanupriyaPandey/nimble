package com.projects.android.MyNotes.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.projects.android.MyNotes.R;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class PrimaryPicker extends DialogFragment {
    LineColorPicker pickerMain, pickerShade;
    int[] arrayMain, arrayShade;
    int colorMain, colorMainDark, color, indexMain, indexShade;
    TextView dialogTitle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public PrimaryPicker() {
    }

    public static PrimaryPicker newInstance() {
        return new PrimaryPicker();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_primary, null);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        dialogTitle = view.findViewById(R.id.primary_title);
        pickerMain = view.findViewById(R.id.primary_main);
        pickerShade = view.findViewById(R.id.primary);

        arrayMain = view.getResources().getIntArray(R.array.main);
        arrayShade = view.getResources().getIntArray(R.array.pink);
        pickerMain.setColors(arrayMain);
        showShade(sharedPreferences.getInt("primary_main",arrayMain[1]));

        indexMain = sharedPreferences.getInt("main_index", 1);
        indexShade = sharedPreferences.getInt("shade_index", 4);
        pickerMain.setSelectedColor(arrayMain[indexMain]);
        pickerShade.setSelectedColor(arrayShade[indexShade]);
        dialogTitle.setBackgroundColor(sharedPreferences.getInt("primary", arrayShade[4]));

        pickerMain.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                colorMain = pickerMain.getColor();
                showShade(colorMain);
            }
        });

        pickerShade.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                color = pickerShade.getColor();
                colorMainDark = getDarkerColor(color);
                dialogTitle.setBackgroundColor(color);

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt("primary", color).apply();
                        editor.putInt("primary_dark", colorMainDark).apply();
                        editor.putInt("primary_main", colorMain).apply();
                        editor.putInt("main_index", getArrayIndex(arrayMain, colorMain)).apply();
                        editor.putInt("shade_index", getArrayIndex(arrayShade, color)).apply();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

    private void showShade(int value) {
        if (value == arrayMain[0]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.red);
        } else if (value == arrayMain[1]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.pink);
        } else if (value == arrayMain[2]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.purple);
        } else if (value == arrayMain[3]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.deep_purple);
        } else if (value == arrayMain[4]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.indigo);
        } else if (value == arrayMain[5]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.blue);
        } else if (value == arrayMain[6]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.light_blue);
        } else if (value == arrayMain[7]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.cyan);
        } else if (value == arrayMain[8]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.teal);
        } else if (value == arrayMain[9]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.green);
        } else if (value == arrayMain[10]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.light_green);
        } else if (value == arrayMain[11]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.lime);
        } else if (value == arrayMain[12]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.yellow);
        } else if (value == arrayMain[13]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.amber);
        } else if (value == arrayMain[14]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.orange);
        } else if (value == arrayMain[15]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.deep_orange);
        } else if (value == arrayMain[16]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.brown);
        } else if (value == arrayMain[17]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.blue_grey);
        } else if (value == arrayMain[18]) {
            arrayShade = getActivity().getResources().getIntArray(R.array.grey);
        }
        pickerShade.setColors(arrayShade);
        pickerShade.setSelectedColor(arrayShade[4]);
    }

    private int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.72f;
        return Color.HSVToColor(hsv);
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

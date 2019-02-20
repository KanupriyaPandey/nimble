package com.projects.android.MyNotes.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Shared_Preferences;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class PrimaryPicker extends DialogFragment {
    LineColorPicker pickerMain, pickerShade;
    int[] arrayMain, arrayShade;
    int colorMain, colorMainDark, color, indexMain, indexShade;
    TextView dialogTitle;
    Shared_Preferences shared_preferences;

    public PrimaryPicker() {
    }

    public static PrimaryPicker newInstance() {
        return new PrimaryPicker();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        shared_preferences = new Shared_Preferences((getContext()));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_primary, null);
        dialogTitle = view.findViewById(R.id.primary_title);
        pickerMain = view.findViewById(R.id.primary_main);
        pickerShade = view.findViewById(R.id.primary);
        arrayMain = view.getResources().getIntArray(R.array.main);
        arrayShade = view.getResources().getIntArray(R.array.pink);
        pickerMain.setColors(arrayMain);
        showShade(shared_preferences.get_primaryColor());
        indexMain = shared_preferences.get_primaryIndex();
        indexShade = shared_preferences.get_shadeIndex();
        pickerMain.setSelectedColor(arrayMain[indexMain]);
        pickerShade.setSelectedColor(arrayShade[indexShade]);
        dialogTitle.setBackgroundColor(shared_preferences.get_shadeColor());

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
                        try {
                            int shadeIndex = getArrayIndex(arrayShade, color);
                            int mainIndex = getArrayIndex(arrayMain, colorMain);
                            shared_preferences.setColorPrimary(color, colorMain, colorMainDark, shadeIndex, mainIndex);
                            Toast.makeText(getActivity(), "Updating the PrimaryColor", Toast.LENGTH_SHORT).show();
                            restartSelf();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                        }
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
    private void restartSelf() {
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        getActivity().finish();
        startActivity(i);
    }
}

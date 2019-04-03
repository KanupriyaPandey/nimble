package com.projects.android.MyNotes.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Preference;
import com.projects.android.MyNotes.listener.FragmentInteraction;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class PickerPrimary extends DialogFragment implements FragmentInteraction {
    int[] arrayMain, arrayShade;
    int colorMain, colorMainDark, color, indexMain, indexShade;
    private FragmentInteraction fragmentInteraction;
    LineColorPicker pickerMain, pickerShade;
    TextView dialogTitle;
    Preference preference;

    public PickerPrimary() {
    }

    public static PickerPrimary newInstance() {
        return new PickerPrimary();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        fragmentInteraction.setPrimary(getActivity(), preference.get_primaryColor(), preference.get_primaryDarkColor());
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_primary, null);
        preference = new Preference((getContext()));

        dialogTitle = view.findViewById(R.id.primary_title);
        pickerMain = view.findViewById(R.id.primary_main);
        pickerShade = view.findViewById(R.id.primary);
        arrayMain = view.getResources().getIntArray(R.array.main);
        arrayShade = view.getResources().getIntArray(R.array.pink);
        pickerMain.setColors(arrayMain);
        showShade(preference.get_primaryColor());
        indexMain = preference.get_primaryIndex();
        indexShade = preference.get_shadeIndex();
        pickerMain.setSelectedColor(arrayMain[indexMain]);
        pickerShade.setSelectedColor(arrayShade[indexShade]);
        dialogTitle.setBackgroundColor(preference.get_shadeColor());

        pickerMain.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                colorMain = pickerMain.getColor();
                indexMain = preference.get_primaryIndex();
                showShade(colorMain);
            }
        });

        pickerShade.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                color = pickerShade.getColor();
                colorMainDark = getDarkerColor(color);
                dialogTitle.setBackgroundColor(color);
                fragmentInteraction.setPrimary(getActivity(), colorMain, colorMainDark);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        colorMain = pickerMain.getColor();
                        color = pickerShade.getColor();
                        colorMainDark = getDarkerColor(color);
                        indexMain = getArrayIndex(arrayMain, colorMain);
                        indexShade = getArrayIndex(arrayShade, color);
                        preference.setColorPrimary(color, colorMain, colorMainDark, indexMain, indexShade);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fragmentInteraction.setPrimary(getActivity(), preference.get_primaryColor(), preference.get_primaryDarkColor());
                    }
                }).create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentInteraction = (FragmentInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteraction = null;
    }


    @Override
    public void setPrimary(Activity activity, int color, int colorDark) {
        activity.getActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(colorDark);
        }
    }

    @Override
    public void setAccent(int color) {
    }

    @Override
    public void setTheme(Activity activity, int theme) {
    }

    @Override
    public void setValue(Activity activity) {
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

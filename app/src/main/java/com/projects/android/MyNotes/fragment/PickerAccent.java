package com.projects.android.MyNotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Preference;
import com.projects.android.MyNotes.listener.FragmentInteraction;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class PickerAccent extends DialogFragment {
    int[] array;
    int colorMain, index;
    private FragmentInteraction fragmentInteraction;
    LineColorPicker picker;
    TextView dialogTitle;
    Preference preference;

    public PickerAccent() {
    }

    public static PickerAccent newInstance() {
        return new PickerAccent();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        fragmentInteraction.setAccent(preference.get_accentColor());
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_accent, null);
        preference = new Preference(getContext());

        dialogTitle = view.findViewById(R.id.accent_title);
        picker = view.findViewById(R.id.accent);
        array = view.getResources().getIntArray(R.array.main);
        picker.setColors(array);
        index = preference.get_accentIndex();
        picker.setSelectedColor(array[index]);
        dialogTitle.setBackgroundColor(preference.get_accentColor());

        picker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                colorMain = picker.getColor();
                dialogTitle.setBackgroundColor(colorMain);
                fragmentInteraction.setAccent(colorMain);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        colorMain = picker.getColor();
                        index = getArrayIndex(array, colorMain);
                        preference.setColorAccent(colorMain, index);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fragmentInteraction.setAccent(preference.get_accentColor());
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

    private int getArrayIndex(int[] arr, int value) {
        int index = 6;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

}



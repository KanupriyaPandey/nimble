package com.projects.android.MyNotes.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Preference;
import com.projects.android.MyNotes.listener.FragmentInteraction;

public class DialogDefault extends DialogFragment implements FragmentInteraction {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private FragmentInteraction fragmentInteraction;
    Preference preference;

    public static Activity activity;

    public DialogDefault() {
    }

    public static DialogDefault newInstance(Activity activity) {
        DialogDefault.activity = activity;
        return new DialogDefault();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_default, null);
        preference = new Preference(getContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // resetPreferences();
                        fragmentInteraction.setValue(getActivity());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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

    public void resetPreferences() {
        editor = sharedPreferences.edit()
                .putString("View", "Grid")
                .putBoolean("checkbox_1", true)
                .putBoolean("checkbox_2", false)
                .putInt("primary", R.color.colorPrimary)
                .putInt("primary_main", R.color.colorPrimary)
                .putInt("primary_dark", R.color.colorPrimaryDark)
                .putInt("main_index", 1)
                .putInt("shade_index", 4)
                .putInt("accent", R.color.colorAccent)
                .putInt("index", 6)
                .putInt("theme", R.style.Theme)
                .putBoolean("checkbox_1.1", true)
                .putBoolean("checkbox_2.1", false);
        editor.apply();
    }

    @Override
    public void setPrimary(Activity activity, int color, int colorDark) {
    }

    @Override
    public void setAccent(int color) {
    }

    @Override
    public void setTheme(Activity activity, int theme) {
    }

    @Override
    public void setValue(Activity activity) {
        editor = sharedPreferences.edit()
                .putString("View", "Grid")
                .putBoolean("checkbox_1", true)
                .putBoolean("checkbox_2", false)
                .putInt("primary", R.color.colorPrimary)
                .putInt("primary_main", R.color.colorPrimary)
                .putInt("primary_dark", R.color.colorPrimaryDark)
                .putInt("main_index", 1)
                .putInt("shade_index", 4)
                .putInt("accent", R.color.colorAccent)
                .putInt("index", 6)
                .putInt("theme", R.style.Theme)
                .putBoolean("checkbox_1.1", true)
                .putBoolean("checkbox_2.1", false);
        editor.apply();
    }

}

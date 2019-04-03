package com.projects.android.MyNotes.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Settings;
import com.projects.android.MyNotes.helper.Preference;
import com.projects.android.MyNotes.listener.FragmentInteraction;

public class DialogTheme extends DialogFragment implements FragmentInteraction {
    int themeValue = 1;
    private FragmentInteraction fragmentInteraction;
    TextView dialogTitle;
    Preference preference;
    Settings settings;

    public static Activity activity;

    public DialogTheme() {
    }

    public static DialogTheme newInstance(Activity activity) {
        DialogTheme.activity = activity;
        return new DialogTheme();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_theme, null);
        settings = new Settings();
        preference = new Preference(getContext());
        dialogTitle = view.findViewById(R.id.theme_dialog);
        dialogTitle.setBackgroundColor(preference.get_primaryColor());
        preference.setCheckbox2((RadioButton) view.findViewById(R.id.light_theme), (RadioButton) view.findViewById(R.id.dark_theme));
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
                        preference.setTheme(themeValue);
                        fragmentInteraction.setTheme(getActivity(), themeValue);
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

    }
}

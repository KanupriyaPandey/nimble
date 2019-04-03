package com.projects.android.MyNotes.listener;

import android.app.Activity;

/* Implemented by Settings */
public interface FragmentInteraction {

    void setPrimary(Activity activity, int color, int colorDark);

    void setAccent(int color);

    void setTheme(Activity activity, int theme);

    void setValue(Activity activity);
}

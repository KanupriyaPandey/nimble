package com.projects.android.MyNotes.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RadioButton;

import com.projects.android.MyNotes.R;

public class Preference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Preference(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public void check_layout() {
        editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("View")) {
            editor.putString("View", "Grid")
                    .apply();
        }
    }

    public String return_layout() {
        return sharedPreferences.getString("View", "");
    }

    public void setCheckbox(RadioButton grid, RadioButton list) {
        if (sharedPreferences.contains("checkbox_1") && sharedPreferences.contains("checkbox_2")) {
            grid.setChecked(sharedPreferences.getBoolean("checkbox_1", false));
            list.setChecked(sharedPreferences.getBoolean("checkbox_2", false));
        }
    }

    public void setCheckbox2(RadioButton Light, RadioButton Dark) {
        if (sharedPreferences.contains("checkbox_1.1") && sharedPreferences.contains("checkbox_2.1")) {
            Light.setChecked(sharedPreferences.getBoolean("checkbox_1.1", false));
            Dark.setChecked(sharedPreferences.getBoolean("checkbox_2.1", false));
        }
    }

    public void apply_grid() {
        editor = sharedPreferences.edit()
                .putBoolean("checkbox_1", true)
                .putBoolean("checkbox_2", false)
                .putString("View", "Grid");
        editor.apply();
    }

    public void apply_list() {
        editor = sharedPreferences.edit()
                .putBoolean("checkbox_2", true)
                .putBoolean("checkbox_1", false)
                .putString("View", "List");
        editor.apply();
    }

    public int get_primaryColor() {
        if (sharedPreferences.contains("primary_main"))
            return sharedPreferences.getInt("primary_main", R.color.colorPrimary);
        else
            return R.color.colorPrimary;
    }

    public int get_primaryDarkColor() {
        if (sharedPreferences.contains("primary_dark"))
            return sharedPreferences.getInt("primary_dark", R.color.colorPrimaryDark);
        else
            return R.color.colorPrimaryDark;
    }

    public int get_primaryIndex() {
        if (sharedPreferences.contains("main_index"))
            return sharedPreferences.getInt("main_index", 1);
        else
            return 1;
    }

    public int get_shadeColor() {
        if (sharedPreferences.contains("primary"))
            return sharedPreferences.getInt("primary", R.color.colorPrimary);
        else
            return R.color.colorPrimary;
    }

    public int get_shadeIndex() {
        if (sharedPreferences.contains("shade_index"))
            return sharedPreferences.getInt("shade_index", 4);
        else
            return 4;
    }

    public void setColorPrimary(int color, int colorMain, int colorMainDark, int mainIndex, int shadeIndex) {
        editor = sharedPreferences.edit()
                .putInt("primary", color)
                .putInt("primary_main", colorMain)
                .putInt("primary_dark", colorMainDark)
                .putInt("main_index", mainIndex)
                .putInt("shade_index", shadeIndex);
        editor.apply();
    }

    public int get_accentIndex() {
        if (sharedPreferences.contains("index"))
            return sharedPreferences.getInt("index", 6);
        else
            return 6;
    }

    public int get_accentColor() {
        if (sharedPreferences.contains("accent"))
            return sharedPreferences.getInt("accent", R.color.colorAccent);
        else
            return R.color.colorAccent;
    }

    public void setColorAccent(int colorMain, int index) {
        editor = sharedPreferences.edit()
                .putInt("accent", colorMain)
                .putInt("index", index);
        editor.apply();
    }

    public void setTheme(int themeValue) {
        editor = sharedPreferences.edit();
        if (themeValue == 1) {
            editor.putBoolean("checkbox_1.1", true)
                    .putBoolean("checkbox_2.1", false)
                    .putInt("theme", R.style.Theme).apply();
        } else {
            editor.putBoolean("checkbox_2.1", true)
                    .putBoolean("checkbox_1.1", false)
                    .putInt("theme", R.style.ThemeDark).apply();
        }
    }

    public int getTheme() {
        if (!sharedPreferences.contains("theme"))
            return R.style.Theme;
        else
            return sharedPreferences.getInt("theme", R.style.Theme);
    }
}

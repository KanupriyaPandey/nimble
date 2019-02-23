package com.projects.android.MyNotes.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.widget.RadioButton;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Settings;

public class Shared_Preferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Shared_Preferences(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public SharedPreferences getpref()
    {
        return sharedPreferences;
    }
    public void check_layout()
    {   editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("k")) {
            editor = sharedPreferences.edit();
            editor.putString("k", "Grid");
            editor.apply();
        }
    }
    public String return_layout()
    {
        return sharedPreferences.getString("k", "");
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
    public void apply_grid()
    {
        editor = sharedPreferences.edit();
        editor.putBoolean("checkbox_1", true);
        editor.putBoolean("checkbox_2", false);
        editor.putString("k", "Grid"); //Changing the value of the Shared Preference
        editor.apply();
    }
    public void apply_list()
    {
        editor = sharedPreferences.edit();
        editor.putBoolean("checkbox_2", true);
        editor.putBoolean("checkbox_1",false);
        editor.putString("k","List");   //Changing the value of the Shared Preference
        editor.apply();
    }
    public int get_primaryColor()
    {
        if (sharedPreferences.contains("primary_main"))
            return sharedPreferences.getInt("primary_main", R.color.colorPrimary);
        else
            return R.color.colorPrimary;
    }
    public int get_primaryIndex()
    {
        if (sharedPreferences.contains("main_index"))
            return sharedPreferences.getInt("main_index", 1);
        else
            return 1;
    }
    public int get_shadeColor()
    {
        if (sharedPreferences.contains("primary"))
            return sharedPreferences.getInt("primary", R.color.colorPrimary);
        else
            return R.color.colorPrimary;
    }
    public int get_shadeIndex()
    {
        if (sharedPreferences.contains("shade_index"))
            return sharedPreferences.getInt("shade_index", 1);
        else
            return 4;
    }
    public void setColorPrimary(int color, int colorMain, int colorMainDark, int shadeIndex, int mainIndex)
    {
        editor = sharedPreferences.edit();
        editor.putInt("primary", color).apply();
        editor.putInt("primary_dark", colorMainDark).apply();
        editor.putInt("primary_main", colorMain).apply();
        editor.putInt("main_index", mainIndex).apply();
        editor.putInt("shade_index", shadeIndex).apply();
    }
    public int get_accentIndex()
    {
        if (sharedPreferences.contains("index"))
            return  sharedPreferences.getInt("index", 3);
        else
            return 1;
    }
    public int getaccentColor()
    {
        if (sharedPreferences.contains("accent"))
            return sharedPreferences.getInt("accent", R.color.colorPrimary);
        else
            return R.color.colorPrimary;
    }
    public void setColorAccent(int colorMain, int index)
    {
        editor = sharedPreferences.edit();
        editor.putInt("accent", colorMain).apply();
        editor.putInt("index", index).apply();
    }
    public void setTheme(int themeValue)
    {
        editor = sharedPreferences.edit();
        if(themeValue==1) {
            editor.putBoolean("checkbox_1.1", true);
            editor.putBoolean("checkbox_2.1", false);
            editor.putInt("theme", R.style.Theme).apply();
        }else{
            editor.putBoolean("checkbox_2.1", true);
            editor.putBoolean("checkbox_1.1", false);
            editor.putInt("theme", R.style.ThemeDark).apply();
        }
    }
    public int getTheme()
    {
        if (!sharedPreferences.contains("theme"))
            return R.style.Theme;
        else
            return sharedPreferences.getInt("theme", R.style.Theme);
    }
}

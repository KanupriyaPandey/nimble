package com.projects.android.MyNotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.fragment.PickerAccent;
import com.projects.android.MyNotes.fragment.DialogDefault;
import com.projects.android.MyNotes.fragment.PickerPrimary;
import com.projects.android.MyNotes.fragment.DialogTheme;
import com.projects.android.MyNotes.helper.Preference;
import com.projects.android.MyNotes.listener.FragmentInteraction;

public class Settings extends AppCompatActivity implements FragmentInteraction {
    Preference preference;
    RadioButton grid, list;
    Toolbar toolbar;
    TextView general, theme, advanced, account_title, view_title, base_title, custom_title, primary_title,
            accent_title, location_title, reset_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preference = new Preference(getApplicationContext());
        setTheme(preference.getTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setBackground(new ColorDrawable(preference.get_primaryColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(preference.get_primaryDarkColor());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        grid = findViewById(R.id.radio_grid);
        list = findViewById(R.id.radio_list);
        toolbar = findViewById(R.id.toolbar_settings);
        general = findViewById(R.id.general_title);
        theme = findViewById(R.id.theme_title);
        advanced = findViewById(R.id.advanced_title);

        account_title = findViewById(R.id.account_title);
        view_title = findViewById(R.id.view_title);
        base_title = findViewById(R.id.base_title);
        custom_title = findViewById(R.id.custom_title);
        primary_title = findViewById(R.id.primary_title);
        accent_title = findViewById(R.id.accent_title);
        location_title = findViewById(R.id.location_title);
        reset_title = findViewById(R.id.reset_title);

        general.setTextColor(preference.get_primaryColor());
        theme.setTextColor(preference.get_primaryColor());
        advanced.setTextColor(preference.get_primaryColor());
        account_title.setTextColor(preference.get_accentColor());
        view_title.setTextColor(preference.get_accentColor());
        base_title.setTextColor(preference.get_accentColor());
        custom_title.setTextColor(preference.get_accentColor());
        primary_title.setTextColor(preference.get_accentColor());
        accent_title.setTextColor(preference.get_accentColor());
        location_title.setTextColor(preference.get_accentColor());
        reset_title.setTextColor(preference.get_accentColor());

        setCheckedRadio();
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrid();
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyList();
            }
        });

        findViewById(R.id.account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        findViewById(R.id.base_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThemeDialog();
            }
        });

        findViewById(R.id.customize_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
        findViewById(R.id.primary).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaletteDialog();
            }
        });

        findViewById(R.id.accent).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccentDialog();
            }
        });

        findViewById(R.id.location).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                location();
            }
        });

        findViewById(R.id.reset).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResetDialog();
            }
        });
    }

    public void setCheckedRadio() {
        preference.setCheckbox(grid, list);
    }

    public void applyGrid() {
        boolean checked = grid.isChecked();
        if (checked) {
            try {
                preference.apply_grid();
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void applyList() {
        boolean checked = list.isChecked();
        if (checked) {
            try {
                preference.apply_list();
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "LIST", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPaletteDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PickerPrimary pickerPrimary = PickerPrimary.newInstance();
        pickerPrimary.show(fragmentManager, "Primary");
    }

    private void showAccentDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PickerAccent accent = PickerAccent.newInstance();
        accent.show(fragmentManager, "Accent");
    }


    private void showThemeDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogTheme dialogTheme = DialogTheme.newInstance(this);
        dialogTheme.show(fragmentManager, "Theme");
    }

    public void showCustomDialog() {

    }

    public void location() {

    }

    public void showResetDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogDefault dialogDefault = DialogDefault.newInstance(this);
        dialogDefault.show(fragmentManager, "Reset");
    }

    @Override
    public void setPrimary(Activity activity, int color, int colorDark) {
        toolbar.setBackground(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(colorDark);
        }
        general.setTextColor(color);
        theme.setTextColor(color);
        advanced.setTextColor(color);
    }

    @Override
    public void setAccent(int color) {
        account_title.setTextColor(color);
        view_title.setTextColor(color);
        base_title.setTextColor(color);
        custom_title.setTextColor(color);
        primary_title.setTextColor(color);
        accent_title.setTextColor(color);
        location_title.setTextColor(color);
        reset_title.setTextColor(color);
    }

    @Override
    public void setTheme(Activity activity, int theme) {
        finishAffinity();
        startActivity(new Intent(activity, Main.class));
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    @Override
    public void setValue(Activity activity) {
        finishAffinity();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

}

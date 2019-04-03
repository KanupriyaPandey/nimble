package com.projects.android.MyNotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogNotebook extends DialogFragment {
    List<String> list;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ListView listView;
    EditText editText;
    TextView textView;
    DbHelper helper;
    SQLiteDatabase db;
    ArrayAdapter<String> adapter;
    Home home;

    public DialogNotebook() {
    }

    public static DialogNotebook newInstance() {
        return new DialogNotebook();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_notebook, null);

        editText = view.findViewById(R.id.create);
        textView = view.findViewById(R.id.create_text);
        listView = view.findViewById(R.id.list_view);
        helper = new DbHelper(getContext());
        db = helper.getWritableDatabase();
        home = new Home();
        return_list();

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                add_database(i);
                getDialog().dismiss();
                //bottomSheetMain.dismiss();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (textView.getVisibility() == View.GONE) {
                            list_add();
                            add_database(list.size() - 1);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

    public void add_database(int i) {
        int position = home.select;
        Cursor data = helper.getAll(db);
        int j = 0;
        if (data.moveToLast()) {
            while (j < position) {
                data.moveToPrevious();
                j++;
            }
        }
        helper.updateNotebook(String.valueOf(data.getInt(3)), String.valueOf(listView.getItemAtPosition(i)), db);
    }

    public void list_add() {
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            editor = sharedPreferences.edit();
            String list_name = editText.getText().toString();
            if (list_name.equals("")) {
                Toast.makeText(getContext(), "No Noteboook is created", Toast.LENGTH_SHORT).show();
            } else {
                list.add(list_name);
                Gson gson = new Gson();
                String notebook = gson.toJson(list);   //Converting a list into json data
                editor.putString("Notebook", notebook);
                editor.apply();
                return_list();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "List_add: " + String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }

    public void return_list() {
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            if (sharedPreferences.contains("Notebook")) {
                Gson gson = new Gson();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String Note_list = sharedPreferences.getString("Notebook", null);
                String[] note = gson.fromJson(Note_list, String[].class);
                List<String> list1 = Arrays.asList(note); //converting the json data back to string
                list = new ArrayList<String>(list1);
            } else {
                list = new ArrayList<>();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Return_list: " + String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }

}

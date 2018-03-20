package com.projects.android.MyNotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class AlertDialogFragment extends AppCompatDialogFragment
{
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
    AlertDialog dialog;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog,null);
        editText=(EditText)view.findViewById(R.id.newBook);
        textView=(TextView)view.findViewById(R.id.create);
        listView=(ListView)view.findViewById(R.id.listView);
        helper=new DbHelper(getContext());
        db=helper.getWritableDatabase();
        home=new Home();
        return_list();   //To initialize the list in the beginning
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                add_database(i);
            }
        });
        final DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        if(textView.getVisibility()==View.GONE)
                        {
                            list_add();
                            add_database(list.size()-1); //To update the Notebook field at the time of creation of new notebook
                        }
                        break;
                }

            }
        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
            }
        });
        dialog=new AlertDialog.Builder(getActivity())
                .setTitle("NoteBook")
                .setView(view)
                .setPositiveButton(android.R.string.ok,listener)     //Used to dismiss the dialog box on click and perform a positive action like saving etc
                .setNegativeButton(android.R.string.cancel,listener) //Used to terminate the actions that were performed
                .create();
        return dialog; //Returning the instance of alert dialog
    }
    public void add_database(int i)
    {
        int position=home.select;       //fetching the value of the selected note
        Cursor data=helper.getAll(db);
        int j=0;
        if(data.moveToLast())
        {
            while(j<position)
            {
                data.moveToPrevious();
                j++;
            }
        }
        helper.updateNotebook(String.valueOf(data.getInt(3)),String.valueOf(listView.getItemAtPosition(i)),db);
        dialog.dismiss();
    }
    public void list_add()
    {
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
        }
        catch(Exception e)
            {
                Toast.makeText(getContext(), "List_add: "+String.valueOf(e), Toast.LENGTH_LONG).show();
            }
    }
    public void return_list()
    {
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            if (sharedPreferences.contains("Notebook")) {
                Gson gson = new Gson();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String Note_list = sharedPreferences.getString("Notebook", null);
                String[] note = gson.fromJson(Note_list, String[].class);
                List<String> list1 = Arrays.asList(note); //converting the json data back to string
                list = new ArrayList<String>(list1);
            }
            else
            {
                list=new ArrayList<>();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getContext(), "Return_list: "+String.valueOf(e), Toast.LENGTH_LONG).show();
        }

    }
}

package com.projects.android.MyNotes.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Main;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;

public class BottomSheetMain extends BottomSheetDialogFragment {
    DbHelper help;
    SQLiteDatabase db2;
    Dbhelper2 dbhelper2;
    SQLiteDatabase db;

    public BottomSheetMain() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_main, container, false);
        help = new DbHelper(getContext());
        dbhelper2 = new Dbhelper2(getContext());
        db2 = help.getReadableDatabase();
        db = dbhelper2.getWritableDatabase();

        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });
        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNote();
            }
        });
        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
        return view;
    }

    public void deleteNote() {
        int position = Home.select;
        Cursor data = help.getAll(db2);
        int i = 0;
        if (data.moveToLast()) {
            while (i < position) {
                data.moveToPrevious();
                i++;
            }
        }
        String Title = data.getString(0);
        String content = data.getString(1);
        try {
            dbhelper2.addInfo(Title, content, db);
        } catch (Exception e) {
            Toast.makeText(getContext(), "DataBase" + String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        help.delete(String.valueOf(data.getInt(3)), db2);
        try {
            Intent intent = new Intent(getActivity(), Main.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "DataBase" + String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }

    public void shareNote() {
        int position = Home.select;
        Cursor data = help.getAll(db2);
        int i = 0;
        if (data.moveToLast()) {
            while (i < position) {
                data.moveToPrevious();
                i++;
            }
        }
        String Contents = data.getString(0) + "\n" + data.getString(1);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(Contents) + "\n" + "The Message Was sent via MyNotesApp" + "\n" + "Credits:Kanupriya Pandey & Aayush Rajput");
        sendIntent.setType("text/plain");
        try {
            startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }

    }

    public void addNote() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogNotebook dialogNotebook = DialogNotebook.newInstance();
        dialogNotebook.show(fragmentManager, "NoteBook");
        dismiss();
    }

}


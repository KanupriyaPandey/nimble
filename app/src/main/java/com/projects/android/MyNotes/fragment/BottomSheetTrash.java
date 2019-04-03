package com.projects.android.MyNotes.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;

/**
 * Created by LENOVO on 3/22/2018.
 */

public class BottomSheetTrash extends BottomSheetDialogFragment {
    SQLiteDatabase db, db2;
    DbHelper help2;
    Dbhelper2 help;
    int position, id;
    String Title, Content;
    BottomSheetTrash bottomSheetTrash;

    public BottomSheetTrash() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_trash, container, false);

        help = new Dbhelper2(getContext());
        help2 = new DbHelper(getContext());
        db2 = help2.getWritableDatabase();
        db = help.getWritableDatabase();
        position = Trash.select;
        getNote();

        view.findViewById(R.id.restore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restore_note();
            }
        });
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_bin();
            }
        });
        view.findViewById(R.id.delete_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permanent_delete(id);
            }
        });

        return view;
    }

    public void restore_note() {
        help2.addInfo(Title, null, Content, "0", db2);
        permanent_delete(id);
    }

    public void permanent_delete(int id) {
        help.delete(String.valueOf(id), db);
        fragment_reload();
        bottomSheetTrash = new BottomSheetTrash();
        Toast.makeText(getContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
        try {
            dismiss();
        } catch (Exception e) {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }

    private void empty_bin() {
        Cursor data = help.getAll(db);
        int i = 0;
        if (data.moveToLast()) {
            do {
                help.delete(String.valueOf(data.getInt(3)), db);
            } while (data.moveToPrevious());
        }
        fragment_reload();
        dismiss();
    }

    public void getNote() {
        Cursor data = help.getAll(db);
        int i = 0;
        if (data.moveToLast()) {
            while (i < position) {
                data.moveToPrevious();
                i++;
            }
        }
        id = data.getInt(3);
        Title = data.getString(0);
        Content = data.getString(1);
    }

    public void fragment_reload() {
        Fragment fragment = new Trash();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }
}

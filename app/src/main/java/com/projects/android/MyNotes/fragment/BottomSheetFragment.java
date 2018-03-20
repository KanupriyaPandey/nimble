package com.projects.android.MyNotes.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Main;
import com.projects.android.MyNotes.activity.Notes;
import com.projects.android.MyNotes.activity.Preview;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;

public class BottomSheetFragment extends BottomSheetDialogFragment{
     DbHelper help;
    SQLiteDatabase db2;
    Dbhelper2 dbhelper2;
    SQLiteDatabase db;
    public BottomSheetFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
        help=new DbHelper(getContext());
        dbhelper2=new Dbhelper2(getContext());
        db2=help.getReadableDatabase();
        db=dbhelper2.getWritableDatabase();
        TextView preview=(TextView)view.findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewNote();
            }
        });
        TextView edit=(TextView)view.findViewById(R.id.eddt);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCalled();
            }
        });
        TextView trash=(TextView)view.findViewById(R.id.del);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });
        TextView share=(TextView)view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNote();
            }
        });
        TextView mail=(TextView)view.findViewById(R.id.gmail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailNote();
            }
        });
        TextView add=(TextView)view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
        return view;
    }
    public void previewNote()
    {
        try {
            Home home=new Home();
            Intent i=new Intent(getActivity(), Preview.class);
            String k1="v1";
            i.putExtra(k1,String.valueOf(home.select));   //To pass the position of the selected card across the intent.
            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }
    public void editCalled()
    {   try {
        Home home = new Home(); //To fetch the global variable from that class.
        Intent i = new Intent(getActivity(), Notes.class);
        String k1 = "v1";
        i.putExtra(k1, String.valueOf(home.select));   //To pass the position of the selected card across the intent.
        startActivity(i);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = preferences.edit(); //Just coz i like using shared preferences now.
        edit.putBoolean("Update", true);
        edit.apply();
    }
    catch (Exception e)
    {
        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
    }
    }
    public void deleteNote()
    {   Home home=new Home();
        int position=home.select;
        Cursor data=help.getAll(db2);
        int i=0;
        if(data.moveToLast())
        {
            while(i<position)
            {
                data.moveToPrevious();
                i++;
            }
        }
        String Title=data.getString(0);
        String content=data.getString(1);
        try {
            dbhelper2.addInfo(Title, content, db);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "DataBase"+String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        help.delete(String.valueOf(data.getInt(3)),db2);
        try {
            Intent intent = new Intent(getActivity(), Main.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "DataBase"+String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }
    public void shareNote()
    {
        Home home=new Home();
        int position=home.select;
        Cursor data=help.getAll(db2);
        int i=0;
        if(data.moveToLast())
        {
            while(i<position)
            {
                data.moveToPrevious();
                i++;
            }
        }
        String Contents=data.getString(0)+"\n"+data.getString(1);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(Contents)+"\n"+"The Message Was sent via MyNotesApp"+"\n"+"Credits:Kanupriya Pandey & Aayush Rajput");
        sendIntent.setType("text/plain");
        try {
            startActivity(sendIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }

    }
    public void mailNote()
    {
        Home home=new Home();
        int position=home.select;
        Cursor data=help.getAll(db2);
        int i=0;
        if(data.moveToLast())
        {
            while(i<position)
            {
                data.moveToPrevious();
                i++;
            }
        }
        String Contents="<h2><b>"+data.getString(0)+"</b></h2>"+"\n"+data.getString(1);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , ""); // email id can be hardcoded too
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Note_Share via MyNotes");
        emailIntent.putExtra(Intent.EXTRA_TEXT   , Html.fromHtml(Contents)+"\n"+"The Mail Was sent via MyNotesApp"+"\n"+"Credits:Kanupriya Pandey & Aayush Rajput");
        try {
            startActivity(Intent.createChooser(emailIntent, "Done!"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No Email client found!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void addNote()
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        AlertDialogFragment dialogFragment=new AlertDialogFragment();
        dialogFragment.show(fragmentManager,"Alert!!");
    }
}


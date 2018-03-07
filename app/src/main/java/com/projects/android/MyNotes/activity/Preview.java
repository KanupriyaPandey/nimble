package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;

public class Preview extends AppCompatActivity {
    private DbHelper help;
    SQLiteDatabase dbRead;
    String primary_title;
    TextView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        help=new DbHelper(getApplicationContext());
        dbRead=help.getReadableDatabase();
        notes=(TextView)findViewById(R.id.displayNote);
        Intent i=getIntent();
        String k1="v1";
        int position=Integer.parseInt(i.getStringExtra(k1));
        setData(position);
    }
    public void setData(int position)
    {
        Cursor data=help.getAll(dbRead);
        int i=0;
        if(data.moveToLast())
        {
            while(i<position)
            {
                data.moveToPrevious();
                i++;
            }
        }
        primary_title=data.getString(0);
        String Contents="<h2><b>"+data.getString(0)+"</b></h2>"+"\n"+data.getString(1);
        notes.setText(Html.fromHtml(Contents));
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),Main.class); //So the bottom sheet is deactivated.
        startActivity(intent);
    }
}

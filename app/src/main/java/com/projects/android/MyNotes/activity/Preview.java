package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.fragment.BottomSheetFragment;

public class Preview extends AppCompatActivity {
    private DbHelper help;
    SQLiteDatabase dbRead;
    SQLiteDatabase db;
    String primary_title;
    TextView notes;
    int id;
    EditText editText;
    String Contents;
    Notes obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        help=new DbHelper(getApplicationContext());
        dbRead=help.getReadableDatabase();
        db=help.getWritableDatabase();
        obj=new Notes();
        notes=(TextView)findViewById(R.id.displayNote);
        editText=findViewById(R.id.editNote);
        Intent i=getIntent();
        String k1="v1";
        final int position=Integer.parseInt(i.getStringExtra(k1));
        try {
            setData(position);
        }
        catch (Exception e)
        {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                editText.setText(Contents);
            }
        });
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
        id=data.getInt(3);
        primary_title=data.getString(0);
        Contents=data.getString(0)+"\n"+data.getString(1);
        notes.setText(Contents);
    }

    @Override
    public void onBackPressed() {
        String Content = editText.getText().toString();
        if (Content.equals("")&&notes.getVisibility()==View.VISIBLE) {
            super.onBackPressed();
        }
        else
        {
            String Title = "";
            String content = "";
            int p = 0;
            do {
                if (Content.charAt(p) == '\n') {
                    p++;
                    do {
                        content += Content.charAt(p);
                        p++;
                    }
                    while (p < Content.length());
                } else {
                    Title = Title + Content.charAt(p);
                }
                p++;
            }
            while (p < Content.length());
            try {
                int success = help.updateNote(String.valueOf(id),Title, content, db);
                if (success > 0) {
                    Toast.makeText(getApplicationContext(), primary_title+" Update Success", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
        }
    }
}

package com.projects.android.MyNotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "Notes.db";
    private static final int DBVERSION = 1;
    public static final String tbquery = "create table note("
            + "Title text,"
            + "Content text,"
            + "Date text,"
            + "image blob,"
            +"Id integer primary key autoincrement,"
            +"Notebook text)";

    public DbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION); //Database Created
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tbquery);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void addInfo(String title, String content, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        try {
            cv.put("Title", title);
            cv.put("Content", content);
            cv.put("Date", getDateTime());
            //cv.put("Image",image);
            db.insert("note", null, cv);
        } catch (Exception exp) {
            System.out.println(exp.toString());
        }
    }

    public Cursor getAll(SQLiteDatabase db) {
        Cursor data;
        String[] Column_name = {"Title", "Content", "Date","Id","Notebook"};
        data = db.query("note", Column_name, null, null, null, null, null);
        return data;
    }
    public int updateNote(String id,String newTitle,String newContent,SQLiteDatabase db)
    {
        ContentValues newValues=new ContentValues();
        newValues.put("Title",newTitle);
        newValues.put("Content",newContent);
        newValues.put("Date",getDateTime());
        String [] column_name={"Title","Content","Date"};
        String selection="Id"+" like ?";
        String [] selcarg={id};
        int ret=db.update("note",newValues,selection,selcarg);
        return ret;
    }
    public void updateNotebook(String id,String Notebook,SQLiteDatabase db)
    {
        ContentValues newValues=new ContentValues();
        newValues.put("Notebook",Notebook);
        String [] column_name={"Notebook"};
        String selection="Id"+" like ?";
        String [] selcarg={id};
        db.update("note",newValues,selection,selcarg);
    }
    public void delete(String id,SQLiteDatabase db)
    {
        String selection="Id "+"like ?";
        String []selcargs={id};
        db.delete("note",selection,selcargs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}

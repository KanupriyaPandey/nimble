package com.projects.android.MyNotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "Notes.db";
    private static final int DBVERSION = 1;
    public static final String tbquery = "create table note(" +
            "Title text primary key," +
            "Content text,"
            + "Date text,"
            + "image blob)";

    public DbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION); //Database Created
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tbquery);
        System.out.println("Table Created");
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
        String[] Column_name = {"Title", "Content", "Date"};
        data = db.query("note", Column_name, null, null, null, null, null);
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}

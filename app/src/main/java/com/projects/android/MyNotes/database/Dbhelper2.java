package com.projects.android.MyNotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LENOVO on 3/7/2018.
 */

public class Dbhelper2 extends SQLiteOpenHelper {
    private static final String DBNAME = "TRASH.db";
    private static final int DBVERSION = 1;
    public static final String tbquery = "create table trash("
            + "Title text,"
            + "Content text,"
            + "Date text,"
            + "image blob,"
            +"Id integer primary key autoincrement,"
            +"Notebook text)";
    public Dbhelper2(Context context) {

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
            db.insert("trash", null, cv);
        } catch (Exception exp) {
            System.out.println(exp.toString());
        }
    }
    public Cursor getAll(SQLiteDatabase db) {
        Cursor data;
        String[] Column_name = {"Title", "Content", "Date","Id"};
        data = db.query("trash", Column_name, null, null, null, null, null);
        return data;
    }
    public void delete(String id,SQLiteDatabase db)
    {
        String selection="Id "+"like ?";
        String []selcargs={id};
        db.delete("trash",selection,selcargs);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

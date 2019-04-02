package com.example.dailyrozgar.MyDB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Helper extends SQLiteOpenHelper {
    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Request(customer text ,worker text ,timeFrom text,timeTo text)");
        Log.i("DatabaseTabs","Request Database Created");
        db.execSQL("create table Accept(cusWorker text ,worker text ,timeFrom text,timeTo text)");
        Log.i("DatabaseTabs","Accept Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


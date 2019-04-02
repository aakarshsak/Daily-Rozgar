package com.example.dailyrozgar.MyDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDatabase {

    public static final String MY_DB = "DatabaseTabs";

    SQLiteDatabase sdb;
    Helper myHelper;
    Cursor cursor;

    public MyDatabase(Context myContext) {
        myHelper = new Helper(myContext, MY_DB, null, 1);
    }

    public void open() {
        sdb = myHelper.getWritableDatabase();
    }

    public void close() {
        myHelper.close();
    }

    public void insertRequest(ContentValues cv) {
        sdb.insert("Request", null, cv);
        Log.i("DatabaseTabs", "Data Inserted in Accept");
    }

    public void insertAccept(ContentValues cv) {
        sdb.insert("Accept", null, cv);
        Log.i("DatabaseTabs", "Data Inserted in Request");
    }
    public Cursor getParticularRequest(String cusWorker){
        sdb=myHelper.getReadableDatabase();
        String whereArgs[] = new String[]{cusWorker};
        cursor=sdb.query("Request",null,"cusWorker=?",whereArgs,null,null,null);
        return cursor;
    }

    public Cursor getParticularAccept(String cusWorker){
        sdb=myHelper.getReadableDatabase();
        String whereArgs[] = new String[]{cusWorker};
        cursor=sdb.query("Accept",null,"cusWorker=?",whereArgs,null,null,null);
        return cursor;
    }
    public Cursor getAllRequest() {
        sdb = myHelper.getReadableDatabase();
        cursor = sdb.query("Request", null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getAllAccept() {
        sdb = myHelper.getReadableDatabase();
        cursor = sdb.query("Accept", null, null, null, null, null, null);
        return cursor;
    }

    public void deleteRequest(String cusUser) {
        sdb.execSQL("delete  from " + "Request where cusWorker ="+cusUser);
        sdb.close();
    }

    public void deleteAccept(String cusUser) {
        sdb.execSQL("delete  from " + "Accept where cusWorker ="+cusUser);
        sdb.close();
    }

}
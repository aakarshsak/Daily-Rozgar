package com.example.dailyrozgar.MyDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dailyrozgar.MyDB.RequestDB.Request;

import java.util.ArrayList;
import java.util.List;


public class MyDatabase {

    public static final String MY_DB = "DatabaseTabs";
    public static final String REQUEST = "Request";
    public static final String ACCEPT = "Accept";
    public static final String CUSTOMER = "customer";
    public static final String WORKER = "worker";
    public static final String TO = "timeTo";
    public static final String FROM = "timeFrom";

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
        sdb.insert(REQUEST, null, cv);
        Log.i("DatabaseTabs", "Data Inserted in Request");
    }

    public void insertAccept(ContentValues cv) {
        sdb.insert(ACCEPT, null, cv);
        Log.i("DatabaseTabs", "Data Inserted in Accept");
    }
    public Cursor getParticularRequest(String cus,String work){
        sdb=myHelper.getReadableDatabase();
        String whereArgs[] = new String[]{cus,work};
        cursor=sdb.rawQuery("SELECT * FROM Request WHERE customer=? AND worker=?",whereArgs);
        return cursor;
    }

    public Cursor getParticularAccept(String cus,String work){
        sdb=myHelper.getReadableDatabase();
        String whereArgs[] = new String[]{cus,work};
        cursor=sdb.rawQuery("SELECT * FROM Accept WHERE customer=? AND worker=?",whereArgs);
        return cursor;
    }
    public ArrayList<Request> getAllRequests(String worker) {
        ArrayList<Request> requestList = new ArrayList<Request>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+REQUEST;

        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(worker.equals(cursor.getString(1)))
                {
                    Request request = new Request();
                    request.setCustomer(cursor.getString(0));
                    request.setWorker(cursor.getString(1));
                    request.setFrom(cursor.getString(2));
                    request.setTo(cursor.getString(3));
                    // Adding contact to list
                    requestList.add(request);
                }
            } while (cursor.moveToNext());
        }

        return requestList;
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
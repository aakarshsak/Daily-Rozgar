package com.example.dailyrozgar.MyDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dailyrozgar.MyDB.RequestDB.Request;

import java.util.ArrayList;

public class Helper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DailyRozgar";
    private static final String REQUEST = "Request";
    private static final String TIME_TO = "timeTo";
    private static final String CUSTOMER = "customer";
    private static final String WORKER = "worker";
    private static final String TIME_FROM = "timeFrom";
    private static final String ACCEPT = "Accept";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCEPT_TABLE = "CREATE TABLE " + REQUEST + "("
                + CUSTOMER + " TEXT," + WORKER + " TEXT,"
                + TIME_FROM + " TEXT," + TIME_TO + " TEXT)";
        db.execSQL(CREATE_ACCEPT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ACCEPT);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CUSTOMER, request.getCustomer()); // Contact Name
        values.put(WORKER, request.getWorker());
        values.put(TIME_FROM,request.getFrom());
        values.put(TIME_TO,request.getTo());// Contact Phone

        // Inserting Row
        db.insert(REQUEST, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    public Request getRequest(String customer,String worker) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("PRint",customer+worker);
        Cursor cursor = db.rawQuery("SELECT * FROM "+REQUEST+" WHERE "+CUSTOMER+"=? AND "+WORKER+"=?",new String[]{customer,worker});

        Request request = null;
        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount()>0) {
            request = new Request();
            request.setCustomer(cursor.getString(0));
            request.setWorker(cursor.getString(1));
            request.setTo(cursor.getString(2));
            request.setFrom(cursor.getString(3));
        }// return contact
        return request;
    }

    // code to get all contacts in a list view
    public ArrayList<Request> getAllRequests(String worker) {
        ArrayList<Request> requestList = new ArrayList<Request>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+REQUEST;

        SQLiteDatabase db = this.getWritableDatabase();
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
                    Log.i("DatabaseTabs", request.getCustomer()+ request.getWorker());
                }

            } while (cursor.moveToNext());

        }


        return requestList;
    }

    // code to update the single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }

    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }

    // Getting contacts Count
    public int getAcceptCount() {
        String countQuery = "SELECT  * FROM " + ACCEPT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}

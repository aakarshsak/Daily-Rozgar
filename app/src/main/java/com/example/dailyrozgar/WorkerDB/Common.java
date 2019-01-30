package com.example.dailyrozgar.WorkerDB;

import com.example.dailyrozgar.WorkerDB.Class.Worker;

public class Common {
    private static String DB_NAME="daily-rozgar";
    private static String COLLECTION_NAME="Workers";
    private static String API_KEY="v3unXFiqRqGW3-0Zg18iYGbKNfaXEb0v";

    public static String getAddressSingle(Worker worker) {
        String baseUrl=String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder=new StringBuilder(baseUrl);
        stringBuilder.append("/"+worker.get_id().getOid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();
    }

    public static String getAddressAPI() {
        String baseUrl=String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder=new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();
    }
}

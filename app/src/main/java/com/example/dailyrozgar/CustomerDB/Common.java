package com.example.dailyrozgar.CustomerDB;

import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.WorkerDB.Class.Worker;

public class Common {
    private static String DB_NAME="daily_rozgar";
    private static String COLLECTION_NAME="customerdetails";
    private static String API_KEY="sPtzXx8lwDwnR2d4UcMMdvA_TyCu3IIe";

    public static String getAddressSingle(Customer customer) {
        String baseUrl=String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder=new StringBuilder(baseUrl);
        stringBuilder.append("/"+customer.get_id().getOid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();
    }

    public static String getAddressAPI() {
        String baseUrl=String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder=new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();
    }
}

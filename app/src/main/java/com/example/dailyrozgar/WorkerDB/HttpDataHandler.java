package com.example.dailyrozgar.WorkerDB;

import com.example.dailyrozgar.WorkerDB.Class.Worker;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpDataHandler {

    static String stream=null;

    public HttpDataHandler() {
    }

    public void postHTTPData(String json) {
        try {
            Common c=new Common();
            URL url=new URL(c.getAddressAPI());
            HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            byte[] out=json.getBytes(StandardCharsets.UTF_8);
            int length=out.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            urlConnection.connect();
            try(OutputStream os=urlConnection.getOutputStream())
            {
                os.write(out);
            }
            InputStream response=urlConnection.getInputStream();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

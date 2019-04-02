package com.example.dailyrozgar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dailyrozgar.CustomerDB.Adapter.CustomAdapter;
import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.CustomerDB.Common;
import com.example.dailyrozgar.MyDB.Helper;
import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StartScreen extends AppCompatActivity {
    ImageView userImage,workerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        ///username and password image buttons to enter into username or password section
        userImage=findViewById(R.id.userImage);
        workerImage=findViewById(R.id.workerImage);

        Helper helper=new Helper(this);
        //helper.addCurrent("aakarshsak","ashish111");

//
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starting login page to sign in as a user
                String u=helper.getCurrent(0);
                CustomAdapter.GetContactsAsyncTask task = new CustomAdapter.GetContactsAsyncTask(u);
                Customer c=null;
                try{
                    c=task.execute().get();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent i=new Intent(StartScreen.this,CustomerMainActivity.class);
                i.putExtra("Customer",c);
                startActivity(i);
            }
        });

        workerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting login page to sign in as a worker
                String u=helper.getCurrent(1);
                GetContactsAsyncTaskForWorker2 task = new GetContactsAsyncTaskForWorker2(u);
                Worker w=null;
                try{
                    w=task.execute().get();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent i=new Intent(StartScreen.this,WorkerMainActivity.class);
                i.putExtra("Worker",w);
                startActivity(i);
            }
        });
    }

    public static class GetContactsAsyncTaskForCustomer extends AsyncTask<Customer, Void, Boolean> {
        String server_output = null;
        String temp_output = null;
        String u;

        public GetContactsAsyncTaskForCustomer(String u)
        {
            this.u=u;
        }
        @Override
        protected Boolean doInBackground(Customer... arg0) {

            //ArrayList<Customer> customers = new ArrayList<>();
            try {
                com.example.dailyrozgar.CustomerDB.Common common = new Common();
                URL url = new URL(common.getAddressAPI());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((temp_output = br.readLine()) != null) {
                    server_output = temp_output;
                }

                String mongoarray = "{ DB_output: "+server_output+"}";
                Object o = com.mongodb.util.JSON.parse(mongoarray);

                DBObject dbObj = (DBObject) o;
                BasicDBList contacts = (BasicDBList) dbObj.get("DB_output");
                for (Object obj : contacts) {
                    DBObject userObj = (DBObject) obj;
                    if(u.equals(userObj.get("username").toString())) {
                        return false;
                    }
                    else
                        continue;

                }

            }catch (Exception e) {
                e.getMessage();
            }

            return true;
        }
    }


    public static class GetContactsAsyncTaskForWorker extends AsyncTask<Worker, Void, Boolean> {
        String server_output = null;
        String temp_output = null;
        String u;

        public GetContactsAsyncTaskForWorker(String u)
        {
            this.u=u;
        }
        @Override
        protected Boolean doInBackground(Worker... arg0) {

            //ArrayList<Customer> customers = new ArrayList<>();
            try {
                com.example.dailyrozgar.WorkerDB.Common common = new com.example.dailyrozgar.WorkerDB.Common();
                URL url = new URL(common.getAddressAPI());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((temp_output = br.readLine()) != null) {
                    server_output = temp_output;
                }

                String mongoarray = "{ DB_output: "+server_output+"}";
                Object o = com.mongodb.util.JSON.parse(mongoarray);

                DBObject dbObj = (DBObject) o;
                BasicDBList contacts = (BasicDBList) dbObj.get("DB_output");
                for (Object obj : contacts) {
                    DBObject userObj = (DBObject) obj;
                    if(u.equals(userObj.get("username").toString())) {
                        return false;
                    }
                    else
                        continue;

                }

            }catch (Exception e) {
                e.getMessage();
            }

            return true;
        }
    }

    public static class GetContactsAsyncTaskForWorker2 extends AsyncTask<Worker, Void, Worker> {
        String server_output = null;
        String temp_output = null;
        String u;
        Worker temp=null;
        public GetContactsAsyncTaskForWorker2(String u)
        {
            this.u=u;
        }
        @Override
        protected Worker doInBackground(Worker... arg0) {

            //ArrayList<Customer> customers = new ArrayList<>();
            try {
                com.example.dailyrozgar.WorkerDB.Common common = new com.example.dailyrozgar.WorkerDB.Common();
                URL url = new URL(common.getAddressAPI());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((temp_output = br.readLine()) != null) {
                    server_output = temp_output;
                }

                String mongoarray = "{ DB_output: "+server_output+"}";
                Object o = com.mongodb.util.JSON.parse(mongoarray);

                DBObject dbObj = (DBObject) o;
                BasicDBList contacts = (BasicDBList) dbObj.get("DB_output");
                for (Object obj : contacts) {
                    DBObject userObj = (DBObject) obj;
                    if(u.equals(userObj.get("username").toString())) {
                        temp = new Worker();
                        temp.setUsername(userObj.get("username").toString());
                        temp.setPassword(userObj.get("password").toString());
                        temp.setBase(userObj.get("bamnt").toString());
                        temp.setFirst(userObj.get("firstname").toString());
                        temp.setLast(userObj.get("lastname").toString());
                        temp.setSex(userObj.get("gender").toString());
                        temp.setAge(userObj.get("age").toString());
                        temp.setPhone(userObj.get("contactno").toString());
                        temp.setLoc(userObj.get("area").toString());
                        temp.setProf(userObj.get("occupation").toString());
                        temp.setState(userObj.get("state").toString());
                        temp.setZip(userObj.get("zcode").toString());
                        temp.setCity(userObj.get("city").toString());
                        return temp;
                    }
                    else
                        continue;

                }

            }catch (Exception e) {
                e.getMessage();
            }

            return temp;
        }
    }





}

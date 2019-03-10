package com.example.dailyrozgar;

import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.example.dailyrozgar.WorkerDB.Adapter.CustomAdapter;
import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.example.dailyrozgar.WorkerDB.Common;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkersListActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    static ArrayList<Worker> datamodels;
    CardView cardView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    List<Worker> workers=new ArrayList<Worker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_list);

        recyclerView = findViewById(R.id.workersListRecycler);
        recyclerView.hasFixedSize();
        cardView =findViewById(R.id.workerListCardView);


        toolbar=findViewById(R.id.toolBar);
        drawerLayout=findViewById(R.id.drawerLayout);


        //merging toolbar with action bar
        setSupportActionBar(toolbar);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        layoutManager=new LinearLayoutManager(this);
        //Recycler View setup
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        GetContactsAsyncTask task = new GetContactsAsyncTask();
        try {
            adapter=new CustomAdapter(task.execute().get());
            recyclerView.setAdapter(adapter);

            Toast.makeText(this, "Fetched from MongoDB!!", Toast.LENGTH_SHORT).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public class GetContactsAsyncTask extends AsyncTask<Worker, Void, ArrayList<Worker>> {
         String server_output = null;
         String temp_output = null;

        @Override
        protected ArrayList<Worker> doInBackground(Worker... arg0) {

            ArrayList<Worker> workers = new ArrayList<>();
            try {
                Common common = new Common();
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
                    String pro=userObj.get("profession").toString();
                    if(pro.equals("Plumber")) {
                        Worker temp = new Worker();
                        temp.setBase(userObj.get("base_price").toString());
                        temp.setFirst(userObj.get("first_name").toString());
                        temp.setLast(userObj.get("last_name").toString());
                        temp.setPhone(userObj.get("contact_no").toString());
                        temp.setLoc(userObj.get("locality").toString());
                        temp.setProf(userObj.get("profession").toString());
                        workers.add(temp);

                    }



                }

            }catch (Exception e) {
                e.getMessage();
            }

            return workers;
        }
    }


//    class GetData extends AsyncTask<String,Void,String> {
////
////
////
////        @Override
////        protected String doInBackground(String... params) {
////            String stream=null;
////            String urlString=params[0];
////            HttpDataHandler http=new HttpDataHandler();
////            stream=http.getHTTPData(urlString);
////            return stream;
////        }
////
////        @Override
////        protected void onPostExecute(String s) {
////            super.onPostExecute(s);
////            Gson gson = new Gson();
////            Type listType = new TypeToken<ArrayList<Worker>>() {
////            }.getType();
////            datamodels = gson.fromJson(s, listType);
////            //datamodels = new ArrayList<Worker>();
////
////            ///need to  addd
////
////
////
////            //Attaching to the adapter
////            adapter = new CustomAdapter(datamodels);
////            recyclerView.setAdapter(adapter);
////        }
////    }



}

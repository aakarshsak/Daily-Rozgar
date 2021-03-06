package com.example.dailyrozgar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.CustomerFragments.History;
import com.example.dailyrozgar.CustomerFragments.Home;
import com.example.dailyrozgar.CustomerFragments.MyProfile;
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
import java.util.concurrent.ExecutionException;

public class WorkersListActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    CardView cardView;
    Toolbar toolbar;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_list);

        recyclerView = findViewById(R.id.workersListRecycler);
        recyclerView.hasFixedSize();
        cardView =findViewById(R.id.workerListCardView);
        drawerLayout=findViewById(R.id.drawerLayout);

        toolbar=findViewById(R.id.toolBar);
        drawerLayout=findViewById(R.id.drawerLayout);

        Intent i = getIntent();
        String s=i.getStringExtra("Profession");
        username = i.getStringExtra("Username");

        //merging toolbar with action bar
        setSupportActionBar(toolbar);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(s);


        layoutManager=new LinearLayoutManager(this);
        //Recycler View setup
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        GetContactsAsyncTask task = new GetContactsAsyncTask(s);
        try {
            adapter=new CustomAdapter(task.execute().get(),username);
            recyclerView.setAdapter(adapter);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public class GetContactsAsyncTask extends AsyncTask<Worker, Void, ArrayList<Worker>> {
         String server_output = null;
         String temp_output = null;
         String s;
         GetContactsAsyncTask(String s)
         {
             this.s=s;
         }
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
                    String pro=userObj.get("occupation").toString();
                    if(pro.equals(s)) {
                        Worker temp = new Worker();
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
                        workers.add(temp);
                    }

                }

            }catch (Exception e) {
                e.getMessage();
            }

            return workers;
        }
    }

    public boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            return true;
        }
        return false;
    }
}

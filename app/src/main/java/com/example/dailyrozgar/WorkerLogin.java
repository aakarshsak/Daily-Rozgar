package com.example.dailyrozgar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.MyDB.Helper;
import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.example.dailyrozgar.WorkerDB.Common;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorkerLogin extends AppCompatActivity {

    EditText user,pass;
    Button btn;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        //username and password edit text fields to enter username and password
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);

        //button  to submit my username and pasword to be checked by the database
        btn=findViewById(R.id.loginButton);


        //a text field to  go to a sign up page if already not signed up
        signup=findViewById(R.id.signup2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkerLogin.this,WorkerSignup.class));
            }
        });

        ///a string to test the feature of sending the string to another page
        //String name="Aakarsh Sinha";

        //button to subit my request to sign in
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///to store the username and password into strings
                String username=user.getText().toString();
                String password=pass.getText().toString();

                GetContactsAsyncTask task =new GetContactsAsyncTask(username,password);
                Worker w=null;
                try{
                    w=task.execute().get();
                }catch(Exception e){
                    e.printStackTrace();
                }

                ///checks if username is there in the database or not and returns true if there is
                if(w!=null)
                {
                    Helper helper=new Helper(WorkerLogin.this);
                    helper.updateCurrent(w.getUsername(),helper.getCurrent(1),2);
                    Intent i=new Intent(WorkerLogin.this, WorkerMainActivity.class);
                    i.putExtra("Worker",w);
                    startActivity(i);//new Intent(CustomerLogin.this,CustomerMainActivity.class));

//                    Intent intent=new Intent(CustomerLogin.this,CustomerMainActivity.class);
//                    intent.putExtra("name",name);
//                    startActivity(intent);
                }

                ///else print a toast saying invalid login
                else
                {
                    Toast.makeText(WorkerLogin.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    user.setText("");
                    pass.setText("");
                }
            }
        });
    }

    public class GetContactsAsyncTask extends AsyncTask<Worker, Void, Worker> {
        String server_output = null;
        String temp_output = null;
        String u,p;
        Worker temp=null;

        public GetContactsAsyncTask(String u,String p)
        {
            this.u=u;
            this.p=p;
        }
        @Override
        protected Worker doInBackground(Worker... arg0) {

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
                    if(u.equals(userObj.get("username").toString()) && p.equals(userObj.get("password").toString())) {
                        temp = new Worker();
                        temp.setUsername(userObj.get("username").toString());
                        temp.setPassword(userObj.get("password").toString());
                        temp.setFirst(userObj.get("firstname").toString());
                        temp.setLast(userObj.get("lastname").toString());
                        temp.setSex(userObj.get("gender").toString());
                        temp.setPhone(userObj.get("contactno").toString());
                        temp.setAge(userObj.get("age").toString());
                        temp.setCity(userObj.get("city").toString());
                        temp.setState(userObj.get("state").toString());
                        temp.setLoc(userObj.get("area").toString());
                        temp.setZip(userObj.get("zcode").toString());
                        temp.setProf(userObj.get("occupation").toString());
                        temp.setBase(userObj.get("bmant").toString());
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


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
import com.example.dailyrozgar.CustomerDB.Common;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CustomerLogin extends AppCompatActivity {

    EditText user,pass;
    Button btn;
    TextView signup;
    ArrayList<Customer> returnValues = new ArrayList<Customer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        btn=findViewById(R.id.loginButton);
        signup=findViewById(R.id.signup2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLogin.this,CustomerSignup.class));
            }
        });
        String name="Aakarsh Sinha";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=user.getText().toString();
                String password=pass.getText().toString();
                if(fetch(username,password))//username.equals("aakarshsak") && password.equals("aak123"))
                {
                    startActivity(new Intent(CustomerLogin.this,CustomerMainActivity.class));

//                    Intent intent=new Intent(CustomerLogin.this,CustomerMainActivity.class);
//                    intent.putExtra("name",name);
//                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(CustomerLogin.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    user.setText("");
                    pass.setText("");
                }
            }
        });
    }

    public Boolean fetch(String user,String pass)
    {
        //Boolean res=false;
        GetContactsAsyncTask task = new GetContactsAsyncTask(user,pass);
        try {

            Customer c=task.execute().get();
            if(c==null) return false;
            else return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class GetContactsAsyncTask extends AsyncTask<Customer, Void, Customer> {
        String server_output = null;
        String temp_output = null;
        String u,p;

        public GetContactsAsyncTask(String u,String p)
        {
            this.u=u;
            this.p=p;
        }
        @Override
        protected Customer doInBackground(Customer... arg0) {

            ArrayList<Customer> customers = new ArrayList<>();
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
                Customer temp=null;
                for (Object obj : contacts) {
                    DBObject userObj = (DBObject) obj;
                    if(u.equals(userObj.get("username").toString()) && p.equals(userObj.get("password").toString())) {
                        temp = new Customer();
                        temp.setUsername(userObj.get("username").toString());
                        temp.setPassword(userObj.get("password").toString());
                        return temp;
                    }
                    else
                        continue;

                }

            }catch (Exception e) {
                e.getMessage();
            }

            return new Customer();
        }
    }

}
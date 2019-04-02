package com.example.dailyrozgar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.CustomerDB.Common;
import com.example.dailyrozgar.CustomerDB.HttpDataHandler;
import com.example.dailyrozgar.MyDB.Helper;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerSignup extends AppCompatActivity {

    EditText user,pass,repass,first,last,contact,city,zip,state;
    Spinner loc;
    Button btn;
    Boolean flag,flag2;
    Customer c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        user=findViewById(R.id.customerUsername);
        pass=findViewById(R.id.customerPassword);
        repass=findViewById(R.id.customerConfirmPassword);
        first=findViewById(R.id.customerFirstName);
        last=findViewById(R.id.customerLastName);
        loc=findViewById(R.id.customerLocality);
        contact=findViewById(R.id.customerContact);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        loc.setAdapter(adapter1);


        c=new Customer();
        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c.setLoc(loc.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn=findViewById(R.id.customerSubmitButton);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String r=repass.getText().toString();
                c.setUsername(user.getText().toString());
                c.setPassword(pass.getText().toString());
                c.setCity("Tumkur");
                c.setContact(contact.getText().toString());
                c.setFname(first.getText().toString());
                c.setLname(last.getText().toString());
                //c.setLoc(loc.getText().toString());
                c.setZip("572103");
                c.setState("Karnataka");

                StartScreen.GetContactsAsyncTaskForCustomer task = new StartScreen.GetContactsAsyncTaskForCustomer(c.getUsername());
                StartScreen.GetContactsAsyncTaskForWorker task2 =new StartScreen.GetContactsAsyncTaskForWorker(c.getUsername());
                try {
                    flag = task.execute().get();
                    flag2=task2.execute().get();
                }catch(Exception e){e.printStackTrace();}
                if(!flag || !flag2)
                {
                    Toast.makeText(CustomerSignup.this, "username already exist", Toast.LENGTH_SHORT).show();
                }

                if(c.getPassword().equals(r))
                {
                    if(flag && flag2)
                    {
                        Toast.makeText(CustomerSignup.this, "Signed Up Successsfully", Toast.LENGTH_LONG).show();

                        new PostData().execute(c);

                        Helper helper=new Helper(CustomerSignup.this);
                        helper.updateCurrent(c.getUsername(),helper.getCurrent(0),1);
//                        Intent intent=new Intent(CustomerSignup.this,CustomerMainActivity.class);
//                        intent.putExtra("name",f+l);
//                        startActivity(intent);
                        Intent i=new Intent(CustomerSignup.this,CustomerMainActivity.class);
                        i.putExtra("Customer",c);
                        startActivity(i);
                    }
                }
                else
                {
                    Toast.makeText(CustomerSignup.this, "Password Did Not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    class PostData extends AsyncTask<Object,Void,Boolean>
    {


        @Override
        protected Boolean doInBackground(Object... params) {
            Customer c = (Customer) params[0];
            HttpDataHandler hh=new HttpDataHandler();
            String json = "{\"username\":\"" + c.getUsername() +
                    "\",\"password\":\"" + c.getPassword() +
                    "\",\"fname\":\"" + c.getFname() +
                    "\",\"contact\":\"" + c.getContact() +
                    "\",\"flat\":\"" + c.getFlat() +
                    "\",\"street\":\"" + c.getStreet() +
                    "\",\"landmark\":\"" + c.getLandmark() +
                    "\",\"lname\":\"" + c.getLname() +
                    "\",\"area\":\"" + c.getLoc() +
                    "\",\"city\":\"" + c.getCity() +
                    "\",\"zip\":\"" + c.getZip() +
                    "\",\"state\":\"" + c.getState() +"\"}";
            hh.postHTTPData(json);
            return false;
        }
    }



}

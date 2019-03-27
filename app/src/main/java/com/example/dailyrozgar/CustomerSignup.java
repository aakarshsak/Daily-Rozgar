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
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerSignup extends AppCompatActivity {

    EditText user,pass,repass,first,last,city,zip,state;
    Spinner loc;
    Button btn;
    Boolean flag=false;
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
                c.setFname(first.getText().toString());
                c.setLname(last.getText().toString());
                //c.setLoc(loc.getText().toString());
                c.setZip("572103");
                c.setState("Karnataka");

                GetContactsAsyncTask task = new GetContactsAsyncTask(c.getUsername());

                try {
                    flag = task.execute().get();
                }catch(Exception e){e.printStackTrace();}
                if(!flag)
                {
                    Toast.makeText(CustomerSignup.this, "username already exist", Toast.LENGTH_SHORT).show();
                }

                if(c.getPassword().equals(r))
                {
                    if(flag)
                    {
                        Toast.makeText(CustomerSignup.this, "Signed Up Successsfully", Toast.LENGTH_LONG).show();

                        new PostData().execute(c);
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
                    "\",\"locality\":\"" + c.getLoc() +
                    "\",\"city\":\"" + c.getCity() +
                    "\",\"zip\":\"" + c.getZip() +
                    "\",\"state\":\"" + c.getState() +"\"}";
            hh.postHTTPData(json);
            return false;
        }
    }


    public class GetContactsAsyncTask extends AsyncTask<Customer, Void, Boolean> {
        String server_output = null;
        String temp_output = null;
        String u;

        public GetContactsAsyncTask(String u)
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
}

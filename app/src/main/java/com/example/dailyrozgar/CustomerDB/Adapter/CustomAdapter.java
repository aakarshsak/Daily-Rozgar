package com.example.dailyrozgar.CustomerDB.Adapter;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.CustomerDB.Common;
import com.example.dailyrozgar.MyDB.AcceptDB.Accept;
import com.example.dailyrozgar.MyDB.Helper;
import com.example.dailyrozgar.MyDB.RequestDB.Request;
import com.example.dailyrozgar.R;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Request> dataset;
    View view;
    public CustomAdapter(ArrayList<Request> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_cardview,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView name=holder.name;
        TextView location=holder.location;
        Button rejButton = holder.rejButton;
        RelativeLayout rejButtonRel=holder.rejButtonRel;
        Button accButton = holder.accButton;
        RelativeLayout accRel=holder.accButtonRel;
        TextView time=holder.time;

        GetContactsAsyncTask task=new GetContactsAsyncTask(dataset.get(position).getCustomer());
        Customer c=null;
        try{
            c=task.execute().get();
        }catch(Exception e){e.printStackTrace();}

        name.setText(c.getFname()+" "+c.getLname());
        location.setText(c.getLoc());
        time.setText(dataset.get(position).getFrom()+ " - "+ dataset.get(position).getTo());

        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Helper helper=new Helper(view.getContext());
                helper.deleteRequest(dataset.get(position));
                Accept accept=new Accept();
                accept.setCustomer(dataset.get(position).getCustomer());
                accept.setWorker(dataset.get(position).getWorker());
                accept.setFrom(dataset.get(position).getFrom());
                accept.setTo(dataset.get(position).getTo());
                helper.addAccept(accept);

                accRel.setVisibility(View.INVISIBLE);
                rejButtonRel.setVisibility(View.INVISIBLE);
            }
        });
        rejButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accRel.setVisibility(View.INVISIBLE);
                rejButtonRel.setVisibility(View.INVISIBLE);

                Helper helper=new Helper(view.getContext());
                helper.deleteRequest(dataset.get(position));



            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,location,time;
        RelativeLayout accButtonRel,rejButtonRel;
        Button accButton,rejButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.customerHomeName);
            this.location = itemView.findViewById(R.id.customerLocation);
            this.time=itemView.findViewById(R.id.selectedTime);
            this.accButtonRel=itemView.findViewById(R.id.accButtonRel);
            this.rejButtonRel=itemView.findViewById(R.id.rejButtonRel);
            this.rejButton=itemView.findViewById(R.id.rejButton);
            this.accButton=itemView.findViewById(R.id.accButton);
            itemView.setTag(itemView);
        }
    }


    public static class GetContactsAsyncTask extends AsyncTask<Customer, Void, Customer> {
        String server_output = null;
        String temp_output = null;
        String u;
        Customer temp=null;

        public GetContactsAsyncTask(String u)
        {
            this.u=u;
        }
        @Override
        protected Customer doInBackground(Customer... arg0) {

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
                    if(u.equals(userObj.get("username").toString())) {
                        temp = new Customer();
                        temp.setUsername(userObj.get("username").toString());
                        temp.setPassword(userObj.get("password").toString());
                        temp.setFname(userObj.get("fname").toString());
                        temp.setLoc(userObj.get("area").toString());
                        temp.setLname(userObj.get("lname").toString());
                        temp.setCity(userObj.get("city").toString());
                        temp.setStreet(userObj.get("street").toString());
                        temp.setFlat(userObj.get("flat").toString());
                        temp.setLandmark(userObj.get("landmark").toString());
                        temp.setZip(userObj.get("zip").toString());
                        temp.setState(userObj.get("state").toString());
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

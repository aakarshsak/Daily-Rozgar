package com.example.dailyrozgar.WorkerDB.Adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dailyrozgar.MyDB.AcceptDB.Accept;
import com.example.dailyrozgar.MyDB.Helper;
import com.example.dailyrozgar.MyDB.RequestDB.Request;
import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkerDB.Class.Worker;


import org.w3c.dom.Text;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Worker> dataset;
    String username;
    View view;

    public CustomAdapter(ArrayList<Worker> dataset,String username) {
        this.dataset = dataset;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.workers_list_card_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView name=holder.name;
        TextView price=holder.price;
        TextView location=holder.location;
        Button reqButton = holder.reqButton;
        RelativeLayout buttonRel=holder.buttonRel;
        ImageView imgC=holder.c;
        ImageView imgP=holder.p;
        Button reqCon=holder.reqConfirm;
        Button reqRej=holder.reqReject;
        RelativeLayout timeRel=holder.timeRel;
        TimePicker timePicker=holder.timePicker;
        TextView t=holder.t;
        TextView selTime=holder.selTime;
        RelativeLayout norRel=holder.norRel;
        TextView age=holder.age;
        TextView accepted=holder.accepted;
        Button can=holder.cancel;
        RelativeLayout aRel=holder.aRel;


        name.setText(dataset.get(position).getFirst() + " " + dataset.get(position).getLast());
        price.setText(dataset.get(position).getBase());
        location.setText(dataset.get(position).getLoc());
        age.setText(dataset.get(position).getAge());
        imgC.setVisibility(View.INVISIBLE);
        imgP.setVisibility(View.VISIBLE);
        timeRel.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        selTime.setVisibility(View.INVISIBLE);
        aRel.setVisibility(View.INVISIBLE);
        can.setEnabled(false);


        Helper oldHelper=new Helper(view.getContext());

        Accept a=oldHelper.getAccept(username,dataset.get(position).getUsername());
        if(a!=null){
            buttonRel.setVisibility(View.INVISIBLE);
            reqButton.setEnabled(false);
            aRel.setVisibility(View.VISIBLE);
            can.setEnabled(true);

            can.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(view.getContext());
                    alertDialog.setTitle("Cancellation of Metting.");
                    alertDialog.setMessage("Do you want to continue ?");
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            oldHelper.deleteAccept(a);
                            dialog.dismiss();
                        }
                    });

                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            });


        }
        else{
        Request r=oldHelper.getRequest(username,dataset.get(position).getUsername());
        if(r!=null){

            imgC.setVisibility(View.VISIBLE);
            imgP.setVisibility(View.INVISIBLE);
            reqButton.setEnabled(false);
            t.setVisibility(View.VISIBLE);
            selTime.setVisibility(View.VISIBLE);
            selTime.setText(r.getFrom()+" - "+r.getTo());

        }}

//        MyDatabase myDatabase=new MyDatabase(view.getContext());
//        Cursor resultSet =myDatabase.getParticularRequest(dataset.get(position).getUsername()+"."+username);
//        Request req0=new Request();
//        if(resultSet.getCount()>0){
//            req0.setTo(resultSet.getString(2));
//            req0.setFrom(resultSet.getString(1));
//            imgP.setVisibility(View.INVISIBLE);
//            imgC.setVisibility(View.VISIBLE);
//            reqButton.setEnabled(false);
//            selTime.setVisibility(View.VISIBLE);
//            selTime.setText(req0.getFrom()+" - "+req0.getTo());
//            t.setVisibility(View.INVISIBLE);
//        }

            reqButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeRel.setVisibility(View.VISIBLE);
                    norRel.setVisibility(View.INVISIBLE);
                    int hour = timePicker.getCurrentHour();
                    int min = timePicker.getCurrentMinute();
                    String format;
                    int hour2;
                    if (hour == 0) {
                        hour += 12;
                        format = "AM";
                    } else if (hour == 12) {
                        format = "PM";
                    } else if (hour > 12) {
                        hour -= 12;
                        format = "PM";
                    } else {
                        format = "AM";
                    }

                    String str;
                    if(min<10){
                        str=new String("0"+min);
                    }
                    else{
                        str=new String(""+min);
                    }
                    String timeTo =
                            new String(hour + 2+" : " + str + " " + format);
                    String timeFrom =
                            new String((hour ) + " : " + str + " " + format);

                    reqCon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Request req = new Request();
                            req.setWorker(dataset.get(position).getUsername());
                            req.setCustomer(username);
                            req.setTo(timeTo);
                            req.setFrom(timeFrom);

                            Helper helper=new Helper(view.getContext());
                            Log.d("Insert:","Inserting");
                            helper.addRequest(req);


                            timeRel.setVisibility(View.INVISIBLE);
                            norRel.setVisibility(View.VISIBLE);
                            imgC.setVisibility(View.VISIBLE);
                            imgP.setVisibility(View.INVISIBLE);
                            reqButton.setEnabled(false);
                            t.setVisibility(View.VISIBLE);
                            selTime.setVisibility(View.VISIBLE);
                            selTime.setText(timeFrom + " - " + timeTo);


                        }
                    });


                    reqRej.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timeRel.setVisibility(View.INVISIBLE);
                            norRel.setVisibility(View.VISIBLE);

                        }
                    });


                }
            });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,location,selTime,t,accepted;
        Button reqButton;
        RelativeLayout buttonRel;
        ImageView c,p;
        TimePicker timePicker;
        Button reqConfirm;
        Button reqReject;
        TextView age;
        Button cancel;
        RelativeLayout timeRel,norRel,aRel;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.workerName);
            this.price = itemView.findViewById(R.id.workerPrice);
            this.location = itemView.findViewById(R.id.workerLocation);
            this.c=itemView.findViewById(R.id.reqCheck);
            this.p=itemView.findViewById(R.id.reqPerson);
            this.buttonRel=itemView.findViewById(R.id.reqButtonRel);
            this.reqButton=itemView.findViewById(R.id.reqButton);
            this.timePicker=itemView.findViewById(R.id.timePicker);
            this.timeRel=itemView.findViewById(R.id.timeRel);
            this.reqConfirm=itemView.findViewById(R.id.reqConfirm);
            this.reqReject=itemView.findViewById(R.id.reqReject);
            this.selTime=itemView.findViewById(R.id.selectedTimeValue);
            this.t=itemView.findViewById(R.id.selectedTime);
            this.norRel=itemView.findViewById(R.id.norRel);
            this.age=itemView.findViewById(R.id.workerAge);
            this.aRel=itemView.findViewById(R.id.acceptedRel);
            this.cancel=itemView.findViewById(R.id.cancel);
            this.accepted=itemView.findViewById(R.id.accepted);
            itemView.setTag(itemView);
        }
    }







}

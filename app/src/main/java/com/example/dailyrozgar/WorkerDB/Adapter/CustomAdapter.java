package com.example.dailyrozgar.WorkerDB.Adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkerDB.Class.Worker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Worker> dataset;
    String username;
    public CustomAdapter(ArrayList<Worker> dataset,String username) {
        this.dataset = dataset;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.workers_list_card_view,parent,false);
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

        selTime.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        timeRel.setVisibility(View.INVISIBLE);
        imgC.setVisibility(View.INVISIBLE);
        String prof=dataset.get(position).getProf();
        name.setText(dataset.get(position).getFirst() + " " + dataset.get(position).getLast());
        price.setText(dataset.get(position).getBase());
        location.setText(dataset.get(position).getLoc());

        reqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeRel.setVisibility(View.VISIBLE);
                buttonRel.setVisibility(View.INVISIBLE);
                int  hour=timePicker.getCurrentHour();
                int  min= timePicker.getCurrentMinute();
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

                String timeTo=new String(hour+" : "+min+" "+format);
                String timeFrom=new String((hour+2)+" : "+min+" "+format);

                reqCon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeRel.setVisibility(View.INVISIBLE);
                        buttonRel.setVisibility(View.VISIBLE);
                        imgC.setVisibility(View.VISIBLE);
                        imgP.setVisibility(View.INVISIBLE);
                        reqButton.setEnabled(false);
                        t.setVisibility(View.VISIBLE);
                        selTime.setVisibility(View.VISIBLE);
                        selTime.setText(timeFrom+" - "+timeTo);
                    }
                });

                reqRej.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeRel.setVisibility(View.INVISIBLE);
                        buttonRel.setVisibility(View.VISIBLE);

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
        TextView name,price,location,selTime,t;
        Button reqButton;
        RelativeLayout buttonRel;
        ImageView c,p;
        TimePicker timePicker;
        Button reqConfirm;
        Button reqReject;
        RelativeLayout timeRel;
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
            itemView.setTag(itemView);
        }
    }
}

package com.example.dailyrozgar.WorkerDB.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkerDB.Class.Worker;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Worker> dataset;

    public CustomAdapter(ArrayList<Worker> dataset) {
        this.dataset = dataset;
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
        TextView contact=holder.contact;

        String prof=dataset.get(position).getProf();
        name.setText(dataset.get(position).getFirst() + " " + dataset.get(position).getLast());
        price.setText(dataset.get(position).getBase());
        location.setText(dataset.get(position).getLoc());
        contact.setText(dataset.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,location,contact;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.workerName);
            this.price = itemView.findViewById(R.id.workerPrice);
            this.location = itemView.findViewById(R.id.workerLocation);
            this.contact =  itemView.findViewById(R.id.workerContact);

            itemView.setTag(itemView);
        }
    }
}

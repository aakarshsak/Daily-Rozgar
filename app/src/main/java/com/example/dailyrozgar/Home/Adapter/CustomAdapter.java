package com.example.dailyrozgar.Home.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailyrozgar.Home.Model.DataModel;
import com.example.dailyrozgar.MainActivity;
import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkersListActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataset;

    public CustomAdapter(ArrayList<DataModel> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_cardview,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView textViewTitle=holder.textViewTitle;
        ImageView img=holder.imageView;
        TextView textViewDesc=holder.textViewDesc;

        textViewTitle.setText(dataset.get(position).getTitle().toString());
        img.setImageResource(dataset.get(position).getImage());
        textViewDesc.setText(dataset.get(position).getDescription().toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView textViewTitle,textViewDesc;
        ImageView imageView;
        MainActivity mainActivity;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.homeCardText);
            this.imageView = itemView.findViewById(R.id.image);
            this.textViewDesc = itemView.findViewById(R.id.homeCardDesc);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(getAdapterPosition())
                    {
                        case 0: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;
                        case 1: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;
                        case 2: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;
                        case 3: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;
                        case 4: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;
                        case 5: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;
                        case 6: v.getContext().startActivity(new Intent(v.getContext(), WorkersListActivity.class));break;

                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}

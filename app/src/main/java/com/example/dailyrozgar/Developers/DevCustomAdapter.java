package com.example.dailyrozgar.Developers;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailyrozgar.Home.Adapter.CustomAdapter;
import com.example.dailyrozgar.R;

import java.util.ArrayList;

public class DevCustomAdapter extends RecyclerView.Adapter<DevCustomAdapter.MyViewHolder>{

    private ArrayList<DevDataModel> datasets;

    public DevCustomAdapter(ArrayList<DevDataModel> datasets) {
        this.datasets = datasets;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.startscreen_card_view,parent,false);
        DevCustomAdapter.MyViewHolder myViewHolder=new DevCustomAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView devName=holder.devName;
        ImageView devImg=holder.devImage;
        TextView devDesc=holder.devDesc;

        devName.setText(datasets.get(position).getName().toString());
        devImg.setImageResource(datasets.get(position).getImg());
        devDesc.setText(datasets.get(position).getDesc().toString());
    }

    @Override
    public int getItemCount() {
        return datasets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView devImage;
        TextView devName,devDesc;
        public MyViewHolder(View itemView) {
            super(itemView);

            this.devImage=itemView.findViewById(R.id.devImage);
            this.devName=itemView.findViewById(R.id.devName);
            this.devDesc=itemView.findViewById(R.id.devDesc);
        }
    }
}

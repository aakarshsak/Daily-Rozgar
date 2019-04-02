package com.example.dailyrozgar.WorkerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailyrozgar.CustomerDB.Adapter.CustomAdapter;
import com.example.dailyrozgar.Home.Model.DataModel;
import com.example.dailyrozgar.MyDB.MyDatabase;
import com.example.dailyrozgar.MyDB.RequestDB.Request;
import com.example.dailyrozgar.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    static ArrayList<Request> datamodels;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2,container,false);
        String username=getArguments().getString("username");


        layoutManager=new LinearLayoutManager(getActivity());
        cardView=view.findViewById(R.id.customerListCardView);
        //Recycler View setup
        recyclerView=view.findViewById(R.id.customerListRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        MyDatabase sdb=new MyDatabase(view.getContext());
        datamodels=sdb.getAllRequests(username);

        adapter=new CustomAdapter(datamodels);
        recyclerView.setAdapter(adapter);

        return view;
    }

}

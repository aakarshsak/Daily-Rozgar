package com.example.dailyrozgar.CustomerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailyrozgar.Home.Adapter.CustomAdapter;
import com.example.dailyrozgar.Home.Model.DataModel;
import com.example.dailyrozgar.Home.Model.MyData;
import com.example.dailyrozgar.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    static ArrayList<DataModel> datamodels;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        String username=getArguments().getString("username");
        recyclerView=view.findViewById(R.id.homeRecyclerView);
        recyclerView.setHasFixedSize(true);
        cardView=view.findViewById(R.id.homeCardView);



        layoutManager=new LinearLayoutManager(getActivity());
        //Recycler View setup
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        datamodels=new ArrayList<>();
        for(int i=0;i<6;i++)
        {
            datamodels.add(new DataModel(MyData.titlearray[i],MyData._id[i],MyData.images[i],MyData.description[i]));
        }
        //Attaching to the adapter
        adapter=new CustomAdapter(datamodels,username);
        recyclerView.setAdapter(adapter);


        return view;
    }

}

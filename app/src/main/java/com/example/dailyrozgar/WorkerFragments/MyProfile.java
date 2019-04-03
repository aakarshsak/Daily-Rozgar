package com.example.dailyrozgar.WorkerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dailyrozgar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {


    public MyProfile() {
        // Required empty public constructor
    }


    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile2,container,false);

        


        return view;
    }


    public boolean loadFragment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return false;

    }
}

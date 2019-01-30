package com.example.dailyrozgar.WorkerFragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkerDB.Common;
import com.example.dailyrozgar.WorkerDB.HttpDataHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerEditProfile extends Fragment {


    public WorkerEditProfile() {
        // Required empty public constructor
    }


    Button submit,reset;
    EditText first,last,phone,loc,city,state,zip,prof,base;
    RadioButton sex,nego;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_worker_edit_profile,container,false);

        submit=view.findViewById(R.id.workerSubmitButton);
        reset=view.findViewById(R.id.workerResetButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)

                    break;
            case R.id.female:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    class PostData extends AsyncTask<String,String,String>{
        ProgressDialog pd=new ProgressDialog(getContext());
        String userName;

        public PostData(String userName) {
            this.userName = userName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please Wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            HttpDataHandler hh = new HttpDataHandler();
            String json = "{\"user\":\"" + userName + "\"}";
            hh.postHTTPData(urlString, json);
            return "";
        }


    }

}

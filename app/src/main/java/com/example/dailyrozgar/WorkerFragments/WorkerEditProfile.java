package com.example.dailyrozgar.WorkerFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.example.dailyrozgar.WorkerDB.Common;
import com.example.dailyrozgar.WorkerDB.HttpDataHandler;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerEditProfile extends Fragment {


    public WorkerEditProfile() {
        // Required empty public constructor
    }


    Button submit,reset;
    EditText first,last,phone,loc,city,state,zip,prof,base;
    TextView t;
    RadioButton male,female,yes,no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_worker_edit_profile,container,false);

        submit=view.findViewById(R.id.workerSubmitButton);
        reset=view.findViewById(R.id.workerResetButton);

        first=view.findViewById(R.id.workerFirstName);
        last=view.findViewById(R.id.workerLastName);
        phone=view.findViewById(R.id.workerContact);
        loc=view.findViewById(R.id.workerLocality);
        city=view.findViewById(R.id.workerCity);
        state=view.findViewById(R.id.workerState);
        zip=view.findViewById(R.id.workerZip);
        prof=view.findViewById(R.id.workerProfession);
        base=view.findViewById(R.id.workerBasePrice);

        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);
        yes=view.findViewById(R.id.negoYes);
        no=view.findViewById(R.id.negoNo);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Worker worker=new Worker();
                worker.setFirst(first.getText().toString());
                worker.setLast(last.getText().toString());
                worker.setPhone(phone.getText().toString());
                worker.setLoc(loc.getText().toString());
                worker.setCity(city.getText().toString());
                worker.setState(state.getText().toString());
                worker.setZip(zip.getText().toString());
                worker.setProf(prof.getText().toString());

                new PostData().execute(worker);
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
                    //worker.setSex("Male");
                break;
            case R.id.female:
                if (checked)
                    // Ninjas rule
                    //worker.setSex("Female");
                break;
        }

        switch(view.getId())
        {
            case R.id.negoNo:
                if(checked)
                    //worker.setBase(base.getText().toString());
                break;
            case R.id.negoYes:
                if(checked)
                    //worker.setBase(base.getText().toString()+"( NEGOTIABLE )");
                break;
        }
    }

    class PostData extends AsyncTask<Object,Void,Boolean>
    {


        @Override
        protected Boolean doInBackground(Object... params) {
            Worker w = (Worker) params[0];
            HttpDataHandler hh=new HttpDataHandler();
            String json = "{\"first_name\":\"" + w.getFirst() +
                        "\",\"last_name\":\"" + w.getLast() +
                        "\",\"contact_no\":\"" + w.getPhone() +
                        "\",\"locality\":\"" + w.getLoc() +
                        "\",\"city\":\"" + w.getCity() +
                        "\",\"state\":\"" + w.getState() +
                        "\",\"zip\":\"" + w.getZip() +
                        "\",\"profession\":\"" + w.getProf() +
                        "\",\"base_price\":\"" + w.getBase() + "\"}";
            hh.postHTTPData(json);
            return false;
            //Log.d("contact", ""+w);

//            try {
//                Common c = new Common();
//                URL url = new URL(c.getAddressAPI());
//
//                HttpURLConnection connection = (HttpURLConnection) url
//                        .openConnection();
//                connection.setRequestMethod("PUT");
//                connection.setDoOutput(true);
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Accept", "application/json");
//
//                OutputStreamWriter osw = new OutputStreamWriter(
//                        connection.getOutputStream());
//                String json = "{\"first_name\":\"" + w.getFirst() + "\"}";
////                        "\",\"last_name\":\"" + w.getLast() +
////                        "\",\"contact_no\":\"" + w.getPhone() +
////                        "\",\"locality\":\"" + w.getLoc() +
////                        "\",\"city\":\"" + w.getCity() +
////                        "\",\"state\":\"" + w.getState() +
////                        "\",\"zip\":\"" + w.getZip() +
////                        "\",\"profession\":\"" + w.getProf() +
////                        "\".\"base_price\":\"" + w.getBase() + "\"}";
//
//                osw.write(json);
//                osw.flush();
//                osw.close();
//
//                if (connection.getResponseCode() < 205) {
//                    return true;
//                } else {
//                    return false;
//                }
//
//            } catch (Exception e) {
//                e.getMessage();
//                Log.d("Got error", e.getMessage());
//                return false;
//            }
        }
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

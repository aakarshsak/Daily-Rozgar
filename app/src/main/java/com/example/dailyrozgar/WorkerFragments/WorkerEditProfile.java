package com.example.dailyrozgar.WorkerFragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dailyrozgar.R;
import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.example.dailyrozgar.WorkerDB.HttpDataHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerEditProfile extends Fragment {


    public WorkerEditProfile() {
        // Required empty public constructor
    }


    Button submit,reset;
    EditText first,last,phone,base,dob;//,city,state,zip
    Spinner prof,loc;
    TextView t;
    RadioButton male,female;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_worker_edit_profile,container,false);
        Worker worker=new Worker();
        submit=view.findViewById(R.id.workerSubmitButton);
        reset=view.findViewById(R.id.workerResetButton);

        first=view.findViewById(R.id.workerFirstName);
        last=view.findViewById(R.id.workerLastName);
        phone=view.findViewById(R.id.workerContact);
        loc=view.findViewById(R.id.workerLocality);
//        city=view.findViewById(R.id.workerCity);
//        state=view.findViewById(R.id.workerState);
//        zip=view.findViewById(R.id.workerZip);
        prof=view.findViewById(R.id.workerProfession);
        base=view.findViewById(R.id.workerBasePrice);
        dob=view.findViewById(R.id.dob);

        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.profession_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prof.setAdapter(adapter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(view.getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        loc.setAdapter(adapter1);


        RadioGroup grp1=view.findViewById(R.id.grp1);
        grp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.male: worker.setForSex(1);break;
                    case R.id.female: worker.setForSex(0);break;
                }
            }
        });
        prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                worker.setProf(prof.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                worker.setLoc(loc.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                worker.setFirst(first.getText().toString());
                worker.setLast(last.getText().toString());
                worker.setPhone(phone.getText().toString());
                //worker.setLoc(loc.getText().toString());
                worker.setCity("Tumkur");
                worker.setState("Karnataka");
                worker.setZip("572103");
                //worker.setProf(prof.getText().toString());
                worker.setBase(base.getText().toString());
                worker.setAge(dob.getText().toString());

                if(worker.getForSex()==1)
                    worker.setSex("MALE");
                else
                    worker.setSex("FEMALE");

                new PostData().execute(worker);
            }
        });

        return view;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    class PostData extends AsyncTask<Object,Void,Boolean>
    {
        @Override
        protected Boolean doInBackground(Object... params) {
            Worker w = (Worker) params[0];
            HttpDataHandler hh=new HttpDataHandler();
            String json = "{\"firstname\":\"" + w.getFirst() +
                        "\",\"lastname\":\"" + w.getLast() +
                        "\",\"gender\":\"" + w.getSex() +
                        "\",\"contactno\":\"" + w.getPhone() +
                        "\",\"age\":\"" + w.getAge() +
                        "\",\"area\":\"" + w.getLoc() +
                        "\",\"city\":\"" + w.getCity() +
                        "\",\"state\":\"" + w.getState() +
                        "\",\"zcode\":\"" + w.getZip() +
                        "\",\"occupation\":\"" + w.getProf() +
                        "\",\"bamnt\":\"" + w.getBase() + "\"}";
            hh.postHTTPData(json);
            return false;
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

package com.example.dailyrozgar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyrozgar.MyDB.Helper;
import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.example.dailyrozgar.WorkerDB.HttpDataHandler;


public class WorkerSignup extends AppCompatActivity {

    Button submit,reset;
    EditText first,last,phone,base,dob,username,password,repass;//,city,state,zip
    Spinner prof,loc;
    TextView t;
    RadioButton male,female;
    Boolean flag,flag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_signup);

        Worker worker=new Worker();
        submit=findViewById(R.id.workerSubmitButton);

        first=findViewById(R.id.workerFirstName);
        last=findViewById(R.id.workerLastName);
        phone=findViewById(R.id.workerContact);
        loc=findViewById(R.id.workerLocality);
//        city=view.findViewById(R.id.workerCity);
//        state=view.findViewById(R.id.workerState);
//        zip=view.findViewById(R.id.workerZip);

        username=findViewById(R.id.workerUsername);
        password=findViewById(R.id.workerPassword);
        repass=findViewById(R.id.workerRePassword);
        prof=findViewById(R.id.workerProfession);
        base=findViewById(R.id.workerBasePrice);
        dob=findViewById(R.id.dob);

        male=findViewById(R.id.male);
        female=findViewById(R.id.female);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profession_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prof.setAdapter(adapter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        loc.setAdapter(adapter1);


        RadioGroup grp1=findViewById(R.id.grp1);
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
                String r=repass.getText().toString();
                worker.setPassword(password.getText().toString());
                worker.setUsername(username.getText().toString());
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

                StartScreen.GetContactsAsyncTaskForCustomer task = new StartScreen.GetContactsAsyncTaskForCustomer(worker.getUsername());
                StartScreen.GetContactsAsyncTaskForWorker task2 =new StartScreen.GetContactsAsyncTaskForWorker(worker.getUsername());
                try {
                    flag = task.execute().get();
                    flag2=task2.execute().get();
                }catch(Exception e){e.printStackTrace();}
                if(!flag || !flag2)
                {
                    Toast.makeText(WorkerSignup.this, "username already exist", Toast.LENGTH_SHORT).show();
                }

                if(worker.getPassword().equals(r))
                {
                    if(flag && flag2)
                    {
                        Toast.makeText(WorkerSignup.this, "Signed Up Successsfully", Toast.LENGTH_LONG).show();

                        new PostData().execute(worker);

                        Helper helper=new Helper(WorkerSignup.this);
                        helper.updateCurrent(worker.getUsername(),helper.getCurrent(1),2);
//                        Intent intent=new Intent(CustomerSignup.this,CustomerMainActivity.class);
//                        intent.putExtra("name",f+l);
//                        startActivity(intent);
                        Intent i=new Intent(WorkerSignup.this,WorkerMainActivity.class);
                        i.putExtra("Worker",worker);
                        startActivity(i);
                    }
                }
                else
                {
                    Toast.makeText(WorkerSignup.this, "Password Did Not Match", Toast.LENGTH_SHORT).show();
                }


            }
        });
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
            String json = "{\"username\":\"" + w.getUsername() +
                    "\",\"password\":\"" + w.getPassword() +
                    "\",\"firstname\":\"" + w.getFirst() +
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

}

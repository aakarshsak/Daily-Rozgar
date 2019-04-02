package com.example.dailyrozgar;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.dailyrozgar.WorkerDB.Class.Worker;
import com.example.dailyrozgar.WorkerFragments.History;
import com.example.dailyrozgar.WorkerFragments.Home;
import com.example.dailyrozgar.WorkerFragments.MyProfile;

public class WorkerMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_main);


        Intent i = getIntent();
        Worker w=(Worker) i.getSerializableExtra("Worker");

        Bundle bundle=new Bundle();
        bundle.putString("Username",w.getUsername());
        Home home=new Home();
        home.setArguments(bundle);
        loadFragment(home);


        toolbar=findViewById(R.id.toolBar);
        drawerLayout=findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(w.getFirst()+ " "+w.getLast());

        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                Bundle bundle=new Bundle();
                bundle.putString("Username",w.getUsername());

                switch (item.getItemId())
                {
                    case R.id.home:fragment=new Home();break;
                    case R.id.myProfile:fragment=new MyProfile();break;
                    case R.id.history:fragment=new History();break;
                }
                fragment.setArguments(bundle);
                return loadFragment(fragment);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }

        return false;
    }
}

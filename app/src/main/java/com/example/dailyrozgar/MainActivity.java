package com.example.dailyrozgar;

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
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.dailyrozgar.CustomerFragments.History;
import com.example.dailyrozgar.CustomerFragments.Home;
import com.example.dailyrozgar.CustomerFragments.MyProfile;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragment initialisation
        loadFragment(new Home());

        //initializing all the variables needed
        toolbar=findViewById(R.id.toolBar);
        drawerLayout=findViewById(R.id.drawerLayout);


        //merging toolbar with action bar
        setSupportActionBar(toolbar);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        //Drawer NAvigation view setup
        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:loadFragment(new Home());break;
                    case R.id.myProfile:loadFragment(new MyProfile());break;
                    case R.id.history:loadFragment(new History());break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //Bottom navigation switching on selection
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment=null;
                switch(item.getItemId())
                {
                    case R.id.myProfile:fragment=new MyProfile();break;
                    case R.id.home:fragment=new Home();break;
                    case R.id.history:fragment=new History();break;
                }

                return loadFragment(fragment);
            }
        });

    }

    //Entries for tab bar icon
    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.entries,menus);

        return super.onCreateOptionsMenu(menus);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    //load function for setup of fragments
    public boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            return true;
        }
        return false;
    }


}

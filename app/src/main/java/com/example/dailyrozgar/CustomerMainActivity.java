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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dailyrozgar.CustomerDB.Class.Customer;
import com.example.dailyrozgar.CustomerFragments.History;
import com.example.dailyrozgar.CustomerFragments.Home;
import com.example.dailyrozgar.CustomerFragments.MyProfile;

import org.w3c.dom.Text;


public class CustomerMainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView test;
    Customer c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_main);

        Intent i = getIntent();
        c =(Customer)i.getSerializableExtra("Customer");


//        Customer c=new Customer();
//        c.setFname("Aakarsh");
//        c.setLname("SInha");

        //fragment initialisation
        Bundle bundle=new Bundle();
        bundle.putString("username",c.getUsername());
        Home home=new Home();
        home.setArguments(bundle);
        loadFragment(home);

        //initializing all the variables needed
        toolbar=findViewById(R.id.toolBar);
        drawerLayout=findViewById(R.id.drawerLayout);


        //merging toolbar with action bar
        setSupportActionBar(toolbar);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(c.getFname()+" "+c.getLname());


        //Drawer NAvigation view setup
        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle b=new Bundle();
                b.putString("username",c.getUsername());
                Fragment h=null;
                switch(item.getItemId())
                {
                    case R.id.home:h=new Home();h.setArguments(b);loadFragment(h);break;
                    case R.id.myProfile: h=new MyProfile();h.setArguments(b);loadFragment(h);break;
                    case R.id.history:h=new History();h.setArguments(b);loadFragment(h);break;
                    case R.id.logout:startActivity(new Intent(CustomerMainActivity.this,CustomerLogin.class));

                }

                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });


//        //Bottom navigation switching on selection
//        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                Bundle b=new Bundle();
//                b.putString("username",c.getUsername());
//                Fragment h=null;
//                switch(item.getItemId())
//                {
//                    case R.id.home:h=new Home();break;
//                    case R.id.myProfile: h=new MyProfile();break;
//                    case R.id.history:h=new History();break;
//                    case R.id.logout:startActivity(new Intent(CustomerMainActivity.this,CustomerLogin.class));
//                }
//                h.setArguments(b);
//
//                return loadFragment(h);
//            }
//        });

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

package com.example.test;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.test.OrderCart.Cart;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout tabbar;
    private ViewPager viewPager;
    private Toolbar toolbar;


    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // this is for not showng the status bar above the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        tabbar = findViewById(R.id.tab);
        viewPager = findViewById(R.id.view_pager);

        //calling navigation view for the click listners
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //For Tab view this View pager can be used
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
       viewPagerAdapter.addfragment(new Tab1(), "Veg");
      viewPagerAdapter.addfragment(new Tab2(), "Non-Veg");
        viewPagerAdapter.addfragment(new BlankFragment(), "Biryani Combos");

        viewPager.setAdapter(viewPagerAdapter);
        tabbar.setupWithViewPager(viewPager);

        setupToolbar();



    }


    public void setupToolbar() {
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }


    }

    // this is for openning the new activity from drawer menu
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.about:
                Intent i = new Intent(this,AboutUs.class);
                onBackPressed();
                startActivity(i);

                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact:
                Intent intent =new Intent(this,Contactus.class);
                onBackPressed();
                startActivity(intent);

                Toast.makeText(this, "Contact Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Intent intent1 = new Intent(this,Customer_Profile.class);
                onBackPressed();
                startActivity(intent1);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cart,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this,Cart.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);

    }
}


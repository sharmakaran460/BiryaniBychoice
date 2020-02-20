package com.example.test;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.WindowManager;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.test.OrderCart.Cart;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FusedLocationProviderClient client;
    private TabLayout tabbar;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText locText;
    private ImageButton manageAddress;

    Location loc;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageAddress =findViewById(R.id.locationButton);
        toolbar = findViewById(R.id.toolbar);
        locText =findViewById(R.id.locationText);
        client = LocationServices.getFusedLocationProviderClient(this);

        //requestPermission();
                requestPerm();


        manageAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Want to change Address");
                dialog.setMessage("Please select the Manage Address option to add Your address manually");
                dialog.setPositiveButton("Manage Address", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which ) {

                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }
                }).create();
                dialog.show();
            }
        });



        // this is for not showng the status bar above the app
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        tabbar = findViewById(R.id.tab);
        viewPager = findViewById(R.id.view_pager);

        //calling navigation view for the click listners
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //For Tab view this View pager can be used
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addfragment(new Tab1(), "All");
        viewPagerAdapter.addfragment(new Tab2(), "Veg");
        viewPagerAdapter.addfragment(new BlankFragment(), "Nonveg");

        viewPager.setAdapter(viewPagerAdapter);
        tabbar.setupWithViewPager(viewPager);

        setupToolbar();


    }
    public void requestPerm(){
        if( ActivityCompat.checkSelfPermission(MainActivity.this,ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_FINE_LOCATION},1);
            Toast.makeText(this, "permission nai hai be", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "permission hai", Toast.LENGTH_SHORT).show();
            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                loc =location;
               if (location!=null){
                   System.out.println("location mai ye aa raha hai"+ location.toString());
                   //Toast.makeText(MainActivity.this, loc.toString(), Toast.LENGTH_SHORT).show();
                   getaddress();
               }else {
                   System.out.println("mar ja kutte");
               }
                }
            });
        }

    }

public void getaddress(){

        List<Address> addresses =null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    try {
        addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
        System.out.println("ye address mai hai"+addresses);
    } catch (IOException e) {
        System.out.println("han service ki exception me ye hai"+addresses);
        e.printStackTrace();
    }catch (IllegalArgumentException e){
        e.printStackTrace();
        System.out.println("bahi illegal argument fek raha hai");
    }


    if (addresses ==null || addresses.size() == 0){
        System.out.println("address kahli hia bc");
    }else {
        Address address = addresses.get(0);

        ArrayList<String> addressFragment = new ArrayList<>();

        for (int i=0;i<=address.getMaxAddressLineIndex(); i++){
            addressFragment.add(address.getAddressLine(i));
        }
        System.out.println("ye address aa rahae hai"+addressFragment);
        locText.setText(addressFragment.get(0));
       // Toast.makeText(this, addressFragment.get(0), Toast.LENGTH_LONG).show();
    }
}


    public void setupToolbar() {
        drawerLayout = findViewById(R.id.drawer);
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


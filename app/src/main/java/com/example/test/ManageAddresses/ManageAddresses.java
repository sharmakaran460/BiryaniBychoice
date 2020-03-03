package com.example.test.ManageAddresses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.example.test.MainActivity;
import com.example.test.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.PACKAGE_USAGE_STATS;

public class ManageAddresses extends AppCompatActivity implements  OnMapReadyCallback {

    String TAG ="MainActicity";
    boolean mlocationpermissiongranted= false;
    static final int ERROR_REQUESTCODE=9001;
    static final int Location_permission_request_code=1234;
     GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_addresses);

        if (isServiceOK()){
            init();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }
    private void init() {
        getLocation();
    }

    public void initMap(){
    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

    supportMapFragment.getMapAsync(ManageAddresses.this);

    }

    public Boolean isServiceOK (){
        Log.d(TAG,"isServiceOK: checking google service version");

        int available =GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ManageAddresses.this);

        if (available == ConnectionResult.SUCCESS){
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isService oK: an error  occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ManageAddresses.this,available, ERROR_REQUESTCODE);
            dialog.show();
        }else {
            Toast.makeText(this, "Sorry we cant make the map request", Toast.LENGTH_SHORT).show();
        }

        return false;
    }




    public void getLocation(){

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mlocationpermissiongranted =true;

            }else {
                ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mlocationpermissiongranted =false;

        switch (requestCode){
            case Location_permission_request_code:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    for (int i=0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mlocationpermissiongranted =false;
                            return;
                        }
                    }
                    mlocationpermissiongranted =true;
                    initMap();
                }

            }
        }
    }


}

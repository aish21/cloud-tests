package com.example.nmeaandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.OnNmeaMessageListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.Console;

public class MainActivity extends AppCompatActivity implements LocationListener, GpsStatus.NmeaListener {

    //for call back
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION_ID    =   0x10;

    public LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initGPSgettingLogic();
    }

    public void initGPSgettingLogic(){

        this.locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gpsEnabled) {

            //GPS ON
            Log.d("NMEA_APP", getClass().getName()+":"+"GPS ON :)");



            //Permissiosns <https://developer.android.com/training/permissions/requesting#java>

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                Log.d("NMEA_APP", getClass().getName()+":"+"Request Permission ");

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION_ID);

            }else {

                Log.d("NMEA_APP", getClass().getName()+":"+"addNmeaListener ");

                //LocationProvider provider =
                //        locationManager.getProvider(LocationManager.GPS_PROVIDER);

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,10,this);

                locationManager.addNmeaListener(this);
                /*
                locationManager.addNmeaListener(new OnNmeaMessageListener() {
                    @Override
                    public void onNmeaMessage(String message, long timestamp) {
                        //
                        Log.d("NMEA_APP", getClass().getName()+":"+"["+timestamp+"]"+message+"");


                    }
                });
                */
            }


        }else {

            //GPS NOT ON

            Log.d("NMEA_APP", getClass().getName()+":"+"GPS NOT ON");

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted

                    this.initGPSgettingLogic();

                } else {
                    // denied,

                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onNmeaReceived(long timestamp, String nmea) {

        Log.d("NMEA_APP", getClass().getName()+":"+"["+timestamp+"]"+nmea+"");

        EditText term = (EditText) findViewById(R.id.editText_term);

        term.append(nmea+"\n");

    }
}

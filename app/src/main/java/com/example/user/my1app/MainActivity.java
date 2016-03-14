package com.example.user.my1app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public TextView txLat, txLong, txAlt, txState;
    public Button btLoc, btMap;
    public LocationManager locationMan;
    public MyLocationListener myLocationLx;
    public double latitude, longitude, altitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txLat = (TextView) findViewById(R.id.txLat);
        txLong = (TextView) findViewById(R.id.txLong);
        txAlt = (TextView) findViewById(R.id.txAlt);
        txState = (TextView) findViewById(R.id.txState);

        btLoc = (Button) findViewById(R.id.btLoc);
        btMap = (Button) findViewById(R.id.btMap);


        locationMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocationLx = new MyLocationListener();
        long minTime = 1000;
        long minDistance = 0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationLx);

        btLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = myLocationLx.latitude;
                longitude = myLocationLx.longitude;
                altitude = myLocationLx.altitude;

                txLat.setText(String.format("%g", latitude));
                txLong.setText(String.format("%g", longitude));
                txAlt.setText(String.format("%g", altitude));
            }
        });

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:%g,%g?z=17", latitude, longitude)));

                startActivity(intent);
            }
        });

    }
}

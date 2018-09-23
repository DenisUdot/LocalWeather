package com.denisudotgmail.localweather;

import android.Manifest;
import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends TabActivity {



//    private GeoService geoService;
//    private boolean bound = false;
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            GeoService.GeoBinder geoBinder = (GeoService.GeoBinder)service;
//            geoService = geoBinder.getGeoService();
//            bound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            bound = false;
//        }
//    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // получаем TabHost
        TabHost tabHost = getTabHost();

        // инициализация была выполнена в getTabHost
        // метод setup вызывать не нужно

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Вкладка 1");
        tabSpec.setContent(new Intent(this, GeoLocation.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Вкладка 2");
        tabSpec.setContent(new Intent(this, GeoHistory.class));
        tabHost.addTab(tabSpec);
//        showLocation();


    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        Intent intent = new Intent(this,GeoService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onStop(){
//        super.onStop();
//        if(bound){
//            unbindService(connection);
//            bound = false;
//        }
//    }

// z
}
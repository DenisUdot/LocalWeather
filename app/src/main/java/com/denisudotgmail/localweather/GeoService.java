package com.denisudotgmail.localweather;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GeoService extends Service {

    private final IBinder binder = new GeoBinder();
    private Location currectlocation = null;

    public class GeoBinder extends Binder{
        GeoService getGeoService(){
            return GeoService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate(){
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("dj","on location changed: "+location.getLatitude()+" & "+location.getLongitude());
                currectlocation = location;
                Toast.makeText(getApplicationContext(),Double.toString(location.getLatitude()), Toast.LENGTH_SHORT).show();
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
        };
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
        }catch (SecurityException e){
            Toast.makeText(getApplicationContext(),"GPS don't work", Toast.LENGTH_SHORT).show();
        }

    }

    public double getLatitude(){
        if(currectlocation != null){
            return currectlocation.getLatitude();
        }
        else {
            return 0.0;
        }
    }

}

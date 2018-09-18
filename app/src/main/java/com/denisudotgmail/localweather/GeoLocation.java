package com.denisudotgmail.localweather;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class GeoLocation extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener,OnRequestPermissionsResultCallback {


    @BindView(R.id.btnLocation)Button btnProceed;
    @BindView(R.id.tvAddress)TextView tvAddress;
    @BindView(R.id.tvEmpty)TextView tvEmpty;
    @BindView(R.id.rlPickLocation)RelativeLayout rlPick;

    private Location mLastLocation;

    double latitude;
    double longitude;

    GeoHelper geoHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_location);

        geoHelper =new GeoHelper(this);

        ButterKnife.bind(this);

        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLastLocation= geoHelper.getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {

                    if(btnProceed.isEnabled())
                        btnProceed.setEnabled(false);

                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                }
            }
        });



        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Proceed to the next step");
            }
        });

        // check availability of play services
        if (geoHelper.checkPlayServices()) {

            // Building the GoogleApi client
            geoHelper.buildGoogleApiClient();
        }

    }


    public void getAddress()
    {
        Address locationAddress;

        locationAddress= geoHelper.getAddress(latitude,longitude);

        if(locationAddress!=null)
        {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String currentLocation;

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+="\n"+address1;

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+="\n"+city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=" - "+postalCode;
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+="\n"+postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                    currentLocation+="\n"+country;

                tvEmpty.setVisibility(View.GONE);
                tvAddress.setText(currentLocation);
                tvAddress.setVisibility(View.VISIBLE);

                if(!btnProceed.isEnabled())
                    btnProceed.setEnabled(true);
            }

        }
        else
            showToast("Something went wrong");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        geoHelper.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        geoHelper.checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation= geoHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        geoHelper.connectApiClient();
    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }



}


// import android.app.Activity;
// import android.content.ComponentName;
// import android.content.Context;
// import android.content.Intent;
// import android.content.ServiceConnection;
// import android.os.Bundle;
// import android.os.Handler;
// import android.os.IBinder;
// import android.widget.TextView;
//
//public class GeoLocation extends Activity {
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
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.geo_location);
//        showLatitude();
//    }
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//        Intent intent = new Intent(this, GeoService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//
//
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
//
//    private void showLatitude(){
//        final TextView textView = (TextView)findViewById(R.id.latitude);
//        final Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if(geoService != null){
//                    textView.setText(Double.toString(geoService.getLatitude()));
//                    handler.postDelayed(this,1000);
//                }
//            }
//        });
//    }
//
//
//}

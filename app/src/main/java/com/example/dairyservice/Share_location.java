package com.example.dairyservice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Share_location extends AppCompatActivity  {


    FusedLocationProviderClient mFusedLocationClient;

    private SharedPreferences prefs;
    private DatabaseReference mDatabaseRef;
    TextView latitudeTextView, longitudeTextView,address;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);

        latitudeTextView = findViewById(R.id.latTextView);
        longitudeTextView = findViewById(R.id.lonTextView);
        address = findViewById(R.id.address);


        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);

        if(getIntent().getStringExtra("orderOf").equals("animal")){

            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Orders").child("Animal");
        }
        else if(getIntent().getStringExtra("orderOf").equals("milk")){

            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Orders").child("Milk");
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            // method to get the location
            getLastLocation();
        }catch (Exception e){

        }

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        try{
            // check if permissions are given
            if (checkPermissions()) {

                // check if location is enabled
                if (isLocationEnabled()) {

                    mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {

                                try {
                                    final Double latitude = location.getLatitude();
                                    final Double longitude = location.getLongitude();
                                    latitudeTextView.setText("Latitude: " + latitude + "");
                                    longitudeTextView.setText("Longitude: " + longitude + "");


                                    Geocoder geocoder = new Geocoder(Share_location.this, Locale.getDefault());
                                    List<Address> addresses = null;

                                    try{
                                        addresses = geocoder.getFromLocation(latitude,  longitude, 1);
                                    }
                                    catch (IOException e){

                                    }


                                    final String cityName = addresses.get(0).getAddressLine(0);
                                    address.setText(cityName);

                                    final String customer_email = prefs.getString("Customer_email", "No Mail defined");

                                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                if (customer_email.equals(ds.child("Customer").getValue().toString()) && ds.child("Delivery").getValue().toString().equals("Online")) {
                                                    DatabaseReference latitudeRef = ds.getRef().child("latitude");
                                                    DatabaseReference longitudeRef = ds.getRef().child("longitude");
                                                    DatabaseReference addressRef = ds.getRef().child("address");

                                                    latitudeRef.setValue(latitude.toString());
                                                    longitudeRef.setValue(longitude.toString());
                                                    addressRef.setValue(cityName);

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(Share_location.this, "Your current location is shared", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Please turn on" + " your location first", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            } else {
                // request for permissions
                requestPermissions();
            }
        }
        catch (Exception e){

        }

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            try{
                if (mLastLocation == null) {
                    requestNewLocationData();
                }
                else{
                    try {
                        final Double latitude = mLastLocation.getLatitude();
                        final Double longitude = mLastLocation.getLongitude();
                        latitudeTextView.setText("Latitude: " + latitude + "");
                        longitudeTextView.setText("Longitude: " + longitude + "");


                        Geocoder geocoder = new Geocoder(Share_location.this, Locale.getDefault());
                        List<Address> addresses = null;

                        addresses = geocoder.getFromLocation(latitude,  longitude, 1);
                        final String cityName = addresses.get(0).getAddressLine(0);
                        address.setText(cityName);

                        final String customer_email = prefs.getString("Customer_email", "No Mail defined");

                        mDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    if (customer_email.equals(ds.child("Customer").getValue().toString())  && ds.child("Delivery").getValue().toString().equals("Online")) {
                                        DatabaseReference latitudeRef = ds.getRef().child("latitude");
                                        DatabaseReference longitudeRef = ds.getRef().child("longitude");
                                        DatabaseReference addressRef = ds.getRef().child("address");

                                        latitudeRef.setValue(latitude.toString());
                                        longitudeRef.setValue(longitude.toString());
                                        addressRef.setValue(cityName);


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Toast.makeText(Share_location.this, "Your Location is shared", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e){

            }

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try{
            if (requestCode == PERMISSION_ID) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
            }
        }
        catch (Exception e){

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            if (checkPermissions()) {
                if(isLocationEnabled()){
                    getLastLocation();
                }
            }
        }
        catch (Exception e){

        }

    }
}

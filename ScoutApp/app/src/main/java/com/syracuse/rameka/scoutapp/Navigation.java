/**
 * @FileName Navigation.java
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.syracuse.rameka.scoutapp.CitytoLatitudeLongitudeModel.CityToLL;
import com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.FSVenueCafeModel;
import com.syracuse.rameka.scoutapp.FourSquareVenueImageModel.FSVenuePhotoModel;
import com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.FSVenueRecommendedModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.google.android.gms.maps.SupportMapFragment;
import com.syracuse.rameka.scoutapp.CitytoLatitudeLongitudeModel.CityToLL;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DisplayLocationMap.OnDisplayButtonClicked ,ProfilePage.OnProfileFragmentInteractionListener{

    Button btnGetLoc;
    Button btnChicago;
    Button btnNewyork;
    Button btnSanfransisco;
    Button btnBoston;
    Button btnSeattle;
    Button btnSyracuse;
    Button btnLasVegas;

    Double latitude;
    Double longitude;

    boolean profileFragmentflag = false;
    boolean isInitialLocationSet = true;
    boolean firebBaseLatLngRequestUpdate=false;
    private LocationManager locationManager;
    private LocationListener locationListener;

    UserInfo profileRef;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupWindowAnimations();
        latitude = 0.0;
        longitude = 0.0;

        btnGetLoc = (Button) findViewById(R.id.currentLoc);
        btnChicago = (Button) findViewById(R.id.chicago);
        btnNewyork = (Button) findViewById(R.id.newyork);
        btnSanfransisco = (Button) findViewById(R.id.sanfransisco);
        btnBoston = (Button) findViewById(R.id.boston);
        btnSeattle = (Button) findViewById(R.id.seattle);
        btnSyracuse = (Button) findViewById(R.id.Syracuse);
        btnLasVegas = (Button) findViewById(R.id.LasVegas);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        profileRef.getInstance().updateNavHeader(headerView);


        if(profileRef.getInstance().profileInfo.get("lat").toString().equals("not specified")||profileRef.getInstance().profileInfo.get("lng").toString().equals("not specified")){
            //initial location not set..
            isInitialLocationSet=false;
        }
        else{
           latitude = Double.parseDouble(profileRef.getInstance().profileInfo.get("lat").toString());
            longitude = Double.parseDouble(profileRef.getInstance().profileInfo.get("lng").toString());
        }


        btnChicago.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 41.8781136;
                longitude = -87.6297982 ;
                Toast.makeText(Navigation.this,"Location set as Chicago"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"Chicago, IL");
            }
        });

        btnNewyork.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 40.7127753;
                longitude = -74.0059728 ;
                Toast.makeText(Navigation.this,"Location set as Newyork"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"Newyork, NY");
            }
        });

        btnSanfransisco.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 37.7749295;
                longitude = -122.4194155 ;
                Toast.makeText(Navigation.this,"Location set as San Fransisco"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"San Fransisco, CA");
            }
        });

        btnBoston.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 42.3600825;
                longitude = -71.0588801 ;
                Toast.makeText(Navigation.this,"Location set as Boston"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"Boston, MA");


            }
        });
        btnLasVegas.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 36.1699412;
                longitude = -115.1398296 ;
                Toast.makeText(Navigation.this,"Location set as Las Vegas"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"Las Vegas, NV");

            }
        });
        btnSeattle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 47.6062095;
                longitude = -122.3320708;
                Toast.makeText(Navigation.this,"Location set as Seattle"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"Seattle, WA");

            }
        });
        btnSyracuse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                latitude = 43.0481221;
                longitude = -76.14742439999999;
                Toast.makeText(Navigation.this,"Location set as Syracuse"+" "+longitude,Toast.LENGTH_SHORT).show();
                inflateFragment(latitude,longitude,"Syracuse, NY");

            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(Navigation.this,"Your current location set!"+location.getLatitude() + " " + location.getLongitude(),Toast.LENGTH_LONG).show();
                inflateFragment(latitude,longitude,"Your Location");

                if(firebBaseLatLngRequestUpdate == true){
                    FirebaseDatabase.getInstance().getReference().child(profileRef.getInstance().PROFILE_PATH).child(profileRef.getInstance().profileInfo.get("email").toString().replace(".","="))
                            .child("lat").setValue(latitude);
                    FirebaseDatabase.getInstance().getReference().child(profileRef.getInstance().PROFILE_PATH).child(profileRef.getInstance().profileInfo.get("email").toString().replace(".","="))
                            .child("lng").setValue(longitude);
                    firebBaseLatLngRequestUpdate = false;
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        configureButton();

    }


    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton() {

        btnGetLoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(Navigation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Navigation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 30, locationListener);

            }
        });

    }

    private void getLLfromCity(final String city) throws IOException {

        Call<CityToLL> cityLL = CityToLLAPI.getService().getLLValues(city);

        cityLL.enqueue(new Callback<CityToLL>() {
            @Override
            public void onResponse(Call<CityToLL> call, Response<CityToLL> response) {

                CityToLL list = response.body();
                Log.d("Toast","toast");

                final Double lat = list.getResults().get(0).getGeometry().getLocation().getLat();
                final Double lng = list.getResults().get(0).getGeometry().getLocation().getLng();
                Log.d("Latitude Longitude,", lat.toString() + "," + lng.toString());
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback(){

                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng city = new LatLng(lat, lng);
                  //      googleMap.addMarker(new MarkerOptions().position(city)
                  //              .title(description));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(city));
                        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 7.0f));
                    }
                });

            }

            @Override
            public void onFailure(Call<CityToLL> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(profileFragmentflag){
            finish();
            startActivity(getIntent());
            profileFragmentflag = false;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.previousDefault){
            if(longitude==0.0||latitude==0.0) {
                Toast.makeText(Navigation.this,"No previous default location found!",Toast.LENGTH_LONG).show();
            }
            else{
                FirebaseDatabase.getInstance().getReference().child(profileRef.getInstance().PROFILE_PATH).child(profileRef.getInstance().profileInfo.get("email").toString().replace(".", "="))
                        .child("lat").setValue(latitude.toString());
                FirebaseDatabase.getInstance().getReference().child(profileRef.getInstance().PROFILE_PATH).child(profileRef.getInstance().profileInfo.get("email").toString().replace(".", "="))
                        .child("lng").setValue(longitude.toString());
                Toast.makeText(Navigation.this,"success",Toast.LENGTH_SHORT).show();

            }
        }
        else if(id == R.id.cityDefault){
            startActivity(new Intent(Navigation.this,PopUpActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle Navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.locationAction) {
            finish();
            startActivity(getIntent());
        }
        else if(id ==R.id.reportAction){
            Intent intent = new Intent(this,ReportBugActivity.class);
            startActivity(intent);
        }

        else if(id ==R.id.profileAction){
            HashMap profileData = profileRef.getInstance().getProfileInfo();
            profileFragmentflag = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.navFrame, ProfilePage.newInstance(profileData)).addToBackStack(null).commit();

        }
     else if (id == R.id.logout) {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            finish();
            return true;
        }
        else if(id == R.id.exploreAction){
            if(isInitialLocationSet==false){
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Your default location is not set!. Do you want to use current location as default location?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                firebBaseLatLngRequestUpdate = true;
                                if (ActivityCompat.checkSelfPermission(Navigation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Navigation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                                    return;
                                }
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 30, locationListener);


                                Toast.makeText(Navigation.this,"Current location set as default location",Toast.LENGTH_SHORT).show();
                                isInitialLocationSet = true;


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
            else{
                Intent intent = new Intent(this,ViewPagerRecyclerActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longtitude",longitude);
                intent.putExtra("locationName","Default Location");
                startActivity(intent);
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void inflateFragment(Double lat,Double lng,String locName){
        getSupportFragmentManager().beginTransaction().replace(R.id.navFrame, DisplayLocationMap.newInstance(lat,lng,locName)).addToBackStack(null).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onExploreItemClicked(View v, Double lat, Double lng, String lName) {
        Intent intent = new Intent(this,ViewPagerRecyclerActivity.class);
        intent.putExtra("latitude",lat);
        intent.putExtra("longtitude",lng);
        intent.putExtra("locationName",lName);
        startActivity(intent);
    }

    @Override
    public void saveEditedClicked(View v, HashMap data) {
        //Call back to Firebase
            profileRef.getInstance().updateUserProfile(data);

    }
}

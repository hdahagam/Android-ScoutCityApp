/**
 * @FileName PopUpActivity.java
 * @Functionality
 *            This file is responsible for loading the pop up activity containing the Map view in the Navigation View
              when the toll bar containing the option to set city is clicked.
              NOTE: This fuction might work or might not work based on the API call limit per day. If the limit exceed the APP will crash
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.syracuse.rameka.scoutapp.CitytoLatitudeLongitudeModel.CityToLL;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopUpActivity extends AppCompatActivity {

    Button getLoc;
    EditText geteditText;

    UserInfo profileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        //Setting the dimensions of the pop up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)(height*.8));

        getLoc = (Button) findViewById(R.id.showInMap);
        geteditText = (EditText) findViewById(R.id.cityEdit);

        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(geteditText.getText().toString()!=""){
                    try {
                        getLLfromCity(geteditText.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private void getLLfromCity(final String city) throws IOException {

        Call<CityToLL> cityLL = CityToLLAPI.getService().getLLValues(city);

        cityLL.enqueue(new Callback<CityToLL>() {
            @Override
            public void onResponse(Call<CityToLL> call, Response<CityToLL> response) {

                CityToLL list = response.body();
                try {
                    final Double lat = list.getResults().get(0).getGeometry().getLocation().getLat();
                    final Double lng = list.getResults().get(0).getGeometry().getLocation().getLng();
                    FirebaseDatabase.getInstance().getReference().child(profileRef.getInstance().PROFILE_PATH).child(profileRef.getInstance().profileInfo.get("email").toString().replace(".","="))
                            .child("lat").setValue(lat);
                    FirebaseDatabase.getInstance().getReference().child(profileRef.getInstance().PROFILE_PATH).child(profileRef.getInstance().profileInfo.get("email").toString().replace(".","="))
                            .child("lng").setValue(lng);

                    Log.d("Latitude Longitude,", lat.toString() + "," + lng.toString());
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.mapPopup);
                    mapFragment.getMapAsync(new OnMapReadyCallback(){

                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng city = new LatLng(lat, lng);
                            googleMap.addMarker(new MarkerOptions().position(city)
                                    .title("Selected Location"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(city));
                            googleMap.animateCamera( CameraUpdateFactory.zoomTo( 7.0f));
                        }
                    });
                }
                catch (Exception ex){
                    Toast.makeText(PopUpActivity.this,"Difficulty to set location",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CityToLL> call, Throwable t) {

            }
        });

    }
}

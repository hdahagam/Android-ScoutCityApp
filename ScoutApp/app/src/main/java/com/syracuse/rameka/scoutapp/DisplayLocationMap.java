/**
 * @FileName DisplayLocationMap.java
 * @Functionality
 *
 *            Here we fetch the location data and the fragment is loaded
 *
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DisplayLocationMap extends Fragment {
    static Double latitude;
    static Double longitude;
    static String locName;

    Button exploreActivity;
    Button goBacktoCity;

    public interface OnDisplayButtonClicked{
        public void onExploreItemClicked(View v, Double lat, Double lng, String lName);
    }

    OnDisplayButtonClicked onDisplayButtonClicked;

    public static DisplayLocationMap newInstance(Double lat, Double lng, String locName) {
        DisplayLocationMap fragment = new DisplayLocationMap();
        latitude = lat;
        longitude = lng;
        return fragment;
    }

    public DisplayLocationMap(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View mainView = null;
        mainView = inflater.inflate(R.layout.fragment_display_location_map,container,false);
        onDisplayButtonClicked = (OnDisplayButtonClicked) mainView.getContext();

        exploreActivity = mainView.findViewById(R.id.goToExplore);
        goBacktoCity = mainView.findViewById(R.id.selCity);



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng loc = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(loc)
                        .title(locName));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                googleMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f));
            }
        });

        exploreActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDisplayButtonClicked.onExploreItemClicked(view,latitude,longitude,locName);
            }
        });

        goBacktoCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return mainView;
    }
}


package com.syracuse.rameka.scoutapp;
/**
 * @FileName CafeDetail.java
 * @Functionality
 *           This files is responsible for displaying the details of the fragment when any item from
cafe recycler view is clicked
 *
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */


import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class CafeDetail extends Fragment {

    // The pojo class for cafe model containing Item class consists the detail to fill the fragment
    public static com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.Item data;

    // Fields to populate in the fragment when loaded
    TextView locTitle;
    TextView locPlace;
    TextView locStatus;
    TextView locTips;
    TextView locContact;
    TextView locWebsite;
    TextView locRating;


    public static CafeDetail newInstance(com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.Item itemData) {
        CafeDetail fragment = new CafeDetail();
        //Initializing Data
        data = itemData;
        return fragment;
    }

    public CafeDetail(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        Log.d("Check TExt","Success");
        View mainView = null;
        //Inflating the fragment
        mainView = inflater.inflate(R.layout.fragment_cafe_detail,container,false);


        locTitle = (TextView) mainView.findViewById(R.id.locName2);
        locStatus = (TextView) mainView.findViewById(R.id.locStatus2);
        locContact = (TextView) mainView.findViewById(R.id.contact2);
        locPlace = (TextView) mainView.findViewById(R.id.locPlace2);
        locTips = (TextView) mainView.findViewById(R.id.tipsLoc2);
        locRating = (TextView) mainView.findViewById(R.id.locRating2);
        locWebsite = (TextView) mainView.findViewById(R.id.website2);

        final Double lat = data.getVenue().getLocation().getLat();
        final Double lng = data.getVenue().getLocation().getLng();

        final String description = data.getVenue().getLocation().getAddress();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng loc = new LatLng(lat, lng);
                googleMap.addMarker(new MarkerOptions().position(loc)
                        .title(description));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                googleMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f));
            }
        });

        locTitle.setText(data.getVenue().getName());
        locContact.setText(data.getVenue().getContact().getPhone());
        locStatus.setText(data.getVenue().getHours().getStatus());
        locPlace.setText(data.getVenue().getLocation().getFormattedAddress().get(0));
        locWebsite.setText(data.getVenue().getUrl());
        locRating.setText("Rating: "+data.getVenue().getRating().toString());
        locTips.setText(data.getTips().get(0).getText());

        return mainView;
    }
}

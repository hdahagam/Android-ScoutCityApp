/**
 * @FileName RecommendedDetail.java
 * @Functionality
 *            This file populates the fragment which is loaded, when the recommended recycler's card view is clicked.
 *             The data to populate is passed from the View Pager activity
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;


public class RecommendedDetail extends Fragment {

    public static com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.Item data;
    ImageView ivPoster;
    TextView locTitle;
    TextView locPlace;
    TextView locTips;
    TextView locWebsite;
    TextView locRating;


    public static RecommendedDetail newInstance(com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.Item itemData) {
        RecommendedDetail fragment = new RecommendedDetail();
        data = itemData;
        return fragment;
    }

    public RecommendedDetail(){

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
        mainView = inflater.inflate(R.layout.fragment_recommended_detail,container,false);


        locTitle = (TextView) mainView.findViewById(R.id.locName);
        locPlace = (TextView) mainView.findViewById(R.id.locPlace);
        locTips = (TextView) mainView.findViewById(R.id.tipsLoc);
        locRating = (TextView) mainView.findViewById(R.id.locRating);
        locWebsite = (TextView) mainView.findViewById(R.id.website);

        final Double lat = data.getVenue().getLocation().getLat();
        final Double lng = data.getVenue().getLocation().getLng();

        final String description = data.getVenue().getLocation().getAddress();

        //Setting the Map view present in the fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng loc = new LatLng(lat, lng);
                googleMap.addMarker(new MarkerOptions().position(loc)
                        .title(description));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                googleMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f));
            }
        });

        locTitle.setText(data.getVenue().getName());
        locPlace.setText(data.getVenue().getLocation().getFormattedAddress().get(0));
        locWebsite.setText(data.getVenue().getUrl());
        locRating.setText("Rating: "+data.getVenue().getRating().toString());
        locTips.setText(data.getTips().get(0).getText());


        return mainView;
    }
}

/**
 * @FileName FSVenueImageAPI.java
 * @Functionality
 *           This file acts as an API call to the FourSquare server.This API could makes request to get respective cafe/restaurent/
 * recommended places image retrieving info
 * NOTE: 1)Four Square API is used throughout the application to search for different details about places.
 *       2)Retrofit is used to make calls to the server from Android Studio
 *
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import com.syracuse.rameka.scoutapp.FourSquareVenueImageModel.FSVenuePhotoModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class FSVenueImageAPI {
    //Following are the keys received from the foursquare website when registered.
    public static final String CLIENT_ID = "OEP4H5DPW3R43JVQ0NVBU2BGKJEHKLHDB3I4X0TBIH1GYERY";
    public static final String CLIENT_SECRECT = "42O1PNTOG1LXDNMTQ3COTWH1A3GBJOKD4LZ4WZSD23PROG5R";
    public static final String url = "https://api.foursquare.com/v2/";

    public static VenueImageService venueImageService = null;

    //Returns the retrofit instance to make call to the server
    public static VenueImageService getService(){
        if(venueImageService ==null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                    .build();
            venueImageService = retrofit.create(VenueImageService.class);
        }
        return venueImageService;
    }

    public interface VenueImageService{
        //Calls the server to fetch the cafe/restaurant/Recommended places image info in JSON format based on the passed venue id
        @GET("https://api.foursquare.com/v2/venues/{venueID}/photos?client_id=OEP4H5DPW3R43JVQ0NVBU2BGKJEHKLHDB3I4X0TBIH1GYERY&client_secret=42O1PNTOG1LXDNMTQ3COTWH1A3GBJOKD4LZ4WZSD23PROG5R&v=20171125")
        Call<FSVenuePhotoModel> getImages(@Path("venueID") String ID);

    }


}

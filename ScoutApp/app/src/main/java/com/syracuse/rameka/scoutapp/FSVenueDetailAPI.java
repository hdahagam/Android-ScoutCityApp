/**
 * @FileName FSVenueDetailAPI.java
 * @Functionality
 *           This file acts as an API call to the FourSquare server.This API could makes request to get respective cafe/restaurent/
 * recommended places details such as rating, tips, comments, reviews,...etc.
 * NOTE: 1)Four Square API is used throughout the application to search for different details about places.
 *       2)Retrofit is used to make calls to the server from Android Studio
 *
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import com.syracuse.rameka.scoutapp.FourSquareVenuDetailModel.FSVenueDetails;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class FSVenueDetailAPI {

    //Following are the keys received from the foursquare website when registered.
    public static final String CLIENT_ID = "OEP4H5DPW3R43JVQ0NVBU2BGKJEHKLHDB3I4X0TBIH1GYERY";
    public static final String CLIENT_SECRECT = "42O1PNTOG1LXDNMTQ3COTWH1A3GBJOKD4LZ4WZSD23PROG5R";

    public static final String url = "https://api.foursquare.com/v2/";

    public static VenueDetailService venueDetailService = null;

    //Returns the retrofit instance for venue details, which helps in populating the fragment
    public static VenueDetailService getService(){
        if(venueDetailService==null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                    .build();
            venueDetailService= retrofit.create(VenueDetailService.class);
        }
        return venueDetailService;
    }

    public interface VenueDetailService{
        //Calls the server to fetch the cafe/restaurant/Recommended places details in JSON format based on the passed venue id
        @GET("https://api.foursquare.com/v2/venues/{venueID}?client_id=JO0RTJORSERKBRXCG4OC1JUWXMDGHEUPDWRU33ILZLRJQW1Y&client_secret=B1W5DDTRZB5LPKU0DEFHYLZFSCNB4R44224X2W0PIX1T22O1&v=20171125")
        Call<FSVenueDetails> getDetails(@Path("venueID") String ID);

    }

}

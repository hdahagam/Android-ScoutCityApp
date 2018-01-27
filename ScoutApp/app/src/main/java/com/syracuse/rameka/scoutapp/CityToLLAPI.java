/**
 * @FileName CityToLLAPI.java
 * @Functionality
 *           This is a API which calls the server to fetch latitude and longitude when an city name is passed. Retrofit is used throughout
 * the application for all API calls made to different server.
 *
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import com.syracuse.rameka.scoutapp.CitytoLatitudeLongitudeModel.CityToLL;
import com.syracuse.rameka.scoutapp.FourSquareVenueImageModel.FSVenuePhotoModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class CityToLLAPI {

    public static final String url = "https://maps.google.com/maps/api/geocode/";

    public static CityToLLService cityToLLService = null;
    public static CityToLLService getService(){
        if(cityToLLService ==null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                    .build();
            cityToLLService = retrofit.create(CityToLLService.class);
        }
        return cityToLLService;
    }


    public interface CityToLLService{

        @GET("json?sensor=false")
        Call<CityToLL> getLLValues(@Query("address") String clientID);
    }

}

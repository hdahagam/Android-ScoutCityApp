/**
 * @FileName RestaurantRecyclerAdapter.java
 * @Functionality
 * This file is the adapter for for cafe recycler view of the view pager. It is responsible for populating
 * each card view in the cafe tab of the view pager
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.Item;
import com.syracuse.rameka.scoutapp.FourSquareVenueImageModel.FSVenuePhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.RestaurantListViewHolder>{

    private List<Item> myData;
    private List<com.syracuse.rameka.scoutapp.FourSquareVenueImageModel.Item> myImageData;
    OnItemClickListener myItemClickListener;
    private Context myContext;

    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cardview_3, parent, false);
                break;
        }


        RestaurantListViewHolder viewHolder = new RestaurantListViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListViewHolder holder, int position) {

        holder.place_title.setText(myData.get(position).getVenue().getName().toString());
        holder.place_location.setText(myData.get(position).getVenue().getLocation().getFormattedAddress().get(0).toString());
        holder.place_rating.setText(myData.get(position).getVenue().getStats().getCheckinsCount().toString());
        holder.place_status.setText(myData.get(position).getVenue().getHours().getStatus());
        getImage(myData.get(position).getVenue().getId(),holder);

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
    //This function is responsible for fetching URL of the image from the server and displaying the image in the cardview
    //Picasso is used for this purpose
    private void getImage(String venueID, final RestaurantListViewHolder holder){
        Call<FSVenuePhotoModel> venueSearch = FSVenueImageAPI.getService().getImages(venueID);

        venueSearch.enqueue(new Callback<FSVenuePhotoModel>(){

            @Override
            public void onResponse(Call<FSVenuePhotoModel> call, Response<FSVenuePhotoModel> response) {

                FSVenuePhotoModel list = response.body();
                String url = list.getResponse().getPhotos().getItems().get(0).getPrefix().toString()+"600x800"+list.getResponse().getPhotos().getItems().get(0).getSuffix().toString();
                Picasso.with(myContext).load(url).into(holder.place_image);

            }

            @Override
            public void onFailure(Call<FSVenuePhotoModel> call, Throwable t) {


            }
        });

    }

    public void setOnClickListner(OnItemClickListener onItemClickListener) {
        this.myItemClickListener = onItemClickListener;

    }


    public interface OnItemClickListener{
        public void onItemClick(View v, int position);
        public void onOverflowMenuClick(View v,int position);
    }

    public void SetOnItemClickListener(final RestaurantRecyclerAdapter.OnItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }



    public RestaurantRecyclerAdapter(Context context, List<com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.Item> data) {
        myContext = context;
        myData = data;
    }


    public class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        public ImageView place_image;
        public TextView place_title;
        public TextView place_location;
        public TextView place_rating;
        public TextView place_status;
        public ImageView options;

        RestaurantListViewHolder(View view) {
            super(view);
            place_image = (ImageView) view.findViewById(R.id.rp_imageView3);
            place_title = (TextView) view.findViewById(R.id.recommendedTitle3);
            place_location = (TextView) view.findViewById(R.id.location3);
            place_rating = (TextView) view.findViewById(R.id.cardrating3);
            options = (ImageView) view.findViewById(R.id.rp_moreOptions3);
            place_status = (TextView) view.findViewById(R.id.statusloc3);
            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(myItemClickListener != null)
                    {
                        myItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });


            if(options != null)
            {
                options.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myItemClickListener != null) {
                            myItemClickListener.onOverflowMenuClick(v,getAdapterPosition());
                        }
                    }
                });
            }
        }

    }



}

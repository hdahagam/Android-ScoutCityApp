/**
 * @FileName RecommendedRecyclerFragment.java
 * @Functionality
 *This is the fragment loaded in to the view pager when Recommended recycler view is selected.
 *Here we fetch the data from the server and set the adapter of this recycler view
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syracuse.rameka.scoutapp.FourSquareVenueImageModel.FSVenuePhotoModel;
import com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.FSVenueRecommendedModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecommendedRecyclerFragment extends Fragment {


    RecyclerView myRecyclerView;
    LinearLayoutManager myLayoutManager;
    RecommendedRecyclerAdapter myAdapter;
    ShareActionProvider mySharedActionProvider;
    static Double lat;
    static Double lng;
    static String lname;
    static int taskId;
    TextView textViewToolbar;
    List<com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.Item> listData;

    //Interface is declared so that when an card view in this fragment is clicked its operations can be handled by
    // the parent view. i.e. to load the detail view fragment

    public interface RecycleViewListener {
        public void onRecycleViewItemClicked(View v, com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.Item item);
    }
    private RecycleViewListener customOnClickRvListener;

    private static final String ARG_SECTION_NUMBER = "sectionNumber";

    //Constructor initializes the location name, latitude and longitude when from the parent view
    public static  RecommendedRecyclerFragment newInstance(Double latitude,Double longitude, String locname){
        RecommendedRecyclerFragment fragment = new RecommendedRecyclerFragment();
        Bundle args = new Bundle();
        lat = latitude;
        lng = longitude;
        lname = locname;

        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public RecommendedRecyclerFragment(){
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //Action bar is contained in the parent view which has feature to search the places by name in the area
        // The search facility for the respective recycler view is coded here
        if (menu.findItem(R.id.icon_searchButton) == null) {
            inflater.inflate(R.menu.menu_recycler, menu);
        }
        SearchView search = (SearchView) menu.findItem(R.id.icon_searchButton).getActionView();
        if (search != null) {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    int pos = findFirst(query);
                    if (pos >= 0)
                        myRecyclerView.scrollToPosition(pos);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private int findFirst(String query) {
        int pos=-1;

        for(int i=0;i<listData.size();i++){
            if(listData.get(i).getVenue().getName().toLowerCase().contains(query.toLowerCase())){
                pos = i;
                break;
            }
        }

        return pos;
    }

    //This function is actually responsible for fetching the data from the server. i.e. it fetches Recommended locations in an are and
    // sets the adapter
    private void getData(){

        String latlng = lat.toString()+","+lng.toString();
        Call<FSVenueRecommendedModel> venueSearch = FSVenueRecommendedAPI.getService().searchRecommended(latlng);
        venueSearch.enqueue(new Callback<FSVenueRecommendedModel>() {
                                @Override
                                public void onResponse(Call<FSVenueRecommendedModel> call, Response<FSVenueRecommendedModel> response) {
                                    final FSVenueRecommendedModel list = response.body();
                                    Log.d("Success",list.toString());
                                    listData = list.getResponse().getGroups().get(0).getItems();
                                    myAdapter = new RecommendedRecyclerAdapter(getActivity(),list.getResponse().getGroups().get(0).getItems());
                                    myRecyclerView.setAdapter(myAdapter);
                                    myAdapter.setOnClickListner(new RecommendedRecyclerAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int position) {

                                            Toast.makeText(getActivity(),"Chicked:"+position,Toast.LENGTH_SHORT).show();
                                            customOnClickRvListener.onRecycleViewItemClicked(v, listData.get(position));

                                        }
                                        // This handles the overflow click event. NOTE: This functionality to perform when an over flow item is
                                        // clicked is not implemented. But the implementation is to add to a calander for future notification
                                        @Override
                                        public void onOverflowMenuClick(View v, final int position) {

                                            final PopupMenu popup = new PopupMenu(getActivity(), v);
                                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.item_interested:
                                                            HashMap map = new HashMap();
                                                            map.put("locname",list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getName());
                                                            map.put("id",list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getId());
                                                            map.put("lat",list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getLocation().getLat());
                                                            map.put("lng",list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getLocation().getLng());
                                                            map.put("tips",list.getResponse().getGroups().get(0).getItems().get(position).getTips().get(0).getText());
                                                            map.put("rating",list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getRating());
                                                            String address =list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getLocation().getFormattedAddress().get(0)+", "+
                                                                    list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getLocation().getFormattedAddress().get(1)+", "+
                                                                    list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getLocation().getFormattedAddress().get(2);
                                                            map.put("address",address);

                                                            //put this map in firebasewith ID

                                                            return true;
                                                        //This functionality is to share the event/cafe/restaurant/places through gmail, bluetooth, hangouts,...
                                                        case R.id.item_share:
                                                            Intent sendIntent = new Intent();
                                                            sendIntent.setAction(Intent.ACTION_SEND);
                                                            String textTosend = "Want to go out to this place with me! "+list.getResponse().getGroups().get(0).getItems().get(position).getVenue().getName();
                                                            sendIntent.putExtra(Intent.EXTRA_TEXT, textTosend);
                                                            sendIntent.setType("text/plain");
                                                            startActivity(Intent.createChooser(sendIntent, "Share with:"));
                                                            return true;
                                                        default:
                                                            return false;
                                                    }
                                                }
                                            });
                                            MenuInflater inflater1 = popup.getMenuInflater();
                                            inflater1.inflate(R.menu.card_menu, popup.getMenu());
                                            popup.show();

                                        }
                                    });

                                }

                                @Override
                                public void onFailure(Call<FSVenueRecommendedModel> call, Throwable t) {

                                }
                            }

        );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        final View rootView = inflater.inflate(R.layout.fragment_recommended_recycler,container,false);


        customOnClickRvListener = (RecycleViewListener) rootView.getContext();
        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewrow);

        myLayoutManager = new LinearLayoutManager(getActivity());

        myRecyclerView.setLayoutManager(myLayoutManager);
        getData();
        return rootView;
    }



}

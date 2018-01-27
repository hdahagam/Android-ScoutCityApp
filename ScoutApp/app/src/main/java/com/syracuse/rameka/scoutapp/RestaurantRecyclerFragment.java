/**
 * @FileName RestaurantRecyclerFragment.java
 * @Functionality
 *This is the fragment loaded in to the view pager when Restaurant recycler view is selected.
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.FSVenueCafeModel;
import com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.Item;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantRecyclerFragment extends Fragment {
    RecyclerView myRecyclerView;
    LinearLayoutManager myLayoutManager;
    RestaurantRecyclerAdapter myAdapter;
    static Double lat;
    static Double lng;
    static String lname;
    static int taskId;
    TextView textViewToolbar;
    List<Item> listData;

    public interface CafeRecycleViewListener {
        public void onCafeRecycleViewItemClicked(View v, Item item);
    }
    private CafeRecycleViewListener customOnClickRvListener;



    private static final String ARG_SECTION_NUMBER = "sectionNumber";

    public static  RestaurantRecyclerFragment newInstance(Double latitude,Double longitude, String locname){
        RestaurantRecyclerFragment fragment = new RestaurantRecyclerFragment();
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

    public RestaurantRecyclerFragment(){
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

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


    private void getData(){

        String latlng = lat.toString()+","+lng.toString();

        Call<FSVenueCafeModel> venueSearch = FSVenueCafeAPI.getService().searchFood(latlng);
        venueSearch.enqueue(new Callback<FSVenueCafeModel>() {
                                @Override
                                public void onResponse(Call<FSVenueCafeModel> call, Response<FSVenueCafeModel> response) {

                                    final FSVenueCafeModel list = response.body();
                                    //Log.d("Data",list.getResponse().getVenues().toString());
                                    listData = list.getResponse().getGroups().get(0).getItems();
                                    myAdapter = new RestaurantRecyclerAdapter(getActivity(),listData);
                                    myRecyclerView.setAdapter(myAdapter);
                                    myAdapter.setOnClickListner(new RestaurantRecyclerAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int position) {

                                            customOnClickRvListener.onCafeRecycleViewItemClicked(v, list.getResponse().getGroups().get(0).getItems().get(position));

                                            Toast.makeText(getActivity(),"Clicked:"+position,Toast.LENGTH_SHORT).show();

                                        }

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
                                public void onFailure(Call<FSVenueCafeModel> call, Throwable t) {

                                }
                            }

        );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        final View rootView = inflater.inflate(R.layout.fragment_restaurant_recycler,container,false);

        customOnClickRvListener = (CafeRecycleViewListener) rootView.getContext();

        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewrow3);
        //Implement myRecyclerView

        myLayoutManager = new LinearLayoutManager(getActivity());
        //Implement myLayoutManager

        myRecyclerView.setLayoutManager(myLayoutManager);

        getData();

        return rootView;
    }

}

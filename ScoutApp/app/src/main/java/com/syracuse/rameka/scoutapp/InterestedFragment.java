/**
 * @FileName InterestedFragment.java
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.syracuse.rameka.scoutapp.FourSquareVenuDetailModel.FSVenueDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InterestedFragment extends Fragment {
    RecyclerView myRecyclerView;
    LinearLayoutManager myLayoutManager;
    RecommendedRecyclerAdapter myAdapter;
    static List<String> listData;
    static int totalListSize;
    static List<FSVenueDetails> adapterData;


    public interface InterestedRecycleViewListener {
        public void onRecycleViewItemClicked(View v, com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.Item item);
    }
    private InterestedRecycleViewListener customOnClickRvListener;

    private static final String ARG_SECTION_NUMBER = "sectionNumber";

    public static  RecommendedRecyclerFragment newInstance(List<String> list){
        RecommendedRecyclerFragment fragment = new RecommendedRecyclerFragment();
        Bundle args = new Bundle();
        listData = list;
        totalListSize = list.size();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    public InterestedFragment(){
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        final View rootView = inflater.inflate(R.layout.fragment_recommended_recycler,container,false);



        customOnClickRvListener = (InterestedRecycleViewListener) rootView.getContext();

        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewrow);
        //Implement myRecyclerView

        myLayoutManager = new LinearLayoutManager(getActivity());
        //Implement myLayoutManager

        myRecyclerView.setLayoutManager(myLayoutManager);

        for(int i=0;i<listData.size();i++){
            getData(listData.get(i),i);
        }


        return rootView;
    }

    private void getData(String s, int i) {
        final int valCheck = i;
        Call<FSVenueDetails> venue = FSVenueDetailAPI.getService().getDetails(s);

        venue.enqueue(new Callback<FSVenueDetails>() {
            @Override
            public void onResponse(Call<FSVenueDetails> call, Response<FSVenueDetails> response) {
                FSVenueDetails list = response.body();
                adapterData.add(list);
                if(valCheck==totalListSize) {
                    //set adapter;
                }
            }

            @Override
            public void onFailure(Call<FSVenueDetails> call, Throwable t) {

            }
        });

    }

}



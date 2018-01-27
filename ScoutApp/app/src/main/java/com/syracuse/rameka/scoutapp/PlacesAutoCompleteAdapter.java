/**
 * @FileName PlacesAutoCompleteAdapter.java
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.select.NodeFilter;

import java.util.ArrayList;

class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> resultList;

    Context mContext;
    int mResource;

    PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filterResults = new FilterResults();
//                if (constraint != null) {
//                    resultList = mPlaceAPI.autocomplete(constraint.toString());
//                    Log.d("Results -", resultList.get(0));
//                    //Toast.makeText(getAct, description, Toast.LENGTH_SHORT).show();
//
//                    // Footer
//                    resultList.add("footer");
//
//                    filterResults.values = resultList;
//                    filterResults.count = resultList.size();
//                }
//
//                return filterResults;
//            }
//
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
//          FilterResults filterResults = new FilterResults();
//          if (constraint != null) {
//            resultList = mPlaceAPI.autocomplete(constraint.toString());
//
//          filterResults.values = resultList;
//          filterResults.count = resultList.size();
//          }
//
                return null;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                Log.d("Results -", Integer.toString(results.count));
//
//                if (results != null && results.count > 0) {
//                    notifyDataSetChanged();
//                }
//                else {
//                    notifyDataSetInvalidated();
//                }
            }
        };

        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position != (resultList.size() - 1))
            view = inflater.inflate(R.layout.autocomplete_list_item, null);
        else
            view = inflater.inflate(R.layout.autocomplete_google_logo, null);
        //}
        //else {
        //    view = convertView;
        //}

        if (position != (resultList.size() - 1)) {
            TextView autocompleteTextView = (TextView) view.findViewById(R.id.autocompleteText);
            autocompleteTextView.setText(resultList.get(position));
        }
        else {
            ImageView imageView = (ImageView) view.findViewById(R.id.autoimageView);
            imageView.setImageResource(R.drawable.powerbygoogle);
            // not sure what to do <img draggable="false" class="emoji" alt="😀" src="https://s.w.org/images/core/emoji/72x72/1f600.png">
        }

        return view;
    }



}

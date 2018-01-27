/**
 * @FileName ViewPagerRecyclerActivity.java
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel.Item;


public class ViewPagerRecyclerActivity extends AppCompatActivity implements RecommendedRecyclerFragment.RecycleViewListener,CafeRecyclerFragment.CafeRecycleViewListener,RestaurantRecyclerFragment.CafeRecycleViewListener{
    ViewPager viewPage;
    MyFragmentPageAdapter FragmentAdapter;
    ActionBar mActionBar;

    Double lat;
    Double lng;
    String lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_recycler);

        setupWindowAnimations();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.recyclerToolbar);
        setSupportActionBar(myToolbar);
        mActionBar = getSupportActionBar();
        // mActionBar.setDisplayHomeAsUpEnabled(true);
        TextView tv_toolbarTitle = (TextView)myToolbar.findViewById(R.id.tv_toolbarTitle);
        tv_toolbarTitle.setText("Near You");
        mActionBar.setTitle(null);

        FragmentAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(),ViewPagerRecyclerActivity.this);
        viewPage = (ViewPager) findViewById(R.id.viewpager);
        viewPage.setAdapter(FragmentAdapter);
        viewPage.setPageTransformer(false, new FlipPageViewTransformer());
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabBar);
        tabLayout.setupWithViewPager(viewPage);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        if(getIntent().getExtras()!=null){
             lname = getIntent().getStringExtra("locationName");
             lat = getIntent().getDoubleExtra("latitude",0);
             lng = getIntent().getDoubleExtra("longtitude",0);

            Toast.makeText(ViewPagerRecyclerActivity.this,lat+" : "+lng,Toast.LENGTH_SHORT).show();

        }
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    @Override
    public void onRecycleViewItemClicked(View v, Item item) {

        Toast.makeText(ViewPagerRecyclerActivity.this,"ID:"+item.getVenue().getId().toString(),Toast.LENGTH_LONG).show();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fT = fm.beginTransaction();
        fT.replace(R.id.locationDetail, RecommendedDetail.newInstance(item));
        fT.addToBackStack(null);
        fT.commit();
    }

    @Override
    public void onCafeRecycleViewItemClicked(View v, com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel.Item item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fT = fm.beginTransaction();
        fT.add(R.id.locationDetail, CafeDetail.newInstance(item));
        fT.addToBackStack(null);
        fT.commit();
    }


    public class MyFragmentPageAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private Context context;
        String[] tabElements = new String[]{"RECOMMENDED","CAFES","RESTAURANTS"};

        public MyFragmentPageAdapter(FragmentManager fm, Context context){
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position){
            Fragment returningFragment;
            switch(position)
            {
                case 0:
                   returningFragment= RecommendedRecyclerFragment.newInstance(lat,lng,lname);
                    break;
                case 1:
                    returningFragment =CafeRecyclerFragment.newInstance(lat,lng,lname);
                    break;
                case 2:
                    returningFragment =RestaurantRecyclerFragment.newInstance(lat,lng,lname);
                    break;
                default:
                    returningFragment =RecommendedRecyclerFragment.newInstance(lat,lng,lname);


            }

            return returningFragment;
        }

        @Override
        public int getCount() {
            return tabElements.length;
        }


        @Override
        public CharSequence getPageTitle(int position){
            return tabElements[position];
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent2 = new Intent(this, Navigation.class);
        startActivity(intent2);
    }
}

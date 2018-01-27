/**
 * @FileName UserInfo.java
 * @Functionality
 * This is a singleton class where the user profile is retrieved anywhere in the class. The instance for user profile is
 * set immediately after loggin to the application.
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


public class UserInfo {

    HashMap profileInfo;
    String userId;

    DatabaseReference myRef;
    StorageReference storageReference;

    final static String PROFILE_PATH = "/Accounts";

    private static UserInfo single_instance = null;

    public static UserInfo getInstance()
    {
        if (single_instance == null)
            single_instance = new UserInfo();

        return single_instance;
    }

    HashMap getProfileInfo()
    {
        return  profileInfo;
    }

    // Constructor gets the reference of firebase
    public UserInfo(){
        profileInfo = new HashMap();
        storageReference = FirebaseStorage.getInstance().getReference();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    public void isUserIDExists(final String id, final String emailId, final String fullName, final String loc, final String description, final String phoneNumber, final String latitude, final String longitude, final String imgpath, final Context context){

        FirebaseDatabase.getInstance().getReference().child(PROFILE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(id.replace(".","="))) {
                    userId = id.replace(".","=").toString();
                    updateProfileInfo(userId);
                    Intent intent = new Intent(context, Navigation.class);
                    context.startActivity(intent);

                }
                else{
                    createUserProfile(emailId,fullName, loc, description, phoneNumber,latitude,longitude, imgpath);
                    Intent intent = new Intent(context, Navigation.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void updateProfileInfo(String userId){

        FirebaseDatabase.getInstance().getReference().child(PROFILE_PATH).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               profileInfo = (HashMap<String, String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    // Function creates the user profile for the first time
    void createUserProfile(String emailId, String fullName, String loc, String description, String phoneNumber, String latitude,String longitude, String imgpath)
    {
        profileInfo.put("name", fullName);
        profileInfo.put("description", description);
        profileInfo.put("location", loc);
        profileInfo.put("phone", phoneNumber);
        profileInfo.put("email", emailId);
        profileInfo.put("lat",latitude);
        profileInfo.put("lng",longitude);
        profileInfo.put("imagePath",imgpath);
        userId = emailId.replace(".","=");
        Log.d("User profile id ", userId);
        myRef.child(PROFILE_PATH).child(userId).setValue(profileInfo);
    }



    void updateUserProfile(final HashMap map)
    {
        String key = map.get("email").toString().replace('.','=');
        myRef.child(PROFILE_PATH).child(key).setValue(map);

    }

    void updateNavHeader(final View headerView)
    {
        final TextView tv_name = (TextView) headerView.findViewById(R.id.navigationName);
        final TextView tv_email = (TextView) headerView.findViewById(R.id.navigationEmail);
        final ImageView iv_displayPic = (ImageView) headerView.findViewById(R.id.profile_image);
        FirebaseDatabase.getInstance().getReference().child(PROFILE_PATH).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileInfo = (HashMap<String, String>) dataSnapshot.getValue();
                tv_email.setText(profileInfo.get("email").toString());
                tv_name.setText(profileInfo.get("name").toString());
                String picturePath = profileInfo.get("imagePath").toString();

                Log.d("picturePath", picturePath);


                if(picturePath==""||picturePath=="not specified"){
                    //do nothing
                }
                else {
                    Picasso.with(headerView.getContext()).load(picturePath).into(iv_displayPic);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FBTest: ", "Cannot read data to navigation drawer header " + databaseError.getMessage());
            }
        });
    }


}

/**
 * @FileName ProfilePage.java
 * @Functionality
 *               This file loads the profile page fragment when the respective navigation bar item is clicked

 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class ProfilePage extends Fragment {

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Creating URI.
    Uri FilePathUri;

    StorageReference storageReference;

    UserInfo profile;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    private OnProfileFragmentInteractionListener mListener;

    static HashMap profileData;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    EditText nameProfile;
    EditText descriptionProfile;
    EditText emailProfile;
    EditText phoneProfile;
    EditText locProfile;

    static String imageURL;
    String keyID;

    ImageButton firstEdit,secondEdit,thirdEdit,fourthEdit;
    ImageView profileImage;
    Button saveEdited;

    static boolean flag1=true;
    static boolean flag2=true;
    static boolean flag3=true;
    static boolean flag4=true;


    public interface OnProfileFragmentInteractionListener {
        // TODO: Update argument type and name
        void saveEditedClicked(View v, HashMap data);

    }

    public ProfilePage() {

    }


    public static ProfilePage newInstance(HashMap mapGiven) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();

        profileData = mapGiven;
        imageURL = profileData.get("imagePath").toString();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = null;
        mainView = inflater.inflate(R.layout.fragment_profile_page,container,false);

        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        mListener = (OnProfileFragmentInteractionListener) mainView.getContext();
        //Text views
        nameProfile = (EditText) mainView.findViewById(R.id.nameProfile);
        descriptionProfile = (EditText) mainView.findViewById(R.id.descriptionProfile);
        locProfile = (EditText) mainView.findViewById(R.id.locationProfile);
        emailProfile = (EditText) mainView.findViewById(R.id.emailProfile);
        phoneProfile = (EditText) mainView.findViewById(R.id.phoneProfile);
        nameProfile.setText(profileData.get("name").toString());
        descriptionProfile.setText(profileData.get("description").toString());
        locProfile.setText(profileData.get("location").toString());
        emailProfile.setText(profileData.get("email").toString());
        phoneProfile.setText(profileData.get("phone").toString());
        keyID = profileData.get("email").toString().replace(".","=");

        //ImageView
        profileImage = (ImageView) mainView.findViewById(R.id.profileimageProfile);

        if(!profileData.get("imagePath").equals("not specified")){
            Picasso.with(getContext()).load(profileData.get("imagePath").toString()).into(profileImage);
        }

        //Edit button initializations

        firstEdit = (ImageButton) mainView.findViewById(R.id.firstEdit);
        secondEdit = (ImageButton) mainView.findViewById(R.id.secondEdit);
        thirdEdit = (ImageButton) mainView.findViewById(R.id.thirdEdit);
        fourthEdit = (ImageButton) mainView.findViewById(R.id.fourthEdited);

        saveEdited = (Button) mainView.findViewById(R.id.editClicked);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

        firstEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag1==true){
                    nameProfile.setFocusableInTouchMode(true);
                    nameProfile.setText("");
                    flag1=false;
                }
                else{
                    nameProfile.setFocusable(false);
                    flag1 = true;
                }
            }
        });

        thirdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag3==true){
                    phoneProfile.setFocusableInTouchMode(true);
                    phoneProfile.setText("");
                    flag3=false;
                }
                else{
                    phoneProfile.setFocusable(false);
                    flag3 = true;
                }
            }
        });

        fourthEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag4==true){
                    locProfile.setFocusableInTouchMode(true);
                    locProfile.setText("");
                    flag4=false;
                }
                else{
                    locProfile.setFocusable(false);
                    flag4 = true;
                }
            }

        });


        secondEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Describe yourself!");
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(),
                            "Your device doesnt support voice Recognition!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        saveEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameProfile.getText().equals("")||phoneProfile.getText().equals("")||descriptionProfile.getText().equals("")
                        ||locProfile.getText().equals("")){
                    Toast.makeText(getActivity(),"No Field should be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                UploadImageFileToFirebaseStorage();
                profileData.put("name",nameProfile.getText().toString());
                profileData.put("description",descriptionProfile.getText().toString());
                profileData.put("location",locProfile.getText().toString());
                profileData.put("phone",phoneProfile.getText().toString());
                profileData.put("imagePath",imageURL.toString());


                Log.d("Profile data",profileData.get("imagePath").toString());
                mListener.saveEditedClicked(view,profileData);

            }
        });

        return mainView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                profileImage.setImageBitmap(bitmap);


            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }


        //Google voice to text
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    descriptionProfile.setText(result.get(0));
                }
                break;
            }

        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {


            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String TempImageName = "Test Image Name";


                            // Showing toast message after done uploading.
                            Toast.makeText(getContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, taskSnapshot.getDownloadUrl().toString());
                            imageURL = taskSnapshot.getDownloadUrl().toString();
                            // Getting image upload ID.

                            // Adding image upload id s child element into databaseReference.
                            FirebaseDatabase.getInstance().getReference().child(profile.getInstance().PROFILE_PATH).child(keyID).child("imagePath").setValue(imageURL);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {


                            // Showing exception erro message.
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        }
                    });
        }
        else {

            Toast.makeText(getActivity(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}

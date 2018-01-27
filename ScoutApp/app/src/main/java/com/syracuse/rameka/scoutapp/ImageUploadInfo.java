/**
 * @FileName ImageUploadInfo.java
 * @Functionality
 *
 * This file is the structure used to store the image upload details before storing in the firebase.

 *
 * @author  Hemanth Dahagam
 * @email   hdahagam@gmail.com
 * @version 1.0
 * @Date   2017-November-10
 */
package com.syracuse.rameka.scoutapp;



public class ImageUploadInfo {


        public String imageName;
        public String imageURL;

        public ImageUploadInfo() {

        }

        public ImageUploadInfo(String name, String url) {

            this.imageName = name;
            this.imageURL= url;
        }

        public String getImageName() {
            return imageName;
        }

        public String getImageURL() {
            return imageURL;
        }

    }


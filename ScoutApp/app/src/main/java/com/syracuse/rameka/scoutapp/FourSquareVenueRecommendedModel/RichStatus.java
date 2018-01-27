
package com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RichStatus {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

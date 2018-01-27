
package com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("reasons")
    @Expose
    private Reasons reasons;
    @SerializedName("referralId")
    @Expose
    private String referralId;
    @SerializedName("tips")
    @Expose
    private List<Tip> tips = null;
    @SerializedName("venue")
    @Expose
    private Venue venue;

    public Reasons getReasons() {
        return reasons;
    }

    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public List<Tip> getTips() {
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

}

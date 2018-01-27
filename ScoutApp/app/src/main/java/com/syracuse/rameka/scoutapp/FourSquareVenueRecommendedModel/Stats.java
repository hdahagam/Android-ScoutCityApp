
package com.syracuse.rameka.scoutapp.FourSquareVenueRecommendedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("checkinsCount")
    @Expose
    private Integer checkinsCount;
    @SerializedName("tipCount")
    @Expose
    private Integer tipCount;
    @SerializedName("usersCount")
    @Expose
    private Integer usersCount;

    public Integer getCheckinsCount() {
        return checkinsCount;
    }

    public void setCheckinsCount(Integer checkinsCount) {
        this.checkinsCount = checkinsCount;
    }

    public Integer getTipCount() {
        return tipCount;
    }

    public void setTipCount(Integer tipCount) {
        this.tipCount = tipCount;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

}

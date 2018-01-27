
package com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tier")
    @Expose
    private Integer tier;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

}

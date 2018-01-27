
package com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hours {

    @SerializedName("isLocalHoliday")
    @Expose
    private Boolean isLocalHoliday;
    @SerializedName("isOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("richStatus")
    @Expose
    private RichStatus richStatus;
    @SerializedName("status")
    @Expose
    private String status;

    public Boolean getIsLocalHoliday() {
        return isLocalHoliday;
    }

    public void setIsLocalHoliday(Boolean isLocalHoliday) {
        this.isLocalHoliday = isLocalHoliday;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public RichStatus getRichStatus() {
        return richStatus;
    }

    public void setRichStatus(RichStatus richStatus) {
        this.richStatus = richStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

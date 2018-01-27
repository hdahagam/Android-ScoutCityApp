
package com.syracuse.rameka.scoutapp.FourSquareVenueCafeModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestedFilters {

    @SerializedName("filters")
    @Expose
    private List<Filter> filters = null;
    @SerializedName("header")
    @Expose
    private String header;

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}

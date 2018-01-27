
package com.syracuse.rameka.scoutapp.FourSquareVenuDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tips_ {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("groups")
    @Expose
    private List<Group___> groups = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Group___> getGroups() {
        return groups;
    }

    public void setGroups(List<Group___> groups) {
        this.groups = groups;
    }

}

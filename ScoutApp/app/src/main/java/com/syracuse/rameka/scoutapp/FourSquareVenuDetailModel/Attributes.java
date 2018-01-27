
package com.syracuse.rameka.scoutapp.FourSquareVenuDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attributes {

    @SerializedName("groups")
    @Expose
    private List<Group______> groups = null;

    public List<Group______> getGroups() {
        return groups;
    }

    public void setGroups(List<Group______> groups) {
        this.groups = groups;
    }

}

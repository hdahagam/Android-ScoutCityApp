
package com.syracuse.rameka.scoutapp.FourSquareVenuDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parent {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private Contact__ contact;
    @SerializedName("location")
    @Expose
    private Location_ location;
    @SerializedName("categories")
    @Expose
    private List<Category_> categories = null;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("stats")
    @Expose
    private Stats_ stats;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("beenHere")
    @Expose
    private BeenHere_ beenHere;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact__ getContact() {
        return contact;
    }

    public void setContact(Contact__ contact) {
        this.contact = contact;
    }

    public Location_ getLocation() {
        return location;
    }

    public void setLocation(Location_ location) {
        this.location = location;
    }

    public List<Category_> getCategories() {
        return categories;
    }

    public void setCategories(List<Category_> categories) {
        this.categories = categories;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Stats_ getStats() {
        return stats;
    }

    public void setStats(Stats_ stats) {
        this.stats = stats;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BeenHere_ getBeenHere() {
        return beenHere;
    }

    public void setBeenHere(BeenHere_ beenHere) {
        this.beenHere = beenHere;
    }

}

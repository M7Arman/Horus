package com.arman.horus.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arman on 4/2/16.
 */
public class TripDetail {

    public String[] images;
    public String title;
    public String description;
    @SerializedName(value="start_date")
    public String startDate;
    public Address from;
    public Address target;
    public String id;

    public TripDetail(String[] images, String title, String description, String startDate, Address from, Address to, String id) {
        this.images = images;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.from = from;
        this.target = to;
        this.id = id;
    }
}

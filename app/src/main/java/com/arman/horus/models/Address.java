package com.arman.horus.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arman on 4/28/16.
 */
public class Address {

    @SerializedName(value = "display_name")
    private String displayName;
    private double[] coord;

    public Address(String displayName) {
        this.displayName = displayName;
    }

    public Address() {
    }

    public Address(double[] coords, String displayName) {
        this.coord = coords;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double[] getCoord() {
        return coord;
    }

    public void setCoord(double[] coord) {
        this.coord = coord;
    }
}

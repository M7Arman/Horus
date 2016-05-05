package com.arman.horus.models;

/**
 * Created by arman on 4/28/16.
 */
public class Address {
    private String display_name;
    private double[] coord;

    public Address(String display_name) {
        this.display_name = display_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public double[] getCoord() {
        return coord;
    }

    public void setCoord(double[] coord) {
        this.coord = coord;
    }
}

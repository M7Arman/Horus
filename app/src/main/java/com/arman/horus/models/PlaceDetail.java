package com.arman.horus.models;

/**
 * Created by arman on 4/2/16.
 */
public class PlaceDetail {

    public int images;
    public String title;
    public String description;
    public Address address;
    public String id;

    public PlaceDetail(int images, String title, String description, Address address, String id) {
        this.images = images;
        this.title = title;
        this.description = description;
        this.address = address;
        this.id = id;
    }
}

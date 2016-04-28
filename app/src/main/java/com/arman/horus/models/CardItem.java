package com.arman.horus.models;

/**
 * Created by arman on 3/27/16.
 */
public class CardItem {

    private String title = "No Title";
    private int image;
    private int icon;
    private String id;

    public CardItem(String title, int image, int icon, String id) {
        this.title = title;
        this.image = image;
        this.icon = icon;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

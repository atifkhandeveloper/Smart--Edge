package com.abh80.smartedge.aod_edgelighting.model;

public class CustomImageModel {
    String name;
    int image,image_selected;

    public CustomImageModel(String name, int image, int image_selected) {
        this.name = name;
        this.image = image;
        this.image_selected = image_selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage_selected() {
        return image_selected;
    }

    public void setImage_selected(int image_selected) {
        this.image_selected = image_selected;
    }
}

package com.abh80.smartedge.aod_edgelighting.model;

public class DefaultThemeModel {
    String name;
    int image;
    int color1,color2,color3,color4;

    public DefaultThemeModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public DefaultThemeModel() {
    }

    public DefaultThemeModel(String name, int image, int color1, int color2) {
        this.name = name;
        this.image = image;
        this.color1 = color1;
        this.color2 = color2;
    }

    public DefaultThemeModel(String name, int image, int color1, int color2, int color3) {
        this.name = name;
        this.image = image;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public DefaultThemeModel(String name, int image, int color1, int color2, int color3, int color4) {
        this.name = name;
        this.image = image;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
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

    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public int getColor3() {
        return color3;
    }

    public void setColor3(int color3) {
        this.color3 = color3;
    }

    public int getColor4() {
        return color4;
    }

    public void setColor4(int color4) {
        this.color4 = color4;
    }
}

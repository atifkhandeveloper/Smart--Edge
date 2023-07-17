package com.abh80.smartedge.aod_edgelighting.item;

import androidx.annotation.Keep;

@Keep
public class ItemPaths {
    public String data;
    public String id;
    public String opacity;


    public ItemPaths(String data, String id, String opacity) {
        this.data = data;
        this.id = id;
        this.opacity = opacity;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOpacity() {
        return opacity;
    }

    public void setOpacity(String opacity) {
        this.opacity = opacity;
    }

    public ItemPaths() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

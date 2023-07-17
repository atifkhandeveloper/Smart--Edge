package com.abh80.smartedge.aod_edgelighting.item;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep
public class ItemIcon {
    public String name;
    public ArrayList<ItemPaths> paths;
    public int size;

    public ItemIcon(String name, ArrayList<ItemPaths> paths, int size) {
        this.name = name;
        this.paths = paths;
        this.size = size;
    }

    public ItemIcon() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ItemPaths> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<ItemPaths> paths) {
        this.paths = paths;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

package com.abh80.smartedge.aod_edgelighting.edge;

import androidx.annotation.Keep;
import androidx.core.view.ViewCompat;

import com.abh80.smartedge.aod_edgelighting.item.ItemIcon;


@Keep
public class ItemLogo {
    private int color;
    private String data;
    private String font;
    private ItemIcon itemIcon;
    private int style;
    private int value;

    public ItemLogo() {
        this.color = ViewCompat.MEASURED_STATE_MASK;
        this.style = 8;
    }

    public ItemLogo(ItemLogo itemLogo, float f) {
        this.style = itemLogo.getStyle();
        this.data = itemLogo.getData();
        this.font = itemLogo.getFont();
        this.value = itemLogo.getValue();
        if (this.style == 4) {
            this.color = (int) (((float) itemLogo.getColor()) * f);
        } else {
            this.color = itemLogo.getColor();
        }
        this.itemIcon = itemLogo.itemIcon;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String str) {
        this.font = str;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public ItemIcon getItemIcon() {
        return this.itemIcon;
    }

    public void setItemIcon(ItemIcon itemIcon2) {
        this.itemIcon = itemIcon2;
    }
}

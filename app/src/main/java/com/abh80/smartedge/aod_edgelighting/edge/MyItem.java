package com.abh80.smartedge.aod_edgelighting.edge;

import androidx.annotation.Keep;

import com.abh80.smartedge.aod_edgelighting.item.ItemIcon;
import com.yalantis.ucrop.view.CropImageView;

import java.util.ArrayList;

@Keep
public class MyItem {
    private int anim;
    private ArrayList<Integer> arrColor;
    private String cusRound;
    private float hC;
    private float hInf;
    private float height;
    private boolean holeType;
    private int id;
    private boolean infinityType;
    private ItemIcon itemIcon;
    private int linearStyle;
    private String name;
    private String nameGroupIcon;
    private float rBot;
    private float rTop;
    private float rTopInf;
    private float raBot;
    private float raTop;
    private boolean reverse;
    private int sizeLogo;
    private float stroke;
    private int style;
    private ItemLogo styleLogo;
    private int typeScreen;
    private float wC;
    private float wInf;
    private float xBot;
    private int xC;
    private int xLogo;
    private float xTop;
    private int yC;
    private int yLogo;

    public MyItem() {
        this.id = -1;
        this.name = "";
        this.typeScreen = 200;
        this.stroke = 50.0f;
        this.rTop = 200.0f;
        this.rBot = 100.0f;
        this.yC = 200;
        this.xC = 200;
        this.hC = 100.0f;
        this.wC = 100.0f;
        this.wInf = 100.0f;
        this.hInf = 100.0f;
        this.rTopInf = 100.0f;
        this.xTop = 120.0f;
        this.raTop = 50.0f;
        this.xBot = 120.0f;
        this.raBot = 50.0f;
        this.height = 60.0f;
        this.styleLogo = new ItemLogo();
        this.anim = 1;
        this.style = 0;
        this.reverse = false;
        this.linearStyle = 0;
        this.holeType = false;
        this.infinityType = false;
        this.cusRound = null;
        this.itemIcon = null;
        this.arrColor = null;
        this.nameGroupIcon = "";
        this.xLogo = -1;
        this.yLogo = -1;
        this.sizeLogo = CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION;
    }

    public MyItem(MyItem myItem, float f, float f2) {
        float f3 = f / f2;
        this.id = myItem.getId();
        this.name = myItem.getName();
        this.itemIcon = myItem.getItemIcon();
        this.arrColor = myItem.getArrColor();
        this.typeScreen = myItem.getTypeScreen();
        this.stroke = myItem.getStroke() * f3;
        this.rTop = myItem.getrTop() * f3;
        this.rBot = myItem.getrBot() * f3;
        this.xC = (int) (((float) myItem.getxC()) * f3);
        this.yC = (int) (((float) myItem.getyC()) * f3);
        this.wC = myItem.getwC() * f3;
        this.hC = myItem.gethC() * f3;
        this.wInf = myItem.getwInf() * f3;
        this.hInf = myItem.gethInf() * f3;
        this.rTopInf = myItem.getrTopInf() * f3;
        this.xTop = myItem.getxTop() * f3;
        this.raTop = myItem.getRaTop() * f3;
        this.xBot = myItem.getxBot() * f3;
        this.raBot = myItem.getRaBot() * f3;
        this.height = myItem.getHeight() * f3;
        if (myItem.getxLogo() != -1) {
            this.xLogo = (int) (((float) myItem.getxLogo()) * f3);
        } else {
            this.xLogo = -1;
        }
        if (myItem.getyLogo() != -1) {
            this.yLogo = (int) (((float) myItem.getyLogo()) * f3);
        } else {
            this.yLogo = -1;
        }
        this.sizeLogo = (int) (((float) myItem.getSizeLogo()) * f3);
        this.styleLogo = new ItemLogo(myItem.getStyleLogo(), f3);
        this.anim = myItem.getAnim();
        this.style = myItem.getStyle();
        this.reverse = myItem.isReverse();
        this.linearStyle = myItem.getLinearStyle();
        this.nameGroupIcon = myItem.getNameGroupIcon();
        this.holeType = myItem.isHoleType();
        this.infinityType = myItem.isInfinityType();
        this.cusRound = myItem.getCusRound();
    }

    public MyItem(MyItem myItem) {
        this.id = myItem.getId();
        this.name = myItem.getName();
        this.itemIcon = myItem.getItemIcon();
        this.arrColor = myItem.getArrColor();
        this.typeScreen = myItem.getTypeScreen();
        this.stroke = myItem.getStroke();
        this.rTop = myItem.getrTop();
        this.rBot = myItem.getrBot();
        this.xC = myItem.getxC();
        this.yC = myItem.getyC();
        this.wC = myItem.getwC();
        this.hC = myItem.gethC();
        this.wInf = myItem.getwInf();
        this.hInf = myItem.gethInf();
        this.rTopInf = myItem.getrTopInf();
        this.xTop = myItem.getxTop();
        this.raTop = myItem.getRaTop();
        this.xBot = myItem.getxBot();
        this.raBot = myItem.getRaBot();
        this.height = myItem.getHeight();
        this.anim = myItem.getAnim();
        this.style = myItem.getStyle();
        this.reverse = myItem.isReverse();
        this.linearStyle = myItem.getLinearStyle();
        this.holeType = myItem.isHoleType();
        this.infinityType = myItem.isInfinityType();
        this.xLogo = myItem.getxLogo();
        this.yLogo = myItem.getyLogo();
        this.sizeLogo = myItem.getSizeLogo();
        this.styleLogo = myItem.getStyleLogo();
        this.cusRound = myItem.getCusRound();
    }

    public MyItem(float f, String str, ItemIcon itemIcon2, int animSpeed, int... iArr) {
        this.id = -1;
        this.name = str;
        this.itemIcon = itemIcon2;
        this.arrColor = new ArrayList<>();
        for (int i : iArr) {
            this.arrColor.add(Integer.valueOf(i));
        }
        this.stroke = f;
        this.rTop = 70.0f;
        this.rBot = 70.0f;
        this.typeScreen = 200;
        this.style = 0;
        this.linearStyle = 0;
        this.anim = animSpeed;
        this.nameGroupIcon = "";
        this.yC = 200;
        this.xC = 200;
        this.hC = 100.0f;
        this.wC = 100.0f;
        this.holeType = false;
        this.infinityType = false;
        this.cusRound = null;
        this.xLogo = -1;
        this.yLogo = -1;
        this.sizeLogo = CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION;
        this.styleLogo = new ItemLogo();
        this.wInf = 100.0f;
        this.hInf = 100.0f;
        this.rTopInf = 100.0f;
        this.xTop = 120.0f;
        this.raTop = 50.0f;
        this.xBot = 120.0f;
        this.raBot = 50.0f;
        this.height = 60.0f;
    }

    public int getTypeScreen() {
        return this.typeScreen;
    }

    public void setTypeScreen(int i) {
        this.typeScreen = i;
    }

    public float getStroke() {
        return this.stroke;
    }

    public void setStroke(float f) {
        this.stroke = f;
    }

    public float getrTop() {
        return this.rTop;
    }

    public void setrTop(float f) {
        this.rTop = f;
    }

    public float getrBot() {
        return this.rBot;
    }

    public void setrBot(float f) {
        this.rBot = f;
    }

    public int getxC() {
        return this.xC;
    }

    public void setxC(int i) {
        this.xC = i;
    }

    public int getyC() {
        return this.yC;
    }

    public void setyC(int i) {
        this.yC = i;
    }

    public float getwC() {
        return this.wC;
    }

    public void setwC(float f) {
        this.wC = f;
    }

    public float gethC() {
        return this.hC;
    }

    public void sethC(float f) {
        this.hC = f;
    }

    public float getxTop() {
        return this.xTop;
    }

    public void setxTop(float f) {
        this.xTop = f;
    }

    public float getRaTop() {
        return this.raTop;
    }

    public void setRaTop(float f) {
        this.raTop = f;
    }

    public float getxBot() {
        return this.xBot;
    }

    public void setxBot(float f) {
        this.xBot = f;
    }

    public float getRaBot() {
        return this.raBot;
    }

    public void setRaBot(float f) {
        this.raBot = f;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public int getAnim() {
        return this.anim;
    }

    public void setAnim(int i) {
        this.anim = i;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public boolean isReverse() {
        return this.reverse;
    }

    public void setReverse(boolean z) {
        this.reverse = z;
    }

    public int getLinearStyle() {
        return this.linearStyle;
    }

    public void setLinearStyle(int i) {
        this.linearStyle = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public ArrayList<Integer> getArrColor() {
        return this.arrColor;
    }

    public void setArrColor(ArrayList<Integer> arrayList) {
        this.arrColor = arrayList;
    }

    public ItemIcon getItemIcon() {
        return this.itemIcon;
    }

    public void setItemIcon(ItemIcon itemIcon2) {
        this.itemIcon = itemIcon2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getNameGroupIcon() {
        return this.nameGroupIcon;
    }

    public void setNameGroupIcon(String str) {
        this.nameGroupIcon = str;
    }

    public boolean isHoleType() {
        return this.holeType;
    }

    public void setHoleType(boolean z) {
        this.holeType = z;
    }

    public float getwInf() {
        return this.wInf;
    }

    public void setwInf(float f) {
        this.wInf = f;
    }

    public float gethInf() {
        return this.hInf;
    }

    public void sethInf(float f) {
        this.hInf = f;
    }

    public float getrTopInf() {
        return this.rTopInf;
    }

    public void setrTopInf(float f) {
        this.rTopInf = f;
    }

    public boolean isInfinityType() {
        return this.infinityType;
    }

    public void setInfinityType(boolean z) {
        this.infinityType = z;
    }

    public int getxLogo() {
        return this.xLogo;
    }

    public void setxLogo(int i) {
        this.xLogo = i;
    }

    public int getyLogo() {
        return this.yLogo;
    }

    public void setyLogo(int i) {
        this.yLogo = i;
    }

    public int getSizeLogo() {
        return this.sizeLogo;
    }

    public void setSizeLogo(int i) {
        this.sizeLogo = i;
    }

    public ItemLogo getStyleLogo() {
        return this.styleLogo;
    }

    public void setStyleLogo(ItemLogo itemLogo) {
        this.styleLogo = itemLogo;
    }

    public String getCusRound() {
        return this.cusRound;
    }

    public void setCusRound(String str) {
        this.cusRound = str;
    }
}

package com.abh80.smartedge.aod_edgelighting.edge;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;

public class MyShare {
    private static SharedPreferences getShare(Context context) {
        return context.getSharedPreferences("pre", 0);
    }

    public static void putDrawLive(Context context, boolean z) {
        getShare(context).edit().putBoolean("draw_live", z).apply();
    }

    public static boolean isDrawLive(Context context) {
        return getShare(context).getBoolean("draw_live", false);
    }

    public static void putCount(Context context) {
        getShare(context).edit().putInt("count_new", getCount(context) + 1).apply();
    }

    public static int getCount(Context context) {
        return getShare(context).getInt("count_new", 0);
    }

    public static void putThemeUse(Context context, MyItem myItem) {
        putCount(context);
        getShare(context).edit().putString("theme_use", new Gson().toJson(myItem)).apply();
    }

    public static MyItem getThemeUse(Context context) {
        String string = getShare(context).getString("theme_use", "");
        if (!string.isEmpty()) {
            MyItem myItem = (MyItem) new Gson().fromJson(string, new TypeToken<MyItem>() {
                /* class com.colorwallpaper.lovephone.example.utils.MyShare.AnonymousClass1 */
            }.getType());
            if (myItem != null) {
                return myItem;
            }
        }
        MyItem myItem = new MyItem(20, "Default 1", null, Color.parseColor("#ff0000"), Color.parseColor("#ffff00"), Color.parseColor("#00ff00"), Color.parseColor("#00ffff"), Color.parseColor("#0000ff"), Color.parseColor("#ff00ff"));

        return myItem;
    }

    public static void putMyTheme(Context context, ArrayList<MyItem> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<MyItem> it = arrayList.iterator();
        while (it.hasNext()) {
            MyItem next = it.next();
            if (next != null) {
                arrayList2.add(next);
            }
        }
        getShare(context).edit().putString("my_theme", new Gson().toJson(arrayList2)).apply();
    }

    public static ArrayList<MyItem> getMyTheme(Context context) {
        String string = getShare(context).getString("my_theme", "");
        if (!string.isEmpty()) {
            ArrayList<MyItem> arrayList = (ArrayList) new Gson().fromJson(string, new TypeToken<ArrayList<MyItem>>() {
                /* class com.colorwallpaper.lovephone.example.utils.MyShare.AnonymousClass2 */
            }.getType());
            if (arrayList != null) {
                return arrayList;
            }
        }
        return new ArrayList<>();
    }

    public static void putShowAgain(Context context, boolean z) {
        getShare(context).edit().putBoolean("show_again", z).apply();
    }

    public static boolean isShowAgain(Context context) {
        return getShare(context).getBoolean("show_again", true);
    }



    public static void customThemeNumber(Context context, String value) {
        SharedPreferences prefs = context.getSharedPreferences("number_theme",0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("number_theme", value);
        editor.apply();
    }

    public static String getStr(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("number_theme", 0);
        return prefs.getString("number_theme","multi_color");
    }
}

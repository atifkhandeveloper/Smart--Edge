package com.abh80.smartedge.aod_edgelighting;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static SharedPref mHenrySharedPref;
    private final SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("prank_call", Context.MODE_PRIVATE);
    }

    public static SharedPref getInstance(Context context) {
        if (mHenrySharedPref == null) {
            mHenrySharedPref = new SharedPref(context);
        }
        return mHenrySharedPref;
    }

    public void isPolicyRead(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getPolicyRead(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return key;
    }


    public void setAppId(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getAppId(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return key;
    }


    public void setNativeId(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getNativeId(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return key;
    }


    public void setInterId(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getInterId(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return key;
    }


    public void setAppOpenId(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getAppOpenId(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return key;
    }


}

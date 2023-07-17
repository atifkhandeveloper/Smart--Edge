package com.abh80.smartedge.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.abh80.smartedge.R;
import com.google.android.gms.ads.nativead.NativeAd;


public class Constants {

    public static String layouttype = "";

    public  static NativeAd nativeAd ;

    public static FrameLayout banner;
    public static com.google.android.gms.ads.AdView New_madView ;
    public static String layout = "";

    public static int TIMER = 0;

    public static boolean IS_VOICE = false;
    public static boolean IS_VIDEO = false;
    public static boolean IS_WA = false;
    public static boolean IS_FB = false;
    public static boolean IS_SYS = false;

    public static boolean IS_APP_OPEN_AD = false;
    public static Bitmap CHAR_BITMAP;
    public static Bitmap MAIN_CHAR_BITMAP;

    public static int rgForm = 1;
    public static int rgVid = 1;


    public static int RG_TIME = 1;
    public static String STATUS_TIME = "Wait for Two seconds";


    /*private static TinyDB tinyDB;*/

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }


    public static void shareApp(Context context) throws Exception {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
        String sAux = "\nLet me recommend you this application\n\n" + context.getResources().getString(R.string.app_name) + "\n\n";
        sAux = sAux + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + " \n\n";
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        context.startActivity(Intent.createChooser(i, "choose one"));
    }

    public static void moreApps(Context context, String account_name) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + account_name)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=pub:" + account_name)));
        }
    }

    public static void rate(Context context, String app_link) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app_link)));
        } catch (ActivityNotFoundException ex) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app_link)));
        }
    }






    /*public static void setIds(Context context) {
        tinyDB = new TinyDB(context);
        Log.d("addds", "setIds: " + tinyDB.getSelectedId());
        switch (tinyDB.getSelectedId()) {
            *//*case 1:
                tinyDB.setSelectedId(2);
                tinyDB.setInterestitialId(context.getResources().getString(R.string.admob_interestitial_id_1));
                tinyDB.setNativeId(context.getResources().getString(R.string.admob_native_id_1));
                break;
            case 2:
                tinyDB.setSelectedId(3);
                tinyDB.setInterestitialId(context.getResources().getString(R.string.admob_interestitial_id_2));
                tinyDB.setNativeId(context.getResources().getString(R.string.admob_native_id_2));
                break;
            case 3:
                tinyDB.setSelectedId(4);
                tinyDB.setInterestitialId(context.getResources().getString(R.string.admob_interestitial_id_3));
                tinyDB.setNativeId(context.getResources().getString(R.string.admob_native_id_3));
                break;
            case 4:
                tinyDB.setSelectedId(1);
                tinyDB.setInterestitialId(context.getResources().getString(R.string.admob_interestitial_id_4));
                tinyDB.setNativeId(context.getResources().getString(R.string.admob_native_id_4));
                break;*//*

        }
    }*/


}

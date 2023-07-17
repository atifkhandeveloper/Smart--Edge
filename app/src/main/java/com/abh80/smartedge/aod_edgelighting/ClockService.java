package com.abh80.smartedge.aod_edgelighting;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.app.NotificationCompat.CATEGORY_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.abh80.smartedge.R;


public class ClockService extends Service {

    private final BroadcastReceiver mScreenStateBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, final Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent intent1 = new Intent(getApplicationContext(), DisplayActivity.class);
//                        intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent1);
                    }
                }, 1000);

            }

        }

    };
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    NotificationChannel notificationChannel;
    String NOTIFICATION_CHANNEL_ID = "1";

    public ClockService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate: ");

        Bitmap IconLg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);

        mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Clock Service")
                .setContentText("Always On Display is running .")
                .setTicker("Always running...")
//                .setSmallIcon(R.drawable.ic_main)
                .setLargeIcon(IconLg)
                .setCategory(CATEGORY_SERVICE)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000})
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setAutoCancel(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotifyManager.createNotificationChannel(notificationChannel);

            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            startForeground(1, mBuilder.build());
        } else {
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotifyManager.notify(1, mBuilder.build());
        }


        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStateBroadcastReceiver, screenStateFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onCreate: ");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mScreenStateBroadcastReceiver);

        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(true);
        } else {
            mNotifyManager.cancel(1);
        }

        super.onDestroy();
    }

}



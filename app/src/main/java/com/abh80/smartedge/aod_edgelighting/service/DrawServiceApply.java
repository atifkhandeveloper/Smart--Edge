package com.abh80.smartedge.aod_edgelighting.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.abh80.smartedge.R;
import com.abh80.smartedge.activities.MainActivity;
import com.abh80.smartedge.aod_edgelighting.edge.ConstMy;
import com.abh80.smartedge.aod_edgelighting.edge.MyShare;
import com.abh80.smartedge.aod_edgelighting.edge.PreviewNoBg;
import com.google.android.material.badge.BadgeDrawable;


public class DrawServiceApply extends Service {
    private boolean isAdd;
    private WindowManager mWindowManager;
    private PreviewNoBg myPreview;
    private RemoteViews notiSmall;
    private Notification notification;
    private NotificationManager notificationManager;
    public final Point point = new Point();

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    DrawServiceApply.this.myPreview.startAnim();
                } else {
                    DrawServiceApply.this.myPreview.stopAnim();
                }
            }
        }
    };

    private WindowManager.LayoutParams wmParams;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    public void onCreate() {
        super.onCreate();
        MyShare.putDrawLive(this, true);
        @SuppressLint("WrongConstant") WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService("window");
        this.mWindowManager = windowManager;
        windowManager.getDefaultDisplay().getRealSize(this.point);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        this.wmParams = layoutParams;
        layoutParams.format = -3;
        this.wmParams.x = 0;
        this.wmParams.y = 0;
        this.wmParams.width = this.point.x;
        this.wmParams.height = this.point.y;
        this.wmParams.gravity = BadgeDrawable.TOP_START;
        this.wmParams.flags = 792;
        PreviewNoBg previewNoBg = new PreviewNoBg(this);
        this.myPreview = previewNoBg;
        previewNoBg.setSystemUiVisibility(1792);
        this.myPreview.setMyItem(MyShare.getThemeUse(this));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(this.receiver, intentFilter);

        makeNotification();
        addView();

    }

    private void addView() {
        if (!this.isAdd) {
            this.isAdd = true;
            this.mWindowManager.addView(this.myPreview, this.wmParams);
            this.myPreview.startAnim();
        }
    }

    public void removeView() {
        if (this.isAdd) {
            this.isAdd = false;
            this.myPreview.stopAnim();
            this.mWindowManager.removeView(this.myPreview);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        String action = intent.getAction();
        if (action != null) {
            action.hashCode();
            char c = 65535;
            switch (action.hashCode()) {
                case 1070418393:
                    if (action.equals(ConstMy.ACTION_CHANGE)) {
                        c = 0;
                        break;
                    }
                    break;
                case 1583723627:
                    if (action.equals(ConstMy.ACTION_STOP)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1847461549:
                    if (action.equals(ConstMy.ACTION_PAUSE)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    update();
                    break;
                case 1:
                    stopSelf();
                    break;
                case 2:
                    if (this.isAdd) {
                        removeView();
                        this.notiSmall.setTextViewText(R.id.tv_pause, "Resume");
                    } else {
                        addView();
                        this.notiSmall.setTextViewText(R.id.tv_pause, "Pause");
                    }
                    this.notificationManager.notify(123, this.notification);
                    break;
            }
        }
        return super.onStartCommand(intent, i, i2);
    }
    private void update() {
        this.myPreview.setMyItem(MyShare.getThemeUse(this));
    }
    public void onDestroy() {
        MyShare.putDrawLive(this, false);
        removeView();
        unregisterReceiver(this.receiver);
        stopForeground(true);
        super.onDestroy();
    }

    @SuppressLint("WrongConstant")
    public final void makeNotification() {
        this.notiSmall = new RemoteViews(getPackageName(), (int) R.layout.layout_notification);
        //TODO Change Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(268435456);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager notificationManager2 = (NotificationManager) getSystemService("notification");
        this.notificationManager = notificationManager2;
        if (notificationManager2 != null) {
            Uri defaultUri = RingtoneManager.getDefaultUri(2);
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel("notification_channel_id", getPackageName(), 4);
                notificationChannel.setSound(defaultUri, new AudioAttributes.Builder().setUsage(5).build());
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                this.notification = new Notification.Builder(this, "notification_channel_id").build();
                this.notificationManager.createNotificationChannel(notificationChannel);
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification_channel_id");
                builder.setSound(defaultUri);
                Notification build = builder.build();
                this.notification = build;
                build.vibrate = new long[]{0, 500, 0, 500};
                this.notification.defaults = 3;
                builder.setPriority(2);
            }
            Intent intent2 = new Intent(this, DrawServiceApply.class);
            intent2.setAction(ConstMy.ACTION_STOP);
            PendingIntent service = PendingIntent.getService(this, 111, intent2, 134217728);
            Intent intent3 = new Intent(this, DrawServiceApply.class);
            intent3.setAction(ConstMy.ACTION_PAUSE);
            this.notiSmall.setOnClickPendingIntent(R.id.tv_pause, PendingIntent.getService(this, 111, intent3, 134217728));
            this.notiSmall.setOnClickPendingIntent(R.id.tv_stop, service);
            this.notification.contentView = this.notiSmall;
            this.notification.icon = R.mipmap.ic_launcher;
            this.notification.contentIntent = activity;
            startForeground(123, this.notification);
            notificationManager2.cancel(123);
        }
    }
}

package com.abh80.smartedge.aod_edgelighting

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.abh80.smartedge.R


class ScreenService : Service() {


//    private lateinit var mScreenReceiver: ScreenReceiver
    var mNotifyManager: NotificationManager? = null
    var mBuilder: NotificationCompat.Builder? = null
    var notificationChannel: NotificationChannel? = null
    var NOTIFICATION_CHANNEL_ID = "1"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        val IconLg = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)

        mNotifyManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mBuilder = NotificationCompat.Builder(this)
        mBuilder!!.setContentTitle("Clock Service")
            .setContentText("Always On Display is running .")
            .setTicker("Always running...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setLargeIcon(IconLg)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(Notification.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            // Configure the notification channel.
            notificationChannel!!.description = "Channel description"
            notificationChannel!!.enableLights(true)
            notificationChannel!!.lightColor = Color.BLUE
            notificationChannel!!.vibrationPattern = longArrayOf(1000)
            notificationChannel!!.enableVibration(true)
            notificationChannel!!.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            mNotifyManager!!.createNotificationChannel(notificationChannel!!)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            startForeground(1, mBuilder!!.build())
        } else {
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotifyManager!!.notify(1, mBuilder!!.build())
        }



        registerScreenStatusReceiver()
    }

    override fun onDestroy() {
        unregisterScreenStatusReceiver()
        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(true)
        } else {
            mNotifyManager!!.cancel(1)
        }
    }

    private fun registerScreenStatusReceiver() {
//        mScreenReceiver = ScreenReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(BatteryManager.EXTRA_LEVEL)
//        registerReceiver(mScreenReceiver, filter)
    }

    private fun unregisterScreenStatusReceiver() {
        try {
//            unregisterReceiver(mScreenReceiver)
        } catch (e: IllegalArgumentException) {
            e.message
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }


}
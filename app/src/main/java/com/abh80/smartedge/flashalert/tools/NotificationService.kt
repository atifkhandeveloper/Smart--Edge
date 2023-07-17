package com.abh80.smartedge.flashalert.tools

import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.telecom.TelecomManager
import android.util.Log
import com.abh80.smartedge.flashalert.FlashAlert
import com.abh80.smartedge.flashalert.db.AppDatabase
import com.abh80.smartedge.flashalert.db.AppEntryRepository
import com.abh80.smartedge.flashalert.impl.CameraAccess
import com.abh80.smartedge.flashalert.impl.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*


class NotificationService : NotificationListenerService() {

    private lateinit var mContext: Context
    lateinit var mCameraImpl: CameraAccess//CameraImpl
    private val notificationScope = MainScope()

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        mCameraImpl = CameraAccess(mContext)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pack = sbn.packageName

        notificationScope.launch(Dispatchers.IO)  {
            val smsPackage = Telephony.Sms.getDefaultSmsPackage(mContext)
            val callerPackage = mContext.getSystemService(TelecomManager::class.java)?.defaultDialerPackage?:"empty"

            if (pack != smsPackage && pack != callerPackage && DeviceController.continueEvent(mContext)) {

                val selectorDao = AppDatabase.INSTANCE?.appSelector()
                if (selectorDao != null) {
                    val appEntry = AppEntryRepository(selectorDao)
                    val item = appEntry.getItemByPackage(pack)
                    Log.d("FLASH", "=> $item")
                    if (item != null && item.selected) {
                        if (!FlashAlert.isRunning)
                            FlashAlert.isRunning = mCameraImpl.toggleStroboscope(default)

                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                if (FlashAlert.isRunning) {
                                    FlashAlert.isRunning = false
                                    mCameraImpl.stopStroboscope()
                                }
                            }
                        }, 1500)
                    }
                }
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

        Log.i("Msg", "Notification Removed ${sbn.packageName}")
        if (isDefaultDialer(mContext,sbn.packageName)){
            mCameraImpl.stopStroboscope()
        }

    }

    private fun isDefaultDialer(context: Context, packageNameToCheck: String = context.packageName): Boolean {
        val dialingIntent = Intent(Intent.ACTION_DIAL)
        val resolveInfoList = packageManager.queryIntentActivities(dialingIntent, 0)
        if (resolveInfoList.size != 1)
            return false
        return packageNameToCheck == resolveInfoList[0].activityInfo.packageName
    }
}

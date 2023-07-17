package com.abh80.smartedge.flashalert.tools

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.abh80.smartedge.flashalert.FlashAlert
import kotlinx.coroutines.delay

object DeviceController {

    private const val zenMode = "zen_mode"
    private const val zenModeOff = 0
    private lateinit var compassInfo: CompassInfo
    private const val delayFlash = 5000
    private const val keyLastFlash = "lastFlash"
    private const val TAG = "DeviceController"
    
    @Synchronized
    suspend fun continueEvent(context: Context, test: Boolean = false): Boolean{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        // Controle si le device a une camera avec flash

        val hasFlash = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (!hasFlash && !test)
            return false
        Log.d(TAG, "continueEvent: 1 ")
        // Controle si l'application est active ou non
        val isActive = sharedPreferences.getBoolean("isActivate",true)

        if (!isActive)
            return false

        Log.d(TAG, "continueEvent: 2")
        val now = System.currentTimeMillis()

        if ((sharedPreferences.getLong(keyLastFlash,(now- delayFlash) ) + delayFlash) > now){
            return false
        }
        Log.d(TAG, "continueEvent: 3")
        val onScreeOn = sharedPreferences.getBoolean("event_screenon",false)
        val isScreenOn = isScreenOn(context)
        if (onScreeOn && isScreenOn && !FlashAlert.isRunning)
            return false
        Log.d(TAG, "continueEvent: 4")
        val dnd = getDNDMode(context)
        Log.d(TAG, "continueEvent: DND $dnd")
        // 0 - If DnD is off.
        // 1 - If DnD is on -RenderScript.Priority Only
        // 2 - If DnD is on - Total Silence
        // 3 - If DnD is on - Alarms Only
        if (dnd > 0 && !sharedPreferences.getBoolean("event_dnd", false) && !FlashAlert.isRunning){
            return false
        }
        Log.d(TAG, "continueEvent: 5")
        val onlyFlat = sharedPreferences.getBoolean("event_onlyflat",false)
        if (onlyFlat && !FlashAlert.isRunning && !deviceIsFlat(context))
            return false
        Log.d(TAG, "continueEvent: 6")
        val perferenceNoise = sharedPreferences.getBoolean("event_noisedetector",true)
        val noiseLimit = sharedPreferences.getInt("event_noiselimit",80)
        if(perferenceNoise && !FlashAlert.isRunning && !noiseIsImportant(noiseLimit,context)){
            return false
        }
        Log.d(TAG, "continueEvent: 7")
        sharedPreferences.edit {
            putLong(keyLastFlash,System.currentTimeMillis())
        }
        return true
    }

    private fun isScreenOn(context: Context): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isInteractive
    }

    private fun getDNDMode(context: Context): Int{
        val resolver = context.contentResolver
        return try {
            Settings.Global.getInt(resolver, zenMode)
        } catch (e: Settings.SettingNotFoundException) {
            zenModeOff
        }
    }

    private suspend fun deviceIsFlat(mContext:Context):Boolean{
        val mSensorManager = mContext.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        compassInfo = CompassInfo(object : DeviceControllerCallBack {
            override fun onSensorEvent() {
                mSensorManager.unregisterListener(compassInfo)
            }
        })
        val accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        mSensorManager.registerListener(compassInfo, accelerometer, SensorManager.SENSOR_DELAY_UI)
        delay(500)

        return compassInfo.flatEnough()
    }

    private suspend fun noiseIsImportant(limit: Int,context: Context): Boolean{
        return if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context.applicationContext,
                Manifest.permission.RECORD_AUDIO)) {
            val sound = SoundDetector()
            sound.start()
            val value = sound.getAverageLevel()
            value > limit
        } else {
            true        }
    }


    interface DeviceControllerCallBack {
        fun onSensorEvent()
    }

}
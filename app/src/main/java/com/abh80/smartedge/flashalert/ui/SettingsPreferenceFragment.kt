package com.abh80.smartedge.flashalert.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.abh80.smartedge.R
import com.abh80.smartedge.flashalert.FlashMainActivity
import com.abh80.smartedge.flashalert.impl.IPermissionCallBack
import com.abh80.smartedge.flashalert.tools.PermissionHelper
import com.abh80.smartedge.flashalert.tools.ThemeSelector



class SettingsPreferenceFragment : androidx.preference.PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener,
    IPermissionCallBack {

    private val arrayKeys = arrayListOf("settings_all", "settings_contacts")
    private lateinit var permissionHelper: PermissionHelper
    private lateinit var mContext: Context
    private lateinit var mActivity: FlashMainActivity


    override fun onResume() {
        super.onResume()
        val startSwitch = findPreference<SwitchPreference>("isActivate")
        startSwitch?.isChecked = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("isActivate", true)
    }

    override fun onPermissionEvent(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        for (i in permissions.indices) {
            when (permissions[i]) {
                "android.permission.RECEIVE_MMS",
                "android.permission.RECEIVE_SMS" -> {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val switch = findPreference<SwitchPreference>("event_sms")
                        switch?.isChecked = false
                    }
                }
                "android.permission.READ_PHONE_STATE",
                "android.permission.READ_CALL_LOG" -> {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val switch = findPreference<SwitchPreference>("event_call")
                        switch?.isChecked = false
                    }
                }
                "android.permission.READ_CONTACTS" -> {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val switch = findPreference<SwitchPreference>("settings_contacts")
                        switch?.isChecked = false
                    } else {
                        disableOther("settings_contacts")
                    }
                }
                "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" -> {
                    val notification = findPreference<Preference>("event_apps")
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        notification?.summary = getString(R.string.notify_list_apps)
                    } else {
                        notification?.summary = getString(R.string.notify_grant_permission)
                    }
                }
                "android.permission.RECORD_AUDIO" -> {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val switch = findPreference<SwitchPreference>("event_noisedetector")
                        switch?.isChecked = false
                    } else {
                        disableOther("event_noisedetector")
                    }
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p0 != null && p1 != null && isBooleanPreferences(p0,p1)) {
            val checked = p0.getBoolean(p1, false)

            when {
                p1 == "event_call" && checked -> {
                    if (!permissionHelper.callHasPermission())
                        permissionHelper.askPermission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG)
                }
                p1 == "event_sms" && checked -> {
                    if (!permissionHelper.smsHasPermission())
                        permissionHelper.askPermission(Manifest.permission.RECEIVE_SMS, Manifest.permission.RECEIVE_MMS)
                }
                p1 == "settings_contacts" && checked -> {
                    if (!permissionHelper.contactsHasPermission())
                        permissionHelper.askPermission(Manifest.permission.READ_CONTACTS)
                    else
                        disableOther(p1)
                }
                p1 == "event_noisedetector" && checked -> {
                    if (!permissionHelper.soundHasPermission())
                        permissionHelper.askPermission(Manifest.permission.RECORD_AUDIO)
                    else
                        disableOther(p1)
                }
                p1 == "isActivate" -> {
                    val startSwitch = findPreference<SwitchPreference>("isActivate")
                    startSwitch?.isChecked = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("isActivate", true)
                }

                checked -> disableOther(p1)
            }

        }
    }

    private fun isBooleanPreferences(prefs: SharedPreferences, key: String): Boolean{
        val all = prefs.all
        return if (all[key] != null) all[key] is Boolean else false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as FlashMainActivity

        PreferenceManager.getDefaultSharedPreferences(mContext).registerOnSharedPreferenceChangeListener(this)

    }

    override fun onDetach() {
        super.onDetach()
        PreferenceManager.getDefaultSharedPreferences(mContext).unregisterOnSharedPreferenceChangeListener(this)

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        permissionHelper = PermissionHelper(requireActivity())
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.R) && !permissionHelper.cameraHasPermission()) {
            permissionHelper.displayInfo()
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        addPreferencesFromResource(R.xml.pref_event)
        addPreferencesFromResource(R.xml.pref_settings)
        addPreferencesFromResource(R.xml.pref_other)
        val switchSMS = findPreference<SwitchPreference>("event_sms")
        val switchCall = findPreference<SwitchPreference>("event_call")
        val switchContact = findPreference<SwitchPreference>("settings_contacts")
        val switchSound = findPreference<SwitchPreference>("event_noisedetector")
        val screenSound = findPreference<Preference>("event_noiselimit")
        val dayAndNight = findPreference<Preference>("settings_theme")

        switchSMS?.isChecked = (sharedPreferences.getBoolean("event_sms", false) && permissionHelper.smsHasPermission())
        switchCall?.isChecked = (sharedPreferences.getBoolean("event_call", false) && permissionHelper.callHasPermission())

        switchContact?.isChecked = (sharedPreferences.getBoolean("settings_contacts", false) && permissionHelper.contactsHasPermission())

        switchSound?.isChecked = (sharedPreferences.getBoolean("event_noisedetector", false) && permissionHelper.soundHasPermission())
        screenSound?.summary = (sharedPreferences.getInt("event_noiselimit", 0)).toString()

        dayAndNight?.setOnPreferenceClickListener {
            val alertDialogTheme =  AlertDialogThemeSelector()
            alertDialogTheme.show(mActivity.supportFragmentManager, "tag")
            alertDialogTheme.addDialogCloseListener(object : OnDialogCloseListener {
                override fun onDialogClose() {
                    mActivity.delegate.localNightMode = ThemeSelector.getTheme(requireContext())
                }
            })
            true
        }

        screenSound?.setOnPreferenceClickListener {
            val alertDialogSound =  AlertDialogSoundSelector()
            alertDialogSound.show(mActivity.supportFragmentManager, "tag")
            alertDialogSound.addDialogCloseListener(object : OnDialogCloseListener {
                override fun onDialogClose() {
                    refreshSoundLevel()
                }
            })
            true
        }

        findPreference<Preference>("event_apps")?.let {
            val granted = permissionHelper.canReadNotifications()

            if(granted)
                it.summary = getString(R.string.notify_list_apps)
            else
                it.summary = getString(R.string.notify_grant_permission)

            it.setOnPreferenceClickListener{
                if (permissionHelper.canReadNotifications()) {
                    mActivity.supportFragmentManager.beginTransaction().replace(android.R.id.content, AppListPreferenceFragment(), "NOTIFYAPP").addToBackStack("NOTIFYAPP").commit()
                    mActivity.invalidateOptionsMenu()
                } else {
                    permissionHelper.buildNotificationServiceAlertDialog().show()
                }
                true
            }
        }
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)) {
            switchSound?.isVisible = false
            screenSound?.isVisible = false
            sharedPreferences.edit(commit = true) {
                putBoolean("event_noisedetector", false)
                putInt("event_noiselimit", 0)
            }
        }
    }

    private fun refreshSoundLevel(){
        val screenSound = findPreference<Preference>("event_noiselimit")
        screenSound?.summary = (PreferenceManager.getDefaultSharedPreferences(mContext).getInt("event_noiselimit", 0)).toString()
    }

    private fun disableOther(str: String) {
        if (arrayKeys.contains(str)) {

            arrayKeys.filter { it != str }.forEach {
                val switch = findPreference<SwitchPreference>(it)
                switch?.isChecked = false
            }
        }
    }
}
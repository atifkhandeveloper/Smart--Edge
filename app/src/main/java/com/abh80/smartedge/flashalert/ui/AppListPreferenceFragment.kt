package com.abh80.smartedge.flashalert.ui

import android.content.Context.TELECOM_SERVICE
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telecom.TelecomManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.abh80.smartedge.R
import com.abh80.smartedge.flashalert.db.AppDatabase
import com.abh80.smartedge.flashalert.db.AppEntryRepository
import com.abh80.smartedge.flashalert.poko.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*


class AppListPreferenceFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    private lateinit var checkBoxPreference: CheckBoxPreference
    private lateinit var appEntry: AppEntryRepository
    private lateinit var appList: MutableList<AppItem>
    private lateinit var screen: PreferenceScreen
    private lateinit var dialogFragment: WaitDialogFragment
    private val appScope = MainScope()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.prefs_apps)
        if( activity is AppCompatActivity && activity != null){
            val appCompatActivity: AppCompatActivity = activity as AppCompatActivity
            appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        screen = preferenceScreen
        showDialog()
        reloadAppList()
    }

    private fun showDialog(){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            val prev = requireActivity().supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                remove(prev)
            }
            addToBackStack(null)
            dialogFragment = WaitDialogFragment()
            dialogFragment.show(this, "dialog")
        }
    }

    fun refreshAppList(){
        screen.removeAll()
        showDialog()
        appScope.launch(Dispatchers.IO)  {
            appEntry.clearAll()
            reloadAppList()
        }
    }

    private fun reloadAppList(){
        //val drawable = ChromeFloatingCirclesDrawable.Builder(context).build()

        val pm = context?.packageManager
        appList = mutableListOf()

        appScope.launch(Dispatchers.IO)  {
            val selectorDao = AppDatabase.INSTANCE?.appSelector()

            if (selectorDao != null) {
                appEntry = AppEntryRepository(selectorDao)
                appList.addAll(appEntry.getAllApps())

                if (appList.size == 0) {
                    pm?.let {
                        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                        lateinit var appItem: AppItem
                        packages.filter { pm.getLaunchIntentForPackage(it.packageName) != null }.forEach {

                            appItem = AppItem(pm.getApplicationLabel(it).toString(), it.packageName, false)
                            val defaultSMS =Telephony.Sms.getDefaultSmsPackage(context)
                            val manger = context?.getSystemService(TELECOM_SERVICE) as TelecomManager?
                            val dialer = manger?.defaultDialerPackage?:packages

                            if (it.packageName != defaultSMS && it.packageName != dialer ) {
                                appList.add(appItem)
                                appEntry.inserAppItem(appItem)
                            }
                        }
                    }
                }

                appScope.launch(Dispatchers.Main) {
                    var addItem = true
                    appList.sortedBy { it.name.lowercase(Locale.ENGLISH) }.forEach {
                        checkBoxPreference = CheckBoxPreference(requireContext())
                        checkBoxPreference.apply {
                            title = it.name
                            key = it.packageId
                            isChecked = it.selected
                            icon = try {
                                pm?.getApplicationIcon(it.packageId)
                            } catch( e: PackageManager.NameNotFoundException){
                                appEntry.deleteAppItem(it)
                                addItem = false
                                ContextCompat.getDrawable(context, R.mipmap.ic_launcher)
                            }
                            onPreferenceClickListener = this@AppListPreferenceFragment
                        }
                        if (addItem)
                            screen.addPreference(checkBoxPreference)
                    }
                    dialogFragment.dismiss()
                }
            }
        }
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        if (preference is CheckBoxPreference){
            appEntry.updateItemFromPackage(preference.key,preference.isChecked)
        }
        return true
    }
}
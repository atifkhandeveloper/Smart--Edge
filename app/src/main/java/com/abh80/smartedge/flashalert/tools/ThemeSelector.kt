package com.abh80.smartedge.flashalert.tools

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object ThemeSelector {

    fun setTheme(mContext: Context){
        changeTheme(PreferenceManager.getDefaultSharedPreferences(mContext).getInt("settings_theme",-1))
    }

    fun changeTheme(theme: Int){
        when(theme){
            0 -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
            1 -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}
            else -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)}
        }
    }

    fun getTheme(mContext: Context) : Int{
       return when(PreferenceManager.getDefaultSharedPreferences(mContext).getInt("settings_theme",-1)){
            0 -> {AppCompatDelegate.MODE_NIGHT_NO}
            1 -> {AppCompatDelegate.MODE_NIGHT_YES}
            else -> {AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM}
        }
    }
}
package com.abh80.smartedge.flashalert.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.abh80.smartedge.R
import com.abh80.smartedge.flashalert.tools.ThemeSelector


class AlertDialogThemeSelector : DialogFragment() {

    private var listener: OnDialogCloseListener? = null

    override fun onDetach() {
        dismiss()
        super.onDetach()
    }

    override fun onPause() {
        super.onPause()
        onDetach()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {mActivity->
            val builder = AlertDialog.Builder(mActivity)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity)
            builder.apply {
                val selected = sharedPreferences.getInt("settings_theme",-1)
                setSingleChoiceItems(R.array.theme, selected) {
                    dialog, which ->
                    sharedPreferences.edit {
                        putInt("settings_theme",which)
                    }
                    ThemeSelector.changeTheme(which)
                    listener?.onDialogClose()
                    dialog.dismiss()
                }
            }
            builder.setCancelable(false)
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    fun addDialogCloseListener(listerner: OnDialogCloseListener){
        this.listener = listerner
    }
}
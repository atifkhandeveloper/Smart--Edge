package com.abh80.smartedge.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.view.Window
import android.widget.TextView
import com.abh80.smartedge.R
import org.apache.commons.lang3.ClassUtils.getPackageName


object PermissionDialog {

    fun showDialog(
        context: Context,
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.overlay_permission_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvAllow = dialog.findViewById<TextView>(R.id.btnPermissionOverlay)
        tvAllow.setOnClickListener {
            var intent: Intent? = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()))
            context.startActivity(intent)
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            dialog.dismiss()

        }
        dialog.show()
    }

}
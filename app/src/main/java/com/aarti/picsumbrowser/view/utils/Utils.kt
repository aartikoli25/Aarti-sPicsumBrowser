package com.aarti.picsumbrowser.view.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build

object Utils {
    fun isAboveMinSDK(minSDK: Int): Boolean {
        return Build.VERSION.SDK_INT >= minSDK
    }


    fun isAppInstalledOrNot(context: Context, pkg: String): Boolean {
        val pm = context.packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    fun scanForActivity(context: Context?): Activity? {
        if (context == null) return null else if (context is Activity) return context else if (context is ContextWrapper) return scanForActivity(
            context.baseContext
        )
        return null
    }

    fun dp2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}
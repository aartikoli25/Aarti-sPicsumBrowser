package com.aarti.picsumbrowser.view.utils

import android.content.Context
import android.content.Intent
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aarti.picsumbrowser.view.ui.HomeActivity


object ShowMessage {

    fun showError(context: Context?, message: String?) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Error...")
            .setContentText(message)
            .show()
    }

    fun showInfo(context: Context?, message: String?) {
        SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("Message")
            .setContentText(message)
            .show()
    }

    fun showSuccess(context: Context?, message: String?) {
        SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(message)
            .setConfirmClickListener {
                context!!.startActivity(
                    Intent(
                        context,
                        HomeActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )

            }
            .show()
    }

    fun showLoading(context: Context?): SweetAlertDialog {
        val loader: SweetAlertDialog
        loader = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        loader.setCancelable(true)
        loader.titleText = "Please wait.."
        return loader
    }
}
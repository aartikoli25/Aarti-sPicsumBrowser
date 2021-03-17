package com.aarti.picsumbrowser.view.ui

import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aarti.picsumbrowser.R
import com.aarti.picsumbrowser.network.NetWorkConection
import com.aarti.picsumbrowser.presenter.BaseViewPresenter.BaseView
import com.aarti.picsumbrowser.view.utils.ColorUtils
import com.aarti.picsumbrowser.view.utils.Constants
import com.aarti.picsumbrowser.view.utils.DrawableUtils
import com.aarti.picsumbrowser.view.utils.ShowMessage

open class BaseActivity : AppCompatActivity(), BaseView {
    var sd: SweetAlertDialog? = null
    protected var shouldRegisterBroadcast = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        sd = ShowMessage.showLoading(this@BaseActivity)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setStatusBarColor() {
        window.statusBarColor = resources.getColor(R.color.primaryDarkColor)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

    protected fun setToolbarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    protected fun setUpEnabled(enable: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(enable)
    }

    protected fun setUpArrow() {
       supportActionBar!!.setHomeAsUpIndicator(
            DrawableUtils.getDrawable(
                this,
                R.drawable.ic_launcher
            )
        )
    }

    protected fun setUpArrow(color: Int) {
        supportActionBar!!.setHomeAsUpIndicator(
            DrawableUtils.getDrawable(
                this,
                R.drawable.ic_launcher
            )
        )
    }

    fun toast(message: String) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_LONG).show()
    }

    protected fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInputFromInputMethod(view.windowToken, 0)
        }
    }

    protected fun showKeyboard(view: EditText) {
        view.requestFocus()
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
        imm.showSoftInput(view, 0)
        //        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    protected fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun showProgressDialog() {
        showProgressDialog(this, "Loading...", true)
    }

    protected fun showProgressDialog(cancelable: Boolean) {
        showProgressDialog(this, "Loading...", cancelable)
    }

    protected fun showProgressDialog(
        activity: Activity?,
        message: String?,
        cancelable: Boolean,
        indeterminate: Boolean
    ) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setTitle(message)
            if (indeterminate) {
                progressDialog!!.setMessage("")
            }
            progressDialog!!.isIndeterminate = indeterminate
            progressDialog!!.setProgressStyle(if (indeterminate) ProgressDialog.STYLE_SPINNER else ProgressDialog.STYLE_HORIZONTAL)
            progressDialog!!.setCancelable(cancelable)
            progressDialog!!.setOnShowListener {
                val v =
                    progressDialog!!.findViewById<View>(
                        android.R.id.progress
                    ) as ProgressBar
                v.indeterminateDrawable.setColorFilter(
                    ColorUtils.getColor(
                        activity,
                        R.color.primaryColor
                    ), PorterDuff.Mode.MULTIPLY
                )
            }
            progressDialog!!.setOnCancelListener { progressDialog = null }
            progressDialog!!.setOnDismissListener { progressDialog = null }
            progressDialog!!.show()
        }
    }

    override fun showProgressbar() {
        sd!!.show()
    }

    override fun hideProgressbar() {
        sd!!.hide()
    }


    override fun checkInternetConnection(): Boolean {
        return NetWorkConection.isNEtworkConnected(this@BaseActivity)
    }

    override fun onError(messgae: String) {
        toast(messgae)
    }

    companion object {
        private var progressDialog: ProgressDialog? = null
        fun showProgressDialog(
            activity: Activity?,
            message: String?,
            cancelable: Boolean
        ) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog(activity)
                progressDialog!!.setMessage(message)
                progressDialog!!.isIndeterminate = true
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(cancelable)
                progressDialog!!.setOnShowListener {
                    val v =
                        progressDialog!!.findViewById<View>(
                            android.R.id.progress
                        ) as ProgressBar
                    v.indeterminateDrawable.setColorFilter(
                        ColorUtils.getColor(
                            activity,
                            R.color.primaryColor
                        ), PorterDuff.Mode.MULTIPLY
                    )
                }
                progressDialog!!.setOnCancelListener { progressDialog = null }
                progressDialog!!.setOnDismissListener { progressDialog = null }
                progressDialog!!.show()
            }
        }

        fun updateProgressDialog(progress: Int, total: Int) {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.isIndeterminate = false
                progressDialog!!.max = total
                progressDialog!!.progress = progress
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            }
        }

        fun updateProgressDialog(
            activity: Activity,
            message: String?,
            progress: Int,
            total: Int
        ) {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.setTitle(message)
                activity.runOnUiThread { progressDialog!!.setMessage(message) }
                progressDialog!!.isIndeterminate = false
                progressDialog!!.max = total
                progressDialog!!.progress = progress
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            }
        }

        fun hideProgressDialog() {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }

        if (sd != null && sd!!.isShowing) {
            sd!!.dismiss()
        }
    }

    fun redirectToLoginPage() {

        //Clear all stored data

       /* startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
        finish()*/
    }

}
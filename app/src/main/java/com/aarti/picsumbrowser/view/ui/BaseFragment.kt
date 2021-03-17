package com.aarti.picsumbrowser.view.ui

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
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aarti.picsumbrowser.network.NetWorkConection
import com.aarti.picsumbrowser.R
import com.aarti.picsumbrowser.presenter.BaseViewPresenter
import com.aarti.picsumbrowser.view.utils.*
import com.aarti.picsumbrowser.view.utils.Utils.isAboveMinSDK
import java.util.*

open class BaseFragment : Fragment(), BaseViewPresenter.BaseView {

    var sd: SweetAlertDialog? = null
    protected var fragmentTitle: String? = null
        protected set(fragmentTitle) {
            field = fragmentTitle ?: ""
        }
    private var progressDialog: ProgressDialog? = null
    var baseFragmentInspectionId = 0
    protected var fragmentListener: FragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sd = ShowMessage.showLoading(context)
    }

    protected fun showProgressDialog(cancelable: Boolean) {
        showProgressDialog("Loading...", cancelable)
    }

    protected fun showProgressDialog(
        message: String? = "Loading...",
        cancelable: Boolean = true,
        indeterminate: Boolean = true
    ) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage(message)
            progressDialog!!.isIndeterminate = indeterminate
            progressDialog!!.setProgressStyle(if (indeterminate) ProgressDialog.STYLE_SPINNER else ProgressDialog.STYLE_HORIZONTAL)
            progressDialog!!.setCancelable(cancelable)
            progressDialog!!.setOnShowListener {
                val v =
                    progressDialog!!.findViewById<View>(android.R.id.progress) as ProgressBar
                v.indeterminateDrawable.setColorFilter(
                    ColorUtils.getColor(
                        context,
                        R.color.primaryColor
                    ), PorterDuff.Mode.MULTIPLY
                )
            }
            progressDialog!!.setOnCancelListener { progressDialog = null }
            progressDialog!!.setOnDismissListener { progressDialog = null }
            progressDialog!!.show()
        }
    }

    protected fun updateProgressDialog(progress: Int, total: Int) {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.isIndeterminate = false
            progressDialog!!.max = total
            progressDialog!!.progress = progress
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        }
    }

    protected fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    protected fun showKeyboard(view: EditText) {
        view.requestFocus()
        val imm =
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    protected fun hideKeyboard(view: EditText?) {
        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun startMyActivity(cls: Class<*>?) {
        val intent = Intent(context, cls)
        startActivity(intent)
    }

    protected fun startMyActivity(
        cls: Class<*>?,
        extras: HashMap<String?, Any?>?
    ) {
        val intent = Intent(context, cls)
        AppUtils.parseIntentExtras(intent, extras)
        startActivity(intent)
    }

    protected fun startMyActivityWithTransition(
        cls: Class<*>?,
        vararg sharedElements: Pair<View?, String?>?
    ) {
        val intent = Intent(context, cls)
        if (isAboveMinSDK(Build.VERSION_CODES.LOLLIPOP)) {
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, *sharedElements)
            //            ((Activity) context).getWindow().setExitTransition(null);
            ActivityCompat.startActivity(context!!, intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    protected fun startMyActivityWithTransition(
        cls: Class<*>?,
        extras: HashMap<String?, Any?>?,
        vararg sharedElements: Pair<View?, String?>?
    ) {
        val intent = Intent(context, cls)
        AppUtils.parseIntentExtras(intent, extras)
        if (!isAboveMinSDK(Build.VERSION_CODES.LOLLIPOP)) {
            startActivity(intent)
        } else {
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, *sharedElements)
            //            ((Activity) context).getWindow().setExitTransition(null);
            ActivityCompat.startActivity(context!!, intent, options.toBundle())
        }
    }


    override fun showProgressbar() {
        sd!!.show()
    }

    override fun hideProgressbar() {
        sd!!.hide()
    }

    override fun onPause() {
        super.onPause()
        sd!!.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sd!!.dismiss()
    }


    override fun checkInternetConnection(): Boolean {
        return NetWorkConection.isNEtworkConnected(context!!)
    }

    override fun onError(messgae: String) {
        toast(messgae)
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun redirectToLoginPage() {

        //Clear all stored data

        /*startActivity(Intent(context, LoginActivity::class.java))
        activity!!.finish()*/
    }
}
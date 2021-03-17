package com.aarti.picsumbrowser.presenter.home

import android.content.Context
import android.util.Log
import com.aarti.picsumbrowser.R
import com.aarti.picsumbrowser.network.ApiClient
import com.aarti.picsumbrowser.view.ui.HomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class HomePresenter
    : HomeViewPresenter.HomeMethodPresenter {
    var context: Context? = null
    var disposable: Disposable? = null
    var activity: HomeActivity? = null
    var parentView: HomeViewPresenter.HomeView? = null

    constructor(
        context: Context?,
        activity: HomeActivity?,
        parentView: HomeViewPresenter.HomeView?
    ) {
        this.context = context
        this.activity = activity
        this.parentView = parentView
    }

    override fun requestPhotoList() {
        if (activity!!.checkInternetConnection()) {
            activity!!.showProgressbar()
            disposable = ApiClient.instance.getRequestPhotoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    activity!!.hideProgressbar()
                    when (response.code()) {
                        200, 201, 202 -> {
                            parentView!!.onSuccess(response)
                        }

                        401 -> {
                            activity!!.redirectToLoginPage()
                        }

                        402, 422 -> {

                            if (!response.isSuccessful) {
                                response.errorBody()
                                var error: String? = ""
                                try {
                                    val ereader = BufferedReader(
                                        InputStreamReader(
                                            response.errorBody()!!.byteStream()
                                        )
                                    )
                                    var eline: String? = null
                                    while (ereader.readLine().also({ eline = it }) != null) {
                                        error += eline + ""
                                    }
                                    ereader.close()
                                } catch (e: Exception) {
                                    error += e.message
                                }
                                Log.e("Error", error.toString())
                                try {
                                    val reader = JSONObject(error)
                                    val message = reader.getString("error")
                                    activity?.onError(message)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        500 -> {
                            activity!!.onError("Internal Server Error ")
                        }
                        501 -> {
                            activity!!.onError("Internal Server Error ")
                        }

                    }
                }, { error ->
                    activity!!.hideProgressbar()
                    activity!!.toast("Failed to connect")

                })
        } else {
            activity!!.toast(context!!.getString(R.string.check_internet_connection))
        }
    }

    override fun stop() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}
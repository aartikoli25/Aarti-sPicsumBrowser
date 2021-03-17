package com.aarti.picsumbrowser.presenter

interface BaseViewPresenter {

    interface BaseView {
        fun showProgressbar()
        fun hideProgressbar()
        fun checkInternetConnection(): Boolean
        fun onError(messgae: String)
    }
}
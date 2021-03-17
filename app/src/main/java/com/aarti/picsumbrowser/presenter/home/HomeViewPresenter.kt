package com.aarti.picsumbrowser.presenter.home

import com.aarti.picsumbrowser.model.PhotoListResponse
import retrofit2.Response

interface HomeViewPresenter {

    interface HomeView {
        fun onSuccess(responseModel: Response<List<PhotoListResponse>>)
    }

    interface HomeMethodPresenter {
        fun requestPhotoList()
        fun stop()
    }
}
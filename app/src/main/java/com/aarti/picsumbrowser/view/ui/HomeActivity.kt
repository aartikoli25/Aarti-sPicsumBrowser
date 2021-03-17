package com.aarti.picsumbrowser.view.ui

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.aarti.picsumbrowser.R
import com.aarti.picsumbrowser.model.PhotoListResponse
import com.aarti.picsumbrowser.presenter.home.HomePresenter
import com.aarti.picsumbrowser.presenter.home.HomeViewPresenter
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Response

class HomeActivity : BaseActivity(), HomeViewPresenter.HomeView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var presenter = HomePresenter(this, this, this)
        presenter.requestPhotoList()

    }

    override fun onSuccess(responseModel: Response<List<PhotoListResponse>>) {
        if (responseModel.isSuccessful) {
                var adapter = HomeListAdapter(this, responseModel.body()!!)
                rv_photo!!.adapter = adapter
                rv_photo.layoutManager = GridLayoutManager(this, 2)
        }
    }
}
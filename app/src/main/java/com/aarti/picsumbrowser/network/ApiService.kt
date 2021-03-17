package com.aarti.picsumbrowser.network

import com.aarti.picsumbrowser.model.PhotoListResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("list")
    fun getPhotoList(
    ): Observable<Response<List<PhotoListResponse>>>

}
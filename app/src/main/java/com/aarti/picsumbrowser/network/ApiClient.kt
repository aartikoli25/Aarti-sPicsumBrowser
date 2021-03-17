package com.aarti.picsumbrowser.network


import com.aarti.picsumbrowser.model.PhotoListResponse
import com.aarti.picsumbrowser.view.utils.Constants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val myAppService: ApiService

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()


        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(HttpClientService.getUnsafeOkHttpClient())
            .build()


        myAppService = retrofit.create(ApiService::class.java)
    }

    companion object {
        private var apiClient: ApiClient? = null

        /**
         * Gets my app client.
         *
         * @return the my app client
         */
        val instance: ApiClient
            get() {
                if (apiClient == null) {
                    apiClient = ApiClient()
                }
                return apiClient as ApiClient
            }
    }

    fun getRequestPhotoList(): Observable<Response<List<PhotoListResponse>>> {
        return myAppService.getPhotoList()
    }

}

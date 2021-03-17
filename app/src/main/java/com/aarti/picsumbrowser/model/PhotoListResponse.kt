package com.aarti.picsumbrowser.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PhotoListResponse {

    @SerializedName("format")
    @Expose
    var format: String? = null

    @SerializedName("width")
    @Expose
    var width = 0

    @SerializedName("height")
    @Expose
    var height = 0

    @SerializedName("filename")
    @Expose
    var filename: String? = null

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("author_url")
    @Expose
    var authorUrl: String? = null

    @SerializedName("post_url")
    @Expose
    var postUrl: String? = null
}
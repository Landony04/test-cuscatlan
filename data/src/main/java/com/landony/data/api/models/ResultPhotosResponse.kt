package com.landony.data.api.models

import com.google.gson.annotations.SerializedName

data class ResultPhotosResponse(
    @SerializedName("albumId")
    val albumId: Long,
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)
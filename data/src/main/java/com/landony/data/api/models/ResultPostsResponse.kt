package com.landony.data.api.models

import com.google.gson.annotations.SerializedName

data class ResultPostsResponse(
    val postsItems: ArrayList<PostsItemResponse>
)

data class PostsItemResponse(
    @SerializedName("userId")
    val userID: Long,
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)

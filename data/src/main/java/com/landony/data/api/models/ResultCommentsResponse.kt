package com.landony.data.api.models

import com.google.gson.annotations.SerializedName

data class ResultCommentsResponse(
    @SerializedName("postId")
    val userID: Long,
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("body")
    val body: String
)
package com.landony.domain.entities

data class PostsResultUI(
    val postsItems: ArrayList<PostsUI>
)

data class PostsUI(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)

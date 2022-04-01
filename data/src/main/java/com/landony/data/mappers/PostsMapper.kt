package com.landony.data.mappers

import com.landony.data.api.models.PostsItemResponse
import com.landony.data.api.models.ResultPostsResponse
import com.landony.domain.entities.PostsResultUI
import com.landony.domain.entities.PostsUI

fun ResultPostsResponse.toResultResponseUI(): PostsResultUI {
    return PostsResultUI(
        postsItems = ArrayList(this.postsItems.map { it.toPostsUI() })
    )
}

fun PostsItemResponse.toPostsUI(): PostsUI {
    return PostsUI(
        userId = this.userID,
        id = this.id,
        title = this.title,
        body = this.body
    )
}
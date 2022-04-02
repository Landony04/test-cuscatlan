package com.landony.data.mappers

import com.landony.data.api.models.PostsItemResponse
import com.landony.domain.entities.PostsUI

fun PostsItemResponse.toPostsUI(): PostsUI {
    return PostsUI(
        userId = this.userID,
        id = this.id,
        title = this.title,
        body = this.body
    )
}
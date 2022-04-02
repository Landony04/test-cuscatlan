package com.landony.data.mappers

import com.landony.data.api.models.ResultCommentsResponse
import com.landony.domain.entities.CommentsByPostResultUI

fun ResultCommentsResponse.toCommentsUI(): CommentsByPostResultUI {
    return CommentsByPostResultUI(
        userID = this.userID,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body
    )
}
package com.landony.data.mappers

import com.landony.data.api.models.ResultPhotosResponse
import com.landony.domain.entities.PhotosByPostUI

fun ResultPhotosResponse.toPhotoUI(): PhotosByPostUI {
    return PhotosByPostUI(
        albumId = this.albumId,
        id = this.id,
        title = this.title,
        url = this.url,
        thumbnailUrl = this.thumbnailUrl
    )
}
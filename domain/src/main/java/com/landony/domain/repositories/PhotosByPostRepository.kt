package com.landony.domain.repositories

import com.landony.domain.common.Result
import com.landony.domain.entities.PhotosByPostUI
import kotlinx.coroutines.flow.Flow

interface PhotosByPostRepository {
    /**
     * This fun getPhotosByPost is use for inject data source
     */
    fun getPhotosByPost(idPost: String): Flow<Result<ArrayList<PhotosByPostUI>>>
}
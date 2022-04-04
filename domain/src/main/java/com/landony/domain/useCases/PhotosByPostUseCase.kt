package com.landony.domain.useCases

import com.landony.domain.common.Result
import com.landony.domain.entities.PhotosByPostUI
import kotlinx.coroutines.flow.Flow

interface PhotosByPostUseCase {
    /**
     * This interface is invoke for invokePhotosByPost
     */
    fun invokePhotosByPost(idPost: String): Flow<Result<ArrayList<PhotosByPostUI>>>
}
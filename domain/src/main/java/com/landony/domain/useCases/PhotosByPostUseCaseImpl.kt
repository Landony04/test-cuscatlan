package com.landony.domain.useCases

import com.landony.domain.common.Result
import com.landony.domain.entities.PhotosByPostUI
import com.landony.domain.repositories.PhotosByPostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosByPostUseCaseImpl @Inject constructor(
    private val photosByPostRepository: PhotosByPostRepository
) : PhotosByPostUseCase {
    override fun invokePhotosByPost(idPost: String): Flow<Result<ArrayList<PhotosByPostUI>>> =
        photosByPostRepository.getPhotosByPost(idPost)
}
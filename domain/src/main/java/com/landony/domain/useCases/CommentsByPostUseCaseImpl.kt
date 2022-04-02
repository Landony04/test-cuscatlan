package com.landony.domain.useCases

import com.landony.domain.common.Result
import com.landony.domain.entities.CommentsByPostResultUI
import com.landony.domain.repositories.CommentsByPostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentsByPostUseCaseImpl @Inject constructor(
    private val commentsByPostRepository: CommentsByPostRepository
) : CommentsByPostUseCase {
    override fun invokeCommentsByPost(idPost: String): Flow<Result<ArrayList<CommentsByPostResultUI>>> =
        commentsByPostRepository.getCommentsByPost(idPost)
}
package com.landony.domain.useCases

import com.landony.domain.common.Result
import com.landony.domain.entities.CommentsByPostResultUI
import kotlinx.coroutines.flow.Flow

interface CommentsByPostUseCase {
    /**
     * This interface is invoke for invokeCommentsByPost
     */
    fun invokeCommentsByPost(idPost: String): Flow<Result<ArrayList<CommentsByPostResultUI>>>
}
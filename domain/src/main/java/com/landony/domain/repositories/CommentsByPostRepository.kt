package com.landony.domain.repositories

import com.landony.domain.common.Result
import com.landony.domain.entities.CommentsByPostResultUI
import kotlinx.coroutines.flow.Flow

interface CommentsByPostRepository {
    /**
     * This fun getCommentsByPost is use for inject data source
     */
    fun getCommentsByPost(idPost: String): Flow<Result<ArrayList<CommentsByPostResultUI>>>
}
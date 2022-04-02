package com.landony.domain.useCases

import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
import kotlinx.coroutines.flow.Flow

interface PostsUseCase {
    /**
     * This interface is invoke for invokePosts
     */
    fun invokePosts(): Flow<Result<ArrayList<PostsUI>>>
}
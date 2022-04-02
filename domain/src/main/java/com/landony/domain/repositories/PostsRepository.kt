package com.landony.domain.repositories

import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    /**
     * This fun getAllPosts is use for inject data source
     */
    fun getAllPosts(): Flow<Result<ArrayList<PostsUI>>>
}
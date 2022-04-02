package com.landony.domain.useCases

import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
import com.landony.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsUseCaseImpl @Inject constructor(private val postsRepository: PostsRepository) :
    PostsUseCase {
    override fun invokePosts(): Flow<Result<ArrayList<PostsUI>>> = postsRepository.getAllPosts()
}
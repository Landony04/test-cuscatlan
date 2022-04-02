package com.landony.data.repositories

import com.landony.data.api.CuscatlanApiService
import com.landony.data.di.CuscatlanServices
import com.landony.data.mappers.toPostsUI
import com.landony.data.util.BaseRepository
import com.landony.data.util.DispatcherProvider
import com.landony.data.util.bodyOrException
import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
import com.landony.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    @CuscatlanServices
    private val cuscatlanApiService: CuscatlanApiService,
    private val dispatcherProvider: DispatcherProvider
) : PostsRepository, BaseRepository() {
    override fun getAllPosts(): Flow<Result<ArrayList<PostsUI>>> {
        return flow<Result<ArrayList<PostsUI>>> {
            val response = cuscatlanApiService.getGitHubUsers().bodyOrException()

            val allValues: ArrayList<PostsUI> = arrayListOf()
            response.forEach {
                allValues.add(it.toPostsUI())
            }

            emit(Result.Success(allValues))
        }.onStart {
            emit(Result.Loading(null))
        }.catch {
            emit(handlerErrorException(throwable = it))
        }.flowOn(dispatcherProvider.io())
    }
}
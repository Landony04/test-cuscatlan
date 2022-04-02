package com.landony.data.repositories

import com.landony.data.api.CuscatlanApiService
import com.landony.data.di.CuscatlanServices
import com.landony.data.mappers.toCommentsUI
import com.landony.data.util.BaseRepository
import com.landony.data.util.DispatcherProvider
import com.landony.data.util.bodyOrException
import com.landony.domain.common.Result
import com.landony.domain.entities.CommentsByPostResultUI
import com.landony.domain.repositories.CommentsByPostRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CommentsByPostRepositoryImpl @Inject constructor(
    @CuscatlanServices
    private val cuscatlanApiService: CuscatlanApiService,
    private val dispatcherProvider: DispatcherProvider
) : CommentsByPostRepository, BaseRepository() {
    override fun getCommentsByPost(idPost: String): Flow<Result<ArrayList<CommentsByPostResultUI>>> {
        return flow<Result<ArrayList<CommentsByPostResultUI>>> {
            val response = cuscatlanApiService.getCommentsByPost(idPost).bodyOrException()

            val allValues: ArrayList<CommentsByPostResultUI> = arrayListOf()
            response.forEach {
                allValues.add(it.toCommentsUI())
            }

            emit(Result.Success(allValues))
        }.onStart {
            emit(Result.Loading(null))
        }.catch {
            emit(handlerErrorException(throwable = it))
        }.flowOn(dispatcherProvider.io())
    }
}
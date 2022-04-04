package com.landony.data.repositories

import com.landony.data.api.CuscatlanApiService
import com.landony.data.di.CuscatlanServices
import com.landony.data.mappers.toPhotoUI
import com.landony.data.util.BaseRepository
import com.landony.data.util.DispatcherProvider
import com.landony.data.util.bodyOrException
import com.landony.domain.common.Result
import com.landony.domain.entities.PhotosByPostUI
import com.landony.domain.repositories.PhotosByPostRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PhotosByPostRepositoryImpl @Inject constructor(
    @CuscatlanServices
    private val cuscatlanApiService: CuscatlanApiService,
    private val dispatcherProvider: DispatcherProvider
) : PhotosByPostRepository, BaseRepository() {
    override fun getPhotosByPost(idPost: String): Flow<Result<ArrayList<PhotosByPostUI>>> {
        return flow<Result<ArrayList<PhotosByPostUI>>> {
            val response = cuscatlanApiService.getPhotosByPost(idPost).bodyOrException()

            val allValues: ArrayList<PhotosByPostUI> = arrayListOf()
            response.forEach {
                allValues.add(it.toPhotoUI())
            }

            emit(Result.Success(allValues))
        }.onStart {
            emit(Result.Loading(null))
        }.catch {
            emit(handlerErrorException(throwable = it))
        }.flowOn(dispatcherProvider.io())
    }
}
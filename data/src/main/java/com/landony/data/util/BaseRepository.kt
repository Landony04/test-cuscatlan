package com.landony.data.util

import com.google.gson.GsonBuilder
import com.landony.data.api.models.ResultPostsResponse
import com.landony.data.mappers.toResultResponseUI
import com.landony.domain.common.Result
import com.landony.domain.entities.PostsResultUI
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

open class BaseRepository {

    private fun parseError(
        httpException: HttpException
    ): PostsResultUI? {
        return try {
            val gson = GsonBuilder().create()
            val messageError = httpException.response()?.errorBody()?.string()
            val errorResponse =
                gson.fromJson(messageError, ResultPostsResponse::class.java)
            Timber.e("ErrorResponse: $errorResponse")
            errorResponse.toResultResponseUI()
        } catch (e: Exception) {
            Timber.e("ParseError: $e")
            null
        }
    }

    fun <T> handlerErrorException(
        throwable: Throwable
    ): Result<T> {
        return when (throwable) {
            is HttpException -> {
                val error = parseError(
                    httpException = throwable
                )
                if (error != null) {
                    Result.Failure(error = error, httpCode = throwable.code())
                } else {
                    Result.Failure(error = null, httpCode = throwable.code())
                }
            }
            is IOException -> {
                Result.Failure(error = null, httpCode = IO_EXCEPTION_CODE)
            }
            else -> {
                Result.Failure(error = null, httpCode = UNEXPECTED_ERROR_CODE)
            }
        }
    }

    companion object {
        const val IO_EXCEPTION_CODE = -101
        const val UNEXPECTED_ERROR_CODE = -102
    }
}
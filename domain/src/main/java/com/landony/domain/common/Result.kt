package com.landony.domain.common

import com.landony.domain.entities.PostsResultUI

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Loading<T>(val data: T?) : Result<T>()
    data class Failure<T>(val error: PostsResultUI?, val httpCode: Int) : Result<T>()
}
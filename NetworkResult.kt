package com.dashfleet.lola.data.model

import okhttp3.ResponseBody

sealed class NetworkResult<T: Any> {

    class ApiSuccess<T: Any>(val data: T): NetworkResult<T>()

    class ApiError<T: Any>(
        val code: Int,
        val message: String
    ): NetworkResult<T>()

    class ApiException<T: Any>(val e: Throwable): NetworkResult<T>()
}

package com.dashfleet.lola.domain.utils

import android.util.Log
import com.dashfleet.lola.data.model.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

object HandleApi {

    suspend fun <T : Any, U : Any> handlePostApi(
        body: U,
        execute: suspend (U) -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute(body)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                NetworkResult.ApiSuccess(responseBody)
            } else {
                NetworkResult.ApiError(
                    code = response.code(),
                    message = response.message()
                )
            }
        } catch (e: Exception) {
            Log.e("handlePostAPIException", e.stackTraceToString())
            NetworkResult.ApiException(e)
        } catch (e: HttpException) {
            NetworkResult.ApiException(e)
        }
    }

    suspend fun <T : Any> handleGetApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                NetworkResult.ApiSuccess(responseBody)
            } else {
                NetworkResult.ApiError(
                    code = response.code(),
                    message = response.message()
                )
            }
        } catch (e: Exception) {
            Log.e("handleGetAPIException", e.stackTraceToString())
            NetworkResult.ApiException(e)
        } catch (e: HttpException) {
            NetworkResult.ApiException(e)
        }
    }
}
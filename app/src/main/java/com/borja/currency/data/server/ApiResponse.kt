package com.borja.currency.data.server

import retrofit2.Response

sealed class ApiResponse<out T> {
    companion object {
        fun <T> error(error: Throwable): ApiResponse<T> {
            return ApiErrorResponse(error, error.message ?: "unknow error")
        }

        fun <T> success(response: Response<T>?): ApiResponse<T> = response?.let {
            if (response.isSuccessful) {
                val body = response.body()

                if (body == null || response.code() == 204) {
                    ApiEmptyResponse
                } else {
                    ApiSuccessResponse<T>(body)
                }
            } else {
                ApiErrorResponse(null, "unknow error")
            }
        } ?: ApiErrorResponse(null, "unknow error")

        fun <T> empty(): ApiResponse<T> = ApiEmptyResponse
    }
}

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
data class ApiErrorResponse(val error: Throwable? = null, val errorMessage: String?) :
    ApiResponse<Nothing>()

object ApiEmptyResponse : ApiResponse<Nothing>()
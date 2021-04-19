package com.borja.currency.data.server

import com.borja.currency.domain.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("latest")
    suspend fun getRates(): ApiResponse<BaseResponse>

    @GET("latest")
    suspend fun getRatesByCurrency(@Query ("base") currency: String): ApiResponse<BaseResponse>

}
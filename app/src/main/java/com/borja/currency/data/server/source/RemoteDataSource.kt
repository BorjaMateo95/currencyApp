package com.borja.currency.data.server.source

import com.borja.currency.domain.BaseResponse
import com.borja.currency.domain.Either
import com.borja.currency.domain.Failure

interface RemoteDataSource {
    suspend fun getRates(): Either<Failure, BaseResponse>
    suspend fun getRatesByCurrency(currency: String): Either<Failure, BaseResponse>
}
package com.borja.currency.data.repository

import com.borja.currency.data.room.CurrencyRoomDataSource
import com.borja.currency.data.server.source.RemoteDataSource
import com.borja.currency.domain.BaseResponse
import com.borja.currency.domain.Either
import com.borja.currency.domain.Failure
import javax.inject.Inject

class RatesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val currencyRoomDataSource: CurrencyRoomDataSource
) {

    suspend fun getRates() : Either<Failure, BaseResponse> {
        return remoteDataSource.getRates()
    }

    suspend fun getRatesByCurrency(currency: String) : Either<Failure, BaseResponse> =
        remoteDataSource.getRatesByCurrency(currency)

    suspend fun insertBaseResponse(baseResponse: BaseResponse) {
        currencyRoomDataSource.saveBaseResponse(baseResponse)
    }

    suspend fun getBaseResponse() : Either<Failure, BaseResponse> =  currencyRoomDataSource.getBaseResponse()

}
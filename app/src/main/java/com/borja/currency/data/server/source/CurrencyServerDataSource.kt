package com.borja.currency.data.server.source

import com.borja.currency.data.server.ApiErrorResponse
import com.borja.currency.data.server.ApiSuccessResponse
import com.borja.currency.data.server.CurrencyService
import com.borja.currency.domain.BaseResponse
import com.borja.currency.domain.Either
import com.borja.currency.domain.Failure
import javax.inject.Inject

class CurrencyServerDataSource  @Inject constructor(
    private val currencyService: CurrencyService
) : RemoteDataSource {

    override suspend fun getRates(): Either<Failure, BaseResponse> {
        return when (val response = currencyService.getRates()) {
            is ApiSuccessResponse -> Either.Right(response.body)
            is ApiErrorResponse -> Either.Left(Failure.ApiFailure.GenericError())
            else -> Either.Left(Failure.ApiFailure.GenericError())
        }
    }

    override suspend fun getRatesByCurrency(currency: String): Either<Failure, BaseResponse> {
        return when (val response = currencyService.getRatesByCurrency(currency)) {
            is ApiSuccessResponse -> Either.Right(response.body)
            is ApiErrorResponse -> Either.Left(Failure.ApiFailure.GenericError())
            else -> Either.Left(Failure.ApiFailure.GenericError())
        }
    }


}
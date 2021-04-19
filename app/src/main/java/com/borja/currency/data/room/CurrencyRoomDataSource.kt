package com.borja.currency.data.room

import com.borja.currency.domain.BaseResponse
import com.borja.currency.domain.Either
import com.borja.currency.domain.Failure
import javax.inject.Inject

class CurrencyRoomDataSource @Inject constructor(
    private val roomDataSource: RatesDao
): RoomDataSource {

    override suspend fun saveBaseResponse(baseResponse: BaseResponse) {
        roomDataSource.insertResponse(baseResponse)
    }

    override suspend fun getBaseResponse(): Either<Failure, BaseResponse> {
        val response = roomDataSource.getResponse()
        return if (response != null) {
            Either.Right(roomDataSource.getResponse())
        } else {
            Either.Left(Failure.ApiFailure.GenericError())
        }

    }


}
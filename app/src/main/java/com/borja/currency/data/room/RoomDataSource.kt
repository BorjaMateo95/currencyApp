package com.borja.currency.data.room

import com.borja.currency.domain.BaseResponse
import com.borja.currency.domain.Either
import com.borja.currency.domain.Failure

interface RoomDataSource {
    suspend fun saveBaseResponse(baseResponse: BaseResponse)
    suspend fun getBaseResponse() : Either<Failure, BaseResponse>
}
package com.borja.currency.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borja.currency.domain.BaseResponse

@Dao
interface RatesDao {
    @Query("SELECT * FROM baseresponse")
    suspend fun getResponse() : BaseResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(baseResponse: BaseResponse)
}
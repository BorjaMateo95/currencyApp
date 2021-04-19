package com.borja.currency.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.borja.currency.domain.BaseResponse

@Database(entities = [BaseResponse::class], version = 3)
@TypeConverters(MapTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}
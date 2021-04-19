package com.borja.currency.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.borja.currency.data.room.MapTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class BaseResponse(

    @PrimaryKey
    val id: Long = 1L,

    @Expose
    @SerializedName("base")
    val base : String,

    @Expose
    @SerializedName("date")
    val date : String,

    @Expose
    @SerializedName("rates")
    @TypeConverters(MapTypeConverter::class)
    val rates : HashMap<String, Double>

)


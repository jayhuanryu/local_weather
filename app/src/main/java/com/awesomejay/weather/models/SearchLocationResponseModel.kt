package com.awesomejay.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awesomejay.weather.utils.Const

@Entity(tableName = Const.TABLE_NAME_CITY_NAME)
data class SearchLocationResponseModel(
    @PrimaryKey val title : String,
    val location_type : String,
    val woeid : Int,
    val latt_long : String
)

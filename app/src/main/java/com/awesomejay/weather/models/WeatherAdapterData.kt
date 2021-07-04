package com.awesomejay.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awesomejay.weather.utils.Const

@Entity(tableName =  Const.TABLE_NAME_CONSOLIDATE_WEATHER)
data class WeatherAdapterData(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val viewType : Int?,
    val cityName : String?,
    val todayWeather : DisplayInfoTableModel?,
    val tomorrowWeather : DisplayInfoTableModel?
) {
    constructor(viewType: Int?, cityName: String, todayWeather: DisplayInfoTableModel, tomorrowWeather: DisplayInfoTableModel) : this(
        id = 0,
        viewType = viewType,
        cityName =  cityName,
        todayWeather = todayWeather,
        tomorrowWeather = tomorrowWeather
    )
}

package com.awesomejay.weather.view_models

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesomejay.weather.models.DisplayInfoTableModel
import com.awesomejay.weather.models.SearchLocationResponseModel
import com.awesomejay.weather.models.WeatherAdapterData


@Dao
interface MainDao {


    @Query("SELECT * FROM consolidate_weather_table ORDER BY id ASC")
    fun getAllWeatherList() : LiveData<List<WeatherAdapterData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item : WeatherAdapterData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeatherInfo(list : ArrayList<WeatherAdapterData>)

    @Query("DELETE FROM consolidate_weather_table")
    suspend fun deleteAllWeatherInfo()


}
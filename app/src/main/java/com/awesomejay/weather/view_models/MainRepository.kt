package com.awesomejay.weather.view_models

import androidx.annotation.WorkerThread
import com.awesomejay.weather.api_utils.WeatherApiInterface
import com.awesomejay.weather.models.SearchLocationResponseModel
import com.awesomejay.weather.models.WeatherAdapterData

class MainRepository (private val mainDao: MainDao){

    private val TAG = javaClass.name
    private val weatherInterface by lazy { WeatherApiInterface.create()}

    val allCityNames = mainDao.getAllCityNames()
    val allCityWeather= mainDao.getAllWeatherList()

    // server interface
    suspend fun getCityNameList(name : String) = weatherInterface.getCityNameByName(name)
    suspend fun getCityWeather(woeid:String) = weatherInterface.getWeatherById(woeid)

    // db delete interface
    suspend fun deleteAllCityNames() = mainDao.deleteAllCityName()
    suspend fun deleteAllWeatherInfo() = mainDao.deleteAllWeatherInfo()




    // db insert interface
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCityName(cityname : SearchLocationResponseModel) = mainDao.insertCityNameSuspended(cityname)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWeatherInfo(displayInfoTableModel: WeatherAdapterData) = mainDao.insert(displayInfoTableModel)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllWeatherInfo(listDisplayInfoTableModel: ArrayList<WeatherAdapterData>) = mainDao.insertAllWeatherInfo(listDisplayInfoTableModel)




}

package com.awesomejay.weather.view_models

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.awesomejay.weather.models.ConsolidatedWeatherModel
import com.awesomejay.weather.models.DisplayInfoTableModel
import com.awesomejay.weather.models.WeatherAdapterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException


class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) : ViewModel() {

    private val TAG = javaClass.name

    val weathers = repository.allCityWeather
    val numberOfCities = MutableLiveData<Int>()
    var tempWeatherInfo : ArrayList<WeatherAdapterData> = arrayListOf()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteAllWeatherInfo()
            }
        }

    }

    fun getAllCityNames() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.getCityNameList("se")
                if (response.isSuccessful) {
                    Log.d(TAG, "[getAllCityNames] + result : $response.body")

                    repository.deleteAllWeatherInfo()
                    tempWeatherInfo.clear()

                    val list = response.body()
                    numberOfCities.postValue(list?.size)
                    list?.map {
                        getCityWeather(it.woeid)
                    }

                }
                else {
                    Log.e(TAG,"[getAllCityNames] Fail")
                    numberOfCities.postValue(-1)
                }
            }
        }


    }

    private suspend fun getCityWeather(woeid : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.getCityWeather(woeid.toString())
                if (response.isSuccessful) {
                    val result = response.body()



                    // create a display body
                    val weatherDataForCities = WeatherAdapterData(
                        1,
                        result!!.title,
                        getDisPlayInfoTableModel(result.title, result.consolidatedWeather[0]),
                        getDisPlayInfoTableModel(result.title, result.consolidatedWeather[1]),
                    )

                    repository.insertWeatherInfo(weatherDataForCities)
                    tempWeatherInfo.add(weatherDataForCities)
                    Log.d(TAG, "[getCityWeather] >> insertCity : ${weatherDataForCities.cityName}")

//                    val dbResult = repository.insertWeatherInfo(weatherDataForCities)
//                    Log.d(TAG, "[getCityWeather] >> dbResult + $dbResult" + weatherDataForCities.cityName)

                }
                else {
                    repository.deleteAllWeatherInfo()
                }
            }
        }
    }


    private fun getDisPlayInfoTableModel(cityName : String, consolidatedWeatherModel: ConsolidatedWeatherModel) : DisplayInfoTableModel{
        return DisplayInfoTableModel(
            cityName,
            consolidatedWeatherModel.weatherStateName,
            consolidatedWeatherModel.weatherStateAbbr,
            consolidatedWeatherModel.applicableDate,
            consolidatedWeatherModel.theTemp,
            consolidatedWeatherModel.humidity
        )
    }



}

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
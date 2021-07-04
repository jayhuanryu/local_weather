package com.awesomejay.weather.api_utils

import com.awesomejay.weather.utils.Const
import com.awesomejay.weather.models.SearchLocationResponseModel
import com.awesomejay.weather.models.WeatherResponseModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiInterface {


    companion object {

        fun create() : WeatherApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Const.API_BASE_URL)
                .build()


            return retrofit.create(WeatherApiInterface::class.java)
        }
    }
    // 1. Location Search
    @GET("location/search/")
    suspend fun getCityNameByName(@Query("query") cityName : String) : Response<List<SearchLocationResponseModel>>


    // 2. Location
    @GET("location/{woeid}")
    suspend fun getWeatherById(@Path("woeid") woeid : String) : Response<WeatherResponseModel>

}
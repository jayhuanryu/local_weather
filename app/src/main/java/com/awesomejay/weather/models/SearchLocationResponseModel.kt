package com.awesomejay.weather.models

data class SearchLocationResponseModel(
    val title : String,
    val location_type : String,
    val woeid : Int,
    val latt_long : String
)

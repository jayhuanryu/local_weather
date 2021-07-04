package com.awesomejay.weather

import android.app.Application
import com.awesomejay.weather.view_models.MainRepository
import com.awesomejay.weather.view_models.MainRoomDatabase

class LocalWeatherApplication : Application() {

    val database by lazy { MainRoomDatabase.getDatabase(this) }
    val repository by lazy { MainRepository(database.mainDao()) }
}
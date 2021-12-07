package com.awesomejay.weather.view_models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awesomejay.weather.models.WeatherAdapterData
import com.awesomejay.weather.utils.Converters


@Database(entities = [WeatherAdapterData::class], version = 1)
@TypeConverters(Converters::class)
abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun mainDao() : MainDao

    companion object {
        @Volatile
        private var INSTANCE : MainRoomDatabase? = null

        fun getDatabase(context : Context) : MainRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDatabase::class.java,
                "database").build()

                INSTANCE = instance
                return instance
            }

        }
    }

    /**
     * If I wanted to update version # of the database, the following would do the job.
     *
     *
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                    "PRIMARY KEY(`id`))")
        }
    }
    **/

}
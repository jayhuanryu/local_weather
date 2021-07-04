package com.awesomejay.weather.utils

import androidx.room.TypeConverter
import com.awesomejay.weather.models.DisplayInfoTableModel

class Converters {

    @TypeConverter
    fun fromDisPlayInfo(item : DisplayInfoTableModel) : String {
        return item.toString()
    }

    @TypeConverter
    fun stringToDispalyInfo(inputString : String) : DisplayInfoTableModel {
        val splitList = inputString.split(",")
        return DisplayInfoTableModel(
            splitList[0],
            splitList[1],
            splitList[2],
            splitList[3],
            splitList[4].toDouble(),
            splitList[5].toLong(),
        )
    }




}
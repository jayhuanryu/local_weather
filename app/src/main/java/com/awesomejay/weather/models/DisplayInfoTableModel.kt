package com.awesomejay.weather.models

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.awesomejay.weather.R
import com.awesomejay.weather.utils.Const
import com.bumptech.glide.Glide

data class DisplayInfoTableModel(
    val cityName: String?,
    val weatherStateName: String?,
    val weatherStateAbbr: String?,
    val applicableDate: String?,
    val theTemp: Double,
    val humidity: Long

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readLong()
    )

    override fun toString(): String {
        return "$cityName,$weatherStateName,$weatherStateAbbr,$applicableDate,$theTemp,$humidity"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cityName)
        parcel.writeString(weatherStateName)
        parcel.writeString(weatherStateAbbr)
        parcel.writeString(applicableDate)
        parcel.writeDouble(theTemp)
        parcel.writeLong(humidity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DisplayInfoTableModel> {
        override fun createFromParcel(parcel: Parcel): DisplayInfoTableModel {
            return DisplayInfoTableModel(parcel)
        }

        override fun newArray(size: Int): Array<DisplayInfoTableModel?> {
            return arrayOfNulls(size)
        }

        @JvmStatic
        @BindingAdapter("app:setImageResource")
        fun setImageResource(imageView: ImageView, category:String?) {
            var url = Const.IMAGE_TEMPLATE_URL
            when (category) {
                "sn" -> { url = java.lang.String.format(url, "sn")}
                "sl" -> {url = java.lang.String.format(url, "sl")}
                "h" -> {url = java.lang.String.format(url, "h")}
                "t" -> {url = java.lang.String.format(url, "t")}
                "hr" -> {url = java.lang.String.format(url, "hr")}
                "lr" -> {url = java.lang.String.format(url, "lr")}
                "s" -> {url = java.lang.String.format(url, "s")}
                "hc" -> {url = java.lang.String.format(url, "hc")}
                "lc" -> {url = java.lang.String.format(url, "lc")}
                else -> {
                    // "c" will fall under here
                    url = java.lang.String.format(url, "c")
                }
            }
            Glide.with(imageView.context).load(url).into(imageView)
        }

        @JvmStatic
        @BindingAdapter("app:setTempText")
        fun setTempText(textView: TextView, temp:Double?) {
            if (temp != null) {

                if (temp >= 0) {
                    textView.setTextColor(Color.parseColor("#722f37"))
                }
                else {
                    textView.setTextColor(Color.parseColor("#006994"))
                }
                textView.text = temp.toString().substringBefore(".") + "°C"
            }
            else {
                textView.text = "Unknown"
            }
        }

        @JvmStatic
        @BindingAdapter("app:setHumidityText")
        fun setTempText(textView: TextView, humidity:Long?) {
            if (humidity != null) {
                textView.text = "$humidity%"
            }
            else {
                textView.text = "Unknown"
            }
        }
    }




}

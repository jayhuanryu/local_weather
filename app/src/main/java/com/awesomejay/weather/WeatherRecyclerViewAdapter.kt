package com.awesomejay.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesomejay.weather.databinding.ItemRvHeaderBinding
import com.awesomejay.weather.databinding.ItemRvWeatherBinding
import com.awesomejay.weather.models.DisplayInfoTableModel
import com.awesomejay.weather.models.WeatherAdapterData
import kotlinx.coroutines.flow.merge

class WeatherRecyclerViewAdapter(private val activity: MainActivity): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val TAG = javaClass.simpleName

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_WEATHER = 1
    }


    // container for the adapter
    private var weatherDisplayInfoList : List<WeatherAdapterData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            0 -> {
                val binding : ItemRvHeaderBinding = ItemRvHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
            else -> {
                val binding : ItemRvWeatherBinding = ItemRvWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                WeatherViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (weatherDisplayInfoList[position].viewType) {
            VIEW_TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(position)
            }
            VIEW_TYPE_WEATHER -> {
                (holder as WeatherViewHolder).bind(weatherDisplayInfoList[position])
            }
            else -> {
                (holder as WeatherViewHolder).bind(weatherDisplayInfoList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (weatherDisplayInfoList[position].viewType) {
            0 -> VIEW_TYPE_HEADER
            1 -> VIEW_TYPE_WEATHER
            else -> VIEW_TYPE_WEATHER
        }
    }

    override fun getItemCount() = weatherDisplayInfoList.size


    fun updateAdapterList(updatedList : List<WeatherAdapterData>) {
        // put header list
        weatherDisplayInfoList = updatedList
        notifyDataSetChanged()
    }


    inner class HeaderViewHolder(private val binding : ItemRvHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position : Int) {

        }
    }

    inner class WeatherViewHolder(private val binding: ItemRvWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherAdapterData) {
            binding.data = item
        }
    }
}
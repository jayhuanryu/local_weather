package com.awesomejay.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesomejay.weather.adapters.WeatherRecyclerViewAdapter
import com.awesomejay.weather.databinding.ActivityMainBinding
import com.awesomejay.weather.models.WeatherAdapterData
import com.awesomejay.weather.view_models.MainViewModel
import com.awesomejay.weather.view_models.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name

    // View Model
    private val mainViewModel : MainViewModel by viewModels { MainViewModelFactory((application as LocalWeatherApplication).repository) }

    // View Binding
    private lateinit var binding : ActivityMainBinding


    // Track updating
    var numberOfCity = -1

    private lateinit var recyclerViewAdapter : WeatherRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()
        addObservers()
        refresh()

    }

    private fun initView() {
        recyclerViewAdapter = WeatherRecyclerViewAdapter()
        binding.layoutSwipeRefresh.isEnabled = true
        binding.layoutSwipeRefresh.post { binding.layoutSwipeRefresh.isRefreshing = true }
        binding.layoutSwipeRefresh.setOnRefreshListener {
            refresh()
        }
        binding.rvWeather.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,false)
            adapter = recyclerViewAdapter
        }
    }

    private fun addObservers() {
        mainViewModel.numberOfCities.observe(this, {
            if (it == -1) {
                binding.layoutSwipeRefresh.isRefreshing = false
            }

            numberOfCity = it
        })

        mainViewModel.weathers.observe(this, {
            Log.d(TAG, "[addObserver] >> weatherList ${it.size}")

            if (numberOfCity == it.size) {
                var headeredList = mutableListOf<WeatherAdapterData>()
                val headerData = WeatherAdapterData(
                        0,
                        0,
                        " ",
                        it[0].todayWeather!!,
                        it[0].tomorrowWeather!!
                )
                headeredList.add(headerData)
                headeredList.addAll(it)
                recyclerViewAdapter.updateAdapterList(headeredList)
                binding.layoutSwipeRefresh.isRefreshing = false
            }

        })
    }

    private fun refresh() {
        mainViewModel.getAllCityNames()
        recyclerViewAdapter.updateAdapterList(emptyList())
        binding.layoutSwipeRefresh.isRefreshing = true
    }


}
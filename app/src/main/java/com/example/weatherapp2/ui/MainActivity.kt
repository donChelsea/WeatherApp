package com.example.weatherapp2.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp2.databinding.ActivityMainBinding
import com.example.weatherapp2.ui.forecast.ForecastViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import android.R.attr.name

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.forecast.observe(this@MainActivity) { forecast ->
            forecast.list.forEach { day ->
                val date = day.dateTimeString.split(" ")[0]

                if (!viewModel.weekMap.containsKey(date)) {
                    viewModel.weekMap[date] = mutableListOf(day)
                } else {
                    viewModel.weekMap[date]?.add(day)
                }
            }

            binding.apply {
                val adapter = ForecastViewPagerAdapter(this@MainActivity, viewModel.weekMap.keys.toList())
                forecastViewpager.adapter = adapter

                cityNameTv.text = forecast.city.name
                indicator.setViewPager(binding.forecastViewpager)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getForecast("Brooklyn")
        }
    }
}
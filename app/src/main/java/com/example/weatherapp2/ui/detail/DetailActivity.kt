package com.example.weatherapp2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.weatherapp2.data.entities.Day
import com.example.weatherapp2.databinding.ActivityDetailBinding
import com.example.weatherapp2.ui.MainViewModel
import com.example.weatherapp2.ui.forecast.ForecastFragment.Companion.ARG_DAY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val day = intent.getParcelableExtra<Day>(ARG_DAY)

        binding.apply {
            if (day != null) {
                cityTv.text = viewModel.city
                currentTempTv.text = day.temp.temp.toInt().toString()
                timeTv.text = day.dateTimeString.split(" ")[1]
                maxTempTextTv.text = day.temp.max.toInt().toString()
                minTempTextTv.text = day.temp.min.toInt().toString()
            }
        }
    }
}
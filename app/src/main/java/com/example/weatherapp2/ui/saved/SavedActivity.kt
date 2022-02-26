package com.example.weatherapp2.ui.saved

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.weatherapp2.data.entities.Day
import com.example.weatherapp2.databinding.ActivityMainBinding
import com.example.weatherapp2.databinding.ActivitySavedBinding
import com.example.weatherapp2.ui.MainActivity
import com.example.weatherapp2.ui.MainViewModel
import com.example.weatherapp2.ui.detail.DetailActivity
import com.example.weatherapp2.ui.forecast.ForecastDatesAdapter
import com.example.weatherapp2.ui.forecast.ForecastFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivitySavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locations = viewModel.readDB()

        binding.apply {
            val adapter = locations.let { SavedLocationsAdapter(this@SavedActivity, it) {location -> onLocationClick(location)} }
            savedListView.adapter = adapter
        }
    }

    private fun onLocationClick(location: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ARG_LOCATION, location)
        startActivity(intent)
    }

    companion object {
        const val ARG_LOCATION = "location"
    }

}
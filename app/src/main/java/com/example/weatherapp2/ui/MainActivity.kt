package com.example.weatherapp2.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.weatherapp2.R
import com.example.weatherapp2.databinding.ActivityMainBinding
import com.example.weatherapp2.ui.forecast.ForecastViewPagerAdapter
import com.example.weatherapp2.ui.saved.SavedActivity
import com.example.weatherapp2.ui.saved.SavedActivity.Companion.ARG_SAVED_LOCATION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(ARG_SAVED_LOCATION) != null) {
            viewModel.city = intent.getStringExtra(ARG_SAVED_LOCATION).toString()
            CoroutineScope(Dispatchers.Main).launch { viewModel.getForecast(viewModel.city) }
        }

        viewModel.forecast.observe(this@MainActivity) { forecast ->
            forecast.list.forEach { day ->
                val date = day.dateTimeString.split(" ")[0]

                if (!viewModel.weekMap.containsKey(date)) {
                    viewModel.weekMap[date] = mutableListOf(day)
                } else {
                    viewModel.weekMap[date]?.add(day)
                }
            }

            if (forecast.city.name.isNotEmpty()) {
                viewModel.city = forecast.city.name
            }

            binding.apply {
                val adapter =
                    ForecastViewPagerAdapter(this@MainActivity, viewModel.weekMap.keys.toList())
                forecastViewpager.adapter = adapter

                cityNameTv.text = viewModel.city
                indicator.setViewPager(binding.forecastViewpager)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forecast_menu, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    if (p0 != null) {
                        p0.capitalize()
                        viewModel.city = p0
                        viewModel.getForecast(p0)
                        viewModel.addCityToDB(p0)
                    }
                }
                searchView.setQuery("", false)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            true
        }

        R.id.action_saved -> {
            val intent = Intent(this, SavedActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}
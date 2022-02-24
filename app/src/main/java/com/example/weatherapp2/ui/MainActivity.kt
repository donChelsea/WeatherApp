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
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import com.example.weatherapp2.R
import com.example.weatherapp2.data.db.DBHandler
import com.example.weatherapp2.ui.saved.SavedActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var dbHandler: DBHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DBHandler(this@MainActivity)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView

        // search queryTextChange Listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    if (p0 != null) {
                        p0.capitalize()
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

        //Expand Collapse listener
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                showToast("Action Collapse")
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                showToast("Action Expand")
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
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }
}
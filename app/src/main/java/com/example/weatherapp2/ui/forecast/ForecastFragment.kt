package com.example.weatherapp2.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp2.data.entities.City
import com.example.weatherapp2.data.entities.Day
import com.example.weatherapp2.databinding.FragmentForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var binding: FragmentForecastBinding

    var city: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            forecast.list.forEach { day ->
                val date = day.dateTimeString.split(" ")[0]

                if (!viewModel.weekMap.containsKey(date)) {
                    viewModel.weekMap[date] = mutableListOf(day)
                } else {
                    viewModel.weekMap[date]?.add(day)
                }
            }


            Log.d("ForecastFragment", viewModel.weekMap.keys.toString())
            Log.d("ForecastFragment", viewModel.weekMap.values.elementAt(0).toString())
            binding.apply {
                forecastViewpager.adapter = ForecastAdapter(forecast)
                indicator.setViewPager(forecastViewpager)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getForecast("Brooklyn")
        }

    }

    fun parseDays(week: List<Day>) {

    }

}
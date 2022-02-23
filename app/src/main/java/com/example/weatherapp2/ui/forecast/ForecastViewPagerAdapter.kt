package com.example.weatherapp2.ui.forecast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ForecastViewPagerAdapter(activity: AppCompatActivity, private val forecastDays: List<String>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = forecastDays.size

    override fun createFragment(position: Int): Fragment {
        return ForecastFragment.getInstance(position, forecastDays[position])
    }
}

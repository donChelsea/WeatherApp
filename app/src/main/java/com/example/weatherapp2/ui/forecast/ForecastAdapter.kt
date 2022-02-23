package com.example.weatherapp2.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp2.data.entities.City
import com.example.weatherapp2.data.entities.Day
import com.example.weatherapp2.data.entities.WeatherResponse
import com.example.weatherapp2.databinding.ForecastSlideBinding
import com.example.weatherapp2.ui.forecast.ForecastAdapter.ForecastViewHolder
import com.squareup.picasso.Picasso

class ForecastAdapter(private val forecast: WeatherResponse) :
    RecyclerView.Adapter<ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ForecastSlideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecast.list[position], forecast.city)
    }

    override fun getItemCount() = forecast.list.size

    inner class ForecastViewHolder(private val binding: ForecastSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(day: Day, city: City) {
                binding.apply {
                    cityNameTv.text = city.name
                    dateTv.text = day.dateTimeString

                    val weatherIconUri = "http://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png"
                    Picasso.get().load(weatherIconUri).into(weatherIcon)


                }
            }
    }

}
package com.example.weatherapp2.data.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val timezone: String,
    val daily: List<Day>
)

data class Day(
    @SerializedName("dt")
    val dateTime: String,
    @SerializedName("temp")
    val temp: Temp,
    val weather: List<Weather>
)

data class Temp(
    val min: Double,
    val max: Double
)

data class Weather(
    val description: String,
    val icon: String
)
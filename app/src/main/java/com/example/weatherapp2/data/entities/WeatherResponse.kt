package com.example.weatherapp2.data.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val list: List<Day>,
    val city: City,
)

data class Day(
    @SerializedName("dt")
    val dateTime: String,
    @SerializedName("dt_txt")
    val dateTimeString: String,
    @SerializedName("main")
    val temp: Temp,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Temp(
    @SerializedName("temp_min")
    val min: Double,
    @SerializedName("temp_max")
    val max: Double,
    @SerializedName("temp")
    val temp: Double,

)

data class Weather(
    @SerializedName("main")
    val description: String,
    val icon: String
)

data class City(
    val name: String,
    val coord: Coord
)

data class Coord(
    val lat: Double,
    val lon: Double
)
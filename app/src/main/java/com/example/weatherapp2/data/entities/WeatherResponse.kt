package com.example.weatherapp2.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class WeatherResponse(
    val list: List<Day>,
    val city: City,
)

@Parcelize
data class Day(
    @SerializedName("dt")
    val dateTime: String,
    @SerializedName("dt_txt")
    val dateTimeString: String,
    @SerializedName("main")
    val temp: Temp,
    @SerializedName("weather")
    val weather: List<Weather>
): Parcelable

@Parcelize
data class Temp(
    @SerializedName("temp_min")
    val min: Double,
    @SerializedName("temp_max")
    val max: Double,
    @SerializedName("temp")
    val temp: Double,
) : Parcelable

@Parcelize
data class Weather(
    @SerializedName("main")
    val description: String,
    val icon: String
): Parcelable

data class City(
    val name: String = "",
    val coord: Coord = Coord()
)

data class Coord(
    val lat: Double = 0.00,
    val lon: Double = 0.00
)
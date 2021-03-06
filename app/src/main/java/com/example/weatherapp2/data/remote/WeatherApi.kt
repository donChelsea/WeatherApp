package com.example.weatherapp2.data.remote

import com.example.weatherapp2.data.entities.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast/?units=imperial")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") appId: String
    ): Response<WeatherResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}

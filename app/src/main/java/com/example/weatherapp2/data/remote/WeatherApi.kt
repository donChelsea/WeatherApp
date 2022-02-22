package com.example.weatherapp2.data.remote

import com.example.weatherapp2.data.entities.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast/?cnt=7&units=imperial")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") appId: String
    ): Response<WeatherResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}

//?lat=40.63012&lon=-73.8879975&cnt=7&appid=dbad4777028d9b7e22614e23fb08433a
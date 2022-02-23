package com.example.weatherapp2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp2.data.entities.Day
import com.example.weatherapp2.data.entities.WeatherResponse
import com.example.weatherapp2.data.remote.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherApi: WeatherApi) : ViewModel() {

    private val _forecast = MutableLiveData<WeatherResponse>()
    val forecast: LiveData<WeatherResponse> = _forecast

    val weekMap = mutableMapOf<String, MutableList<Day>>()

    var position = 0
    var currentDayInView = ""

    suspend fun getForecast(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherApi.getForecast(city, "dbad4777028d9b7e22614e23fb08433a")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        _forecast.value = it
                    }
                } else {
                    throw Exception(response.errorBody()?.charStream()?.readText())
                }
            }
        }
    }

}
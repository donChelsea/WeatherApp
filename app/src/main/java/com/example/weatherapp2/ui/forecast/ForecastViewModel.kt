package com.example.weatherapp2.ui.forecast

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp2.R
import com.example.weatherapp2.data.entities.City
import com.example.weatherapp2.data.entities.Day
import com.example.weatherapp2.data.remote.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val weatherApi: WeatherApi) : ViewModel() {

    private val _forecast = MutableLiveData<List<Day>>()
    val forecast: LiveData<List<Day>> = _forecast

    private val _city = MutableLiveData<City>()
    val city: LiveData<City> = _city

    suspend fun getForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherApi.getForecast("33.44", "-94.04", "dbad4777028d9b7e22614e23fb08433a")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        _forecast.value = it.list
                        Log.d("ForecastViewModel", it.list.toString())
                        Log.d("ForecastViewModel", it.city.coord.toString())
                    }
                } else {
                    throw Exception(response.errorBody()?.charStream()?.readText())
                }
            }
        }
    }

}
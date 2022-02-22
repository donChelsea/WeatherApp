package com.example.weatherapp2.ui.forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    suspend fun getForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherApi.getForecast("33.44", "-94.04", "imperial", "dbad4777028d9b7e22614e23fb08433a")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        Log.d("ForecastViewModel", it.daily.toString())
                    }
                } else {
                    throw Exception(response.errorBody()?.charStream()?.readText())
                }
            }
        }
    }

}
package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.database.WeatherEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weather = MutableStateFlow<WeatherEntity?>(null)
    val weather: StateFlow<WeatherEntity?> get() = _weather

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchWeather(location: String) {
        viewModelScope.launch {
            try {
                val result = repository.getWeather(location)
                if (result != null) {
                    _weather.value = result
                    _error.value = null
                } else {
                    _error.value = "Unable to fetch weather data for $location"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}
package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.database.WeatherDatabase

class WeatherViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            val database = WeatherDatabase.getInstance(context)
            val weatherDao = database.weatherDao()
            val weatherApi = WeatherApi.create()
            val repository = WeatherRepository(weatherDao, weatherApi, context)
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
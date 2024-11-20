package com.example.weatherapp.data

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.database.WeatherDao
import com.example.weatherapp.database.WeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDao: WeatherDao, private val api: WeatherApi, private val context: Context) {

    suspend fun getWeather(location: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            val localData = weatherDao.getWeather(location)
            localData ?: fetchAndSaveWeather(location)
        }
    }

    private suspend fun fetchAndSaveWeather(location: String): WeatherEntity? {
        return try {
            val apiKey = context.getString(R.string.api_key)
            val coordinates = api.getCoordinates(location, 1, apiKey).firstOrNull()

            if (coordinates != null) {
                val weatherResponse = api.getWeather(coordinates.lat, coordinates.lon, "metric", apiKey)
                val weather = WeatherEntity(
                    location = weatherResponse.name,
                    temperature = weatherResponse.main.temp,
                    description = weatherResponse.weather.firstOrNull()?.description ?: "No description available"
                )
                weatherDao.insertWeather(weather)
                weather
            } else {
                null
            }
        } catch (e: Exception) {
            // Log the exception or handle it as needed
            null
        }
    }
}
package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDao {
    @Insert
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather WHERE location = :location")
    suspend fun getWeather(location: String): WeatherEntity?

    @Update
    suspend fun updateWeather(weather: WeatherEntity)
}

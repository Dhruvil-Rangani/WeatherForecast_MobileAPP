package com.example.weatherapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class GeocodingResponse(
    val name: String,
    val lat: Double,
    val lon: Double
)

data class WeatherResponse(
    val main: MainWeather,
    val weather: List<WeatherDescription>,
    val name: String
)

data class MainWeather(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

interface WeatherApi {
    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") location: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): List<GeocodingResponse>

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
        fun create(): WeatherApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}
package com.example.weather.repository

import com.example.weather.model.WeatherData

interface WeatherRepository {
    suspend fun getWeatherData(city: String,key: String): WeatherData
}
package com.example.five_days_weather.repository

import com.example.five_days_weather.api.WeatherApi
import com.example.five_days_weather.model.WeatherConverter
import com.example.five_days_weather.model.WeatherData

class WeatherRemoteRepository(
    private val api: WeatherApi
) : WeatherRepository {


    override suspend fun getWeatherData(city: String, key: String): WeatherData {
       val data = api.getWeatherData(city, key)
       return WeatherConverter.fromNetwork(data)
        }
}
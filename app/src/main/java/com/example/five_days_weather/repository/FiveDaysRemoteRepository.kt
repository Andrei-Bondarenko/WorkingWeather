package com.example.five_days_weather.repository

import com.example.five_days_weather.api.WeatherApi
import com.example.five_days_weather.model.WeatherConverter
import com.example.five_days_weather.model.WeatherData
import timber.log.Timber

class FiveDaysRemoteRepository(
    private val api: WeatherApi
) : FiveDaysRepository {


    override suspend fun getWeatherData(city: String, key: String): WeatherData {
       val data = api.getWeatherData(city, key)
        Timber.d("DATA____ $data")
       return WeatherConverter.fromNetwork(data)
        }
}
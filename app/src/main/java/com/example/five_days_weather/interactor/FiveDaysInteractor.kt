package com.example.five_days_weather.interactor

import com.example.five_days_weather.model.WeatherData
import com.example.five_days_weather.repository.FiveDaysRemoteRepository

class FiveDaysInteractor(
    private val remoteRepository: FiveDaysRemoteRepository
) {
   suspend fun getWeatherData(city:String, key: String): WeatherData =
        remoteRepository.getWeatherData(city,key)
}
package com.example.weather.interactor

import com.example.weather.repository.WeatherRemoteRepository
import com.example.weather.model.WeatherData

class WeatherInteractor(
    private val remoteRepository: WeatherRemoteRepository
) {
    suspend fun getWeatherData(city:String, key: String): WeatherData {
        return remoteRepository.getWeatherData(city,key)
    }
}
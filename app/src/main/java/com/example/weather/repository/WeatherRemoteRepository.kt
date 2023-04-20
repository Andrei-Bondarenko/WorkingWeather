package com.example.weather.repository

import com.example.weather.api.WeatherApi
import com.example.weather.api.model.WeatherDataResponse
import com.example.weather.model.WeatherConverter
import com.example.weather.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class WeatherRemoteRepository(
    private val api: WeatherApi
) : WeatherRepository {


    override suspend fun getWeatherData(city: String, key: String): WeatherData {
        val data = api.getWeatherData(city, key)
        return WeatherConverter.fromNetwork(data)
    }
}
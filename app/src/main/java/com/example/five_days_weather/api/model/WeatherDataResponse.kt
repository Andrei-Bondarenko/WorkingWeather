package com.example.five_days_weather.api.model

data class WeatherDataResponse(
    val cod:String,
    val message:Int,
    val cnt:Int,
    val list: List<ListWeatherResponse>,
    val city:CityResponse
)

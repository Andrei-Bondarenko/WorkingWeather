package com.example.five_days_weather.model

data class WeatherData(
    val cod: String,
    val message:Int,
    val cnt:Int,
    val listWeather: List<ListWeather>,
    val city:City

)

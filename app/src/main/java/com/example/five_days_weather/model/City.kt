package com.example.five_days_weather.model

data class City(
    val id:Int,
    val name:String,
    val coord:Coord,
    val country:String,
    val population:Int,
    val timezone:Int,
    val sunrise: Long,
    val sunset: Long
)

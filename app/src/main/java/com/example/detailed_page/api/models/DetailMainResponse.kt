package com.example.detailed_page.api.models

data class DetailMainResponse(
    val temp: Float?,
    val feels_like: Float?,
    val temp_min: Float?,
    val temp_max: Float?,
    val pressure: Int?,
    val sea_level: Int?,
    val grnd_level: Int?,
    val humidity: Int?,
    val temp_kf: Int?
)

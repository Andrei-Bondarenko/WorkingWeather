package com.example.five_days_weather.ui

import com.example.common.mvp.MvpPresenter
import com.example.common.mvp.MvpView
import com.example.five_days_weather.model.WeatherData

interface FiveDaysWeatherContract {
    interface View: MvpView {
        fun showData(data: WeatherData)
        fun showLoading(isVisible: Boolean)
    }
    interface Presenter: MvpPresenter<View> {
        fun getData(key: String, city: String)
    }
}
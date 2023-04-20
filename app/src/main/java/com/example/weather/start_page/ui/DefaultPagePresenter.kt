package com.example.weather.start_page.ui

import com.example.common.mvp.BasePresenter
import com.example.weather.interactor.WeatherInteractor
import kotlinx.coroutines.launch
import timber.log.Timber

class DefaultPagePresenter(
    private val interactor: WeatherInteractor
) : BasePresenter<DefaultPageContract.View>(),
    DefaultPageContract.Presenter {

    override fun getData(key: String, city: String) {
        launch {
            view?.showLoading(true)
            try {
                val weatherData = interactor.getWeatherData(city, key)
                view?.showData(weatherData)
                view?.setContent(true)
            } catch (t: Throwable) {
                Timber.e("Get data error ${t.message}")
            } finally {
                view?.showLoading(false)
            }
        }
    }

}
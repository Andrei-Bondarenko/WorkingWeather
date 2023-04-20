package com.example.weather.ui

import com.example.common.mvp.BasePresenter
import com.example.weather.interactor.WeatherInteractor
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherPresenter(
    private val interactor: WeatherInteractor
) : WeatherContract.Presenter,
    BasePresenter<WeatherContract.View>() {

    override fun getData(key: String, city: String) {
      launch {
          try {
              view?.showLoading(true)
              val weatherData = interactor.getWeatherData(city, key)
              weatherData.let { weather ->
                  view?.changeBackgroundImage(weather)
                  view?.showData(weather)
                  view?.setContent(true)
              }
          } catch (t: Throwable) {
              view?.showError(t.message.toString())
          } finally {
              view?.showLoading(isLoading = false)
              Timber.d("_____GET DATA ")
          }
      }
    }
}
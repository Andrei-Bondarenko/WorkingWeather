package com.example.five_days_weather.ui

import com.example.common.mvp.BasePresenter
import com.example.five_days_weather.interactor.FiveDaysInteractor
import kotlinx.coroutines.launch
import timber.log.Timber


class FiveDaysWeatherPresenter(
    private val interactor: FiveDaysInteractor
) : FiveDaysWeatherContract.Presenter,
    BasePresenter<FiveDaysWeatherContract.View>() {

    override fun getData(key: String, city: String) {
        launch {
            view?.showLoading(true)
            try {
                val weatherData = interactor.getWeatherData(city, key)
                view?.showLoading(false)
                view?.showData(weatherData)
            } catch (t: Throwable) {
                Timber.e("DATA ERROR ---->>>>>$t")
            } finally {
                view?.showLoading(false)
            }
        }
    }


}
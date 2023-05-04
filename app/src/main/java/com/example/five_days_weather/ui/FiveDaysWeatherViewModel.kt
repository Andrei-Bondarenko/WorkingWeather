package com.example.five_days_weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.interactor.WeatherInteractor
import com.example.weather.model.WeatherData
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

class FiveDaysWeatherViewModel(
    private val interactor: WeatherInteractor
) : ViewModel() {

    private val _fiveDaysWeatherLiveData = MutableLiveData<WeatherData>()
    val fiveDaysWeatherLiveData: LiveData<WeatherData>
        get() = _fiveDaysWeatherLiveData


    private val _isLoadingFiveDays = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoadingFiveDays


    fun getWeatherData(cityName: String, key: String) {
        viewModelScope.launch {
            try {
                _isLoadingFiveDays.value = true
                val weatherData = interactor.getWeatherData(cityName, key)
                _fiveDaysWeatherLiveData.value = weatherData
            } catch (t: Throwable) {
                Timber.e(t.message)
            } catch (e: CancellationException) {
                Timber.e(e.message)
            } finally {
                _isLoadingFiveDays.value = false
            }
        }
    }
}

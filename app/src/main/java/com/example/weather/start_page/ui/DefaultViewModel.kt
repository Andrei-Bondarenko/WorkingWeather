package com.example.weather.start_page.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.interactor.WeatherInteractor
import com.example.weather.model.WeatherData
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

class DefaultViewModel(
    private val interactor: WeatherInteractor
) : ViewModel() {

    private val _defaultWeatherLiveData = MutableLiveData<WeatherData>()
    val defaultWeatherLiveData: LiveData<WeatherData>
        get() = _defaultWeatherLiveData


    private val _isLoadingDefault = MutableLiveData<Boolean>()
    val isLoadingDefault: LiveData<Boolean>
        get() = _isLoadingDefault


    fun getWeatherData(cityName: String, key: String) {
        viewModelScope.launch {
            try {
                _isLoadingDefault.value = true
                val weatherData = interactor.getWeatherData(cityName, key)
                _defaultWeatherLiveData.value = weatherData
            } catch (t: Throwable) {
                Timber.e(t.message)
            } catch (e: CancellationException) {
                Timber.e(e.message)
            } finally {
                _isLoadingDefault.value = false
            }
        }
    }
}

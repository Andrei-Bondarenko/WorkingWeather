package com.example.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.interactor.WeatherInteractor
import com.example.weather.model.WeatherData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

class WeatherViewModel(
    private val interactor: WeatherInteractor
) : ViewModel() {
    private val _weatherFlow = MutableStateFlow<WeatherData?>(null)
    val weatherFlow: StateFlow<WeatherData?>
        get() = _weatherFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()


    fun getWeatherData(cityName: String, key: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val weatherData = interactor.getWeatherData(cityName, key)
                _weatherFlow.emit(weatherData)
            } catch (t: Throwable) {
                Timber.e(t.message)
            } catch (e: CancellationException) {
                Timber.e(e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}

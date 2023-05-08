package com.example.five_days_weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.five_days_weather.interactor.FiveDaysInteractor
import com.example.five_days_weather.model.WeatherData
import com.example.weather.interactor.WeatherInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

class FiveDaysWeatherViewModel(
    private val interactor: FiveDaysInteractor
) : ViewModel() {

    private val _weatherFlow= MutableStateFlow<WeatherData?>(null)
    val fiveDaysWeatherFlow: StateFlow<WeatherData?>
        get() = _weatherFlow.asStateFlow()



    private val _isLoading = MutableStateFlow(false)
    val isLoadingFiveDays: StateFlow<Boolean>
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

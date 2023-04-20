package com.example.detail_page

import android.os.Bundle
import android.view.View
import com.example.common.mvp.BaseFragment
import com.example.five_days_weather.ui.FiveDaysWeatherFragment
import com.example.utils.Arguments
import com.example.utils.extensions.*
import com.example.weather.model.WeatherData
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentWeatherDetailedPageBinding

class WeatherDetailedPageFragment : BaseFragment(R.layout.fragment_weather_detailed_page) {
    private val binding: FragmentWeatherDetailedPageBinding by lazy {
        FragmentWeatherDetailedPageBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(weatherData: WeatherData?) = WeatherDetailedPageFragment()
            .withArgs(Arguments.WEATHER_DATA to weatherData)
    }

    private val weatherData: WeatherData? by args(Arguments.WEATHER_DATA)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetailedWeather()

        with(binding) {
            toolBarDetailPageTitle.setNavigationOnClickListener {
                popScreen()
            }

            buttonFiveDaysInfo.setOnClickListener {
                val cityName = toolBarDetailPageTitle.title.toString()
                replaceScreen(FiveDaysWeatherFragment.newInstance(cityName))
            }
        }
    }

    private fun showDetailedWeather() {
        with(binding) {
            toolBarDetailPageTitle.title = weatherData?.name
            textViewFeelsLike.text = tempDescription(weatherData?.main?.feelsLike)
            textViewTempMin.text = tempDescription(weatherData?.main?.tempMin)
            textViewTempMax.text = tempDescription(weatherData?.main?.tempMax)
            textViewPressure.text = weatherData?.main?.pressure.toString()
            textViewHumidity.text = weatherData?.main?.humidity.toString()
        }
    }
}

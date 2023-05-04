package com.example.detail_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.mvp.BaseFragment
import com.example.five_days_weather.ui.FiveDaysWeatherFragment
import com.example.utils.Arguments
import com.example.utils.extensions.*
import com.example.weather.model.WeatherData
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentWeatherDetailedPageBinding
import timber.log.Timber

class WeatherDetailedPageFragment : BaseFragment(R.layout.fragment_weather_detailed_page) {



    private lateinit var binding: FragmentWeatherDetailedPageBinding

    companion object {
        fun newInstance(weatherData: WeatherData?) = WeatherDetailedPageFragment()
            .withArgs(Arguments.WEATHER_DATA to weatherData)
    }

    private val weatherData: WeatherData? by args(Arguments.WEATHER_DATA)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailedPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetailedWeather()

        Timber.d("_______WEATHERDATA===>>>    $weatherData")

        with(binding) {
            toolBarDetailPageTitle.setNavigationOnClickListener {
                popScreen()
            }

            buttonFiveDaysInfo.setOnClickListener {
                val cityName = toolBarDetailPageTitle.title.toString()
                replace(FiveDaysWeatherFragment.newInstance(cityName),R.id.fragmentContainer)
            }
            toolBarDetailPageTitle.setNavigationOnClickListener{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun showDetailedWeather() {
        with(binding) {
            toolBarDetailPageTitle.title = weatherData?.name
            feelsLikeTextView.text = tempDescription(weatherData?.main?.feels_like)
            tempMinTextView.text = tempDescription(weatherData?.main?.temp_min)
            tempMaxTextView.text = tempDescription(weatherData?.main?.temp_max)
            pressureTextView.text = weatherData?.main?.pressure.toString()
            airHumidityTextView.text = weatherData?.main?.humidity.toString()
            windSpeedTextView.text = weatherData?.wind?.speed.toString()
        }
    }
}

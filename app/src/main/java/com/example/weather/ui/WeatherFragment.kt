package com.example.weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.common.mvp.BaseFragment
import com.example.detail_page.WeatherDetailedPageFragment
import com.example.utils.extensions.replace
import com.example.weather.model.WeatherData
import com.example.weather.start_page.ui.DefaultFragment
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentWeatherBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.roundToInt

private const val KEY = "7465cd2201320080ec76abc3b7bb945d"

class WeatherFragment :
    BaseFragment(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by inject()
    private lateinit var binding: FragmentWeatherBinding

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            observe(viewModel.weatherFlow) { weatherData ->
                showData(weatherData)
                changeBackgroundImage(weatherData)
            }
            observe(viewModel.loading) { isLoading ->
                progressBar.isVisible = isLoading
                setContent(!isLoading)
            }

            cityEditText.doAfterTextChanged {
                viewModel.getWeatherData(cityName = it.toString(), key = KEY)
            }

            buttonBackToMyCity.setOnClickListener {
                replace(DefaultFragment.newInstance(), R.id.fragmentContainer)
            }
            moreButton.setOnClickListener {
                if (viewModel.weatherFlow.value != null){
                    replace(WeatherDetailedPageFragment.newInstance(viewModel.weatherFlow.value),R.id.fragmentContainer)
                }
            }
        }
    }

    fun showData(data: WeatherData?) {
        Timber.d("______showData: %s", data)
        with(binding) {
            tempTextView.text = data?.main?.temp?.roundToInt().toString()
            weatherTextView.text = data?.weather?.first()?.description
            airHumidityTextView.text = data?.main?.humidity.toString()
            windSpeedTextView.text = data?.wind?.speed.toString()
            feelsLikeTextView.text = data?.main?.feels_like.toString()
            changeBackgroundImage(data)
        }
    }


    fun changeBackgroundImage(data: WeatherData?) {
        with(binding) {
            when (data?.weather?.first()?.description) {
                "clear sky" -> constraintContainer.setBackgroundResource(R.drawable.clear_sky)
                "overcast clouds" -> constraintContainer.setBackgroundResource(R.drawable.overcastclouds)
                "few clouds" -> constraintContainer.setBackgroundResource(R.drawable.few_clouds)
                "moderate rain" -> constraintContainer.setBackgroundResource(R.drawable.moderaterain)
                "scattered clouds" -> constraintContainer.setBackgroundResource(R.drawable.scattered_clouds)
                "broken clouds" -> constraintContainer.setBackgroundResource(R.drawable.scattered_clouds)
                "light rain" -> constraintContainer.setBackgroundResource(R.drawable.light_rain)
                "heavy intensity rain" -> constraintContainer.setBackgroundResource(R.drawable.heavyintensityrain)
                "light snow" -> constraintContainer.setBackgroundResource(R.drawable.light_snow)
                "smoke" -> constraintContainer.setBackgroundResource(R.drawable.smoke)
                else -> constraintContainer.setBackgroundResource(R.drawable.clear_sky)
            }
        }
    }

    fun setContent(isVisible: Boolean) {
        binding.contentContainer.isVisible = isVisible
    }


}
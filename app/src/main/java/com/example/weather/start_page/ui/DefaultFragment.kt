package com.example.weather.start_page.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.common.mvp.BaseFragment
import com.example.detail_page.WeatherDetailedPageFragment
import com.example.utils.extensions.replace
import com.example.weather.model.WeatherData
import com.example.weather.ui.WeatherFragment
import com.example.weather.ui.WeatherViewModel
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentDefaultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.roundToInt


private const val KEY = "7465cd2201320080ec76abc3b7bb945d"
class DefaultFragment :
    BaseFragment(R.layout.fragment_default) {

    private val viewModel: DefaultViewModel by viewModel()
    private lateinit var binding: FragmentDefaultBinding

    companion object {
        fun newInstance() = DefaultFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()

            viewModel.getWeatherData(KEY, binding.myCityTextView.text.toString())
        with(binding) {
            buttonShowOtherCity.setOnClickListener {
                replace(WeatherFragment.newInstance(),R.id.fragmentContainer)
            }
            moreButton.setOnClickListener {
                if (tempTextView.text != ""){
                    replace(WeatherDetailedPageFragment.newInstance(viewModel.defaultWeatherLiveData.value),R.id.fragmentContainer)
                }
            }

        }
    }

    fun bind() {
        viewModel.defaultWeatherLiveData.observe(viewLifecycleOwner) { weatherData ->
            showData(weatherData)
            changeBackgroundImage(weatherData)
        }

        with(binding) {
            viewModel.isLoadingDefault.observe(viewLifecycleOwner) { isLoading ->
                progressBar.isVisible = isLoading
                setContent(!isLoading)
            }
        }
    }


    fun showData(data: WeatherData) {
        Timber.d("_____showData: %s", data)
        with(binding) {
            tempTextView.text = data.main.temp.roundToInt().toString()
            weatherTextView.text = data.weather.first().description
            airHumidityTextView.text = data.main.humidity.toString()
            windSpeedTextView.text = data.wind.speed.toString()
            feelsLikeTextView.text = data.main.feels_like.toString()
            changeBackgroundImage(data)
        }
    }

     fun changeBackgroundImage(data: WeatherData) {
        with(binding) {
            when (data.weather.first().description) {
                "clear sky" -> defaultFragmentContainer.setBackgroundResource(R.drawable.clear_sky)
                "overcast clouds" -> defaultFragmentContainer.setBackgroundResource(R.drawable.overcastclouds)
                "few clouds" -> defaultFragmentContainer.setBackgroundResource(R.drawable.few_clouds)
                "moderate rain" -> defaultFragmentContainer.setBackgroundResource(R.drawable.moderaterain)
                "scattered clouds" -> defaultFragmentContainer.setBackgroundResource(R.drawable.scattered_clouds)
                "broken clouds" -> defaultFragmentContainer.setBackgroundResource(R.drawable.scattered_clouds)
                "light rain" -> defaultFragmentContainer.setBackgroundResource(R.drawable.light_rain)
                "heavy intensity rain" -> defaultFragmentContainer.setBackgroundResource(R.drawable.heavyintensityrain)
                "light snow" -> defaultFragmentContainer.setBackgroundResource(R.drawable.light_snow)
                "smoke" -> defaultFragmentContainer.setBackgroundResource(R.drawable.smoke)
                else -> defaultFragmentContainer.setBackgroundResource(R.drawable.clear_sky)
            }
        }
    }

    fun setContent(isVisible: Boolean) {
        binding.contentContainer.isVisible = isVisible
    }


}
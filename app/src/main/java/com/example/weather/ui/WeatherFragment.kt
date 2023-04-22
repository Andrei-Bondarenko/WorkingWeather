package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.five_days_weather.ui.FiveDaysWeatherFragment
import com.example.utils.Client
import com.example.utils.extensions.replace
import com.example.weather.api.WeatherApi
import com.example.common.mvp.BaseMvpFragment
import com.example.detail_page.WeatherDetailedPageFragment
import com.example.utils.Arguments
import com.example.utils.extensions.replaceScreen
import com.example.utils.extensions.withArgs
import com.example.weather.interactor.WeatherInteractor
import com.example.weather.model.Weather
import com.example.weather.model.WeatherData
import com.example.weather.repository.WeatherRemoteRepository
import com.example.weather.start_page.ui.DefaultFragment
import com.example.workingweather.R
import com.example.workingweather.databinding.WeatherFragmentBinding
import timber.log.Timber
import kotlin.math.roundToInt


class WeatherFragment :
    BaseMvpFragment<WeatherContract.View, WeatherContract.Presenter>(R.layout.weather_fragment),
    WeatherContract.View {


    private val key by lazy {
        getString(R.string.key)
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }


    private val api = Client.getClient().create(WeatherApi::class.java)
    private val remoteRepository = WeatherRemoteRepository(api)
    private val interactor = WeatherInteractor(remoteRepository)
    override val presenter: WeatherPresenter = WeatherPresenter(interactor)

    private lateinit var binding: WeatherFragmentBinding

    private var weatherData: WeatherData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cityEditText.doAfterTextChanged {
                presenter.getData(key, it.toString())
            }
            buttonBackToMyCity.setOnClickListener {
                replace(DefaultFragment.newInstance(),R.id.fragmentContainer)
            }
            moreButton.setOnClickListener {
                replace(WeatherDetailedPageFragment.newInstance(weatherData),R.id.fragmentContainer)

            }

        }
    }

    override fun showData(data: WeatherData) {
        Timber.d("______showData: %s", data)
        weatherData = data
        with(binding) {
            tempTextView.text = data.main.temp.roundToInt().toString()
            weatherTextView.text = data.weather.first().description
            airHumidityTextView.text = data.main.humidity.toString()
            windSpeedTextView.text = data.wind.speed.toString()
            feelsLikeTextView.text = data.main.feels_like.toString()
            changeBackgroundImage(data)
        }
    }


    override fun changeBackgroundImage(data: WeatherData) {
        with(binding) {
            when (data.weather.first().description) {
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

    override fun showError(e: String) {
        Timber.d("____WeatherFragment ERROR ===>>> $e")
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    override fun setContent(isVisible: Boolean) {
        binding.contentContainer.isVisible = isVisible
    }

    fun hideContent() {
        binding.constraintContainer.setBackgroundResource(R.drawable.clear_sky)
        binding.weatherTextView.text = ""
        binding.tempTextView.text = ""
        binding.progressBar.isVisible = true
    }

}
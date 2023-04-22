package com.example.weather.start_page.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.common.mvp.BaseMvpFragment
import com.example.detail_page.WeatherDetailedPageFragment
import com.example.utils.Arguments
import com.example.utils.Client
import com.example.utils.extensions.replace
import com.example.utils.extensions.replaceScreen
import com.example.utils.extensions.withArgs
import com.example.weather.api.WeatherApi
import com.example.weather.interactor.WeatherInteractor
import com.example.weather.model.WeatherData
import com.example.weather.repository.WeatherRemoteRepository
import com.example.weather.ui.WeatherFragment
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentDefaultBinding
import timber.log.Timber
import kotlin.math.roundToInt


class DefaultFragment :
    BaseMvpFragment<DefaultPageContract.View, DefaultPageContract.Presenter>(R.layout.fragment_default),
    DefaultPageContract.View {

    private val api: WeatherApi = Client.getClient().create(WeatherApi::class.java)
    private val key by lazy {
        getString(R.string.key)
    }

    companion object {
        fun newInstance() = DefaultFragment()
    }


    private val remoteRepository = WeatherRemoteRepository(api)
    private val interactor = WeatherInteractor(remoteRepository)
    override val presenter: DefaultPagePresenter = DefaultPagePresenter(interactor)

    private lateinit var binding: FragmentDefaultBinding

    private var weatherData: WeatherData? = null

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
        presenter.getData(key, binding.myCityTextView.text.toString())
        with(binding) {
            buttonShowOtherCity.setOnClickListener {
                replace(WeatherFragment.newInstance(),R.id.fragmentContainer)
            }
            moreButton.setOnClickListener {
                replace(WeatherDetailedPageFragment.newInstance(weatherData),R.id.fragmentContainer)
            }

        }
    }

    override fun showData(data: WeatherData) {
        Timber.d("_____showData: %s", data)
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

    override fun showError(e: String) {
        Timber.e("_____Error: %s", e)
    }


    override fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    override fun setContent(isVisible: Boolean) {
        binding.contentContainer.isVisible = isVisible
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
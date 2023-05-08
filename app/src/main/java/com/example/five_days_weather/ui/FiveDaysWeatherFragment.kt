package com.example.five_days_weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.common.mvp.BaseFragment
import com.example.details_of_item.DetailsPageItemFragment
import com.example.five_days_weather.model.WeatherData
import com.example.five_days_weather.ui.adapter.FiveDaysWeatherAdapter
import com.example.utils.Arguments.CITY_NAME
import com.example.utils.extensions.args
import com.example.utils.extensions.replace
import com.example.utils.extensions.withArgs
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentFiveDaysWeatherBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

private const val KEY = "7465cd2201320080ec76abc3b7bb945d"

class FiveDaysWeatherFragment : BaseFragment(R.layout.fragment_five_days_weather) {

    private val viewModel: FiveDaysWeatherViewModel by inject()
    private lateinit var binding: FragmentFiveDaysWeatherBinding

    companion object {
        fun newInstance(cityName: String) = FiveDaysWeatherFragment()
            .withArgs(CITY_NAME to cityName)
    }

    private val cityName: String? by args(CITY_NAME)


    private val adapter: FiveDaysWeatherAdapter by lazy {
        FiveDaysWeatherAdapter { item ->
            replace(DetailsPageItemFragment.newInstance(item), R.id.fragmentContainer)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiveDaysWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = adapter
            cityName?.let { viewModel.getWeatherData(cityName = it,key = KEY) }
            observe(viewModel.fiveDaysWeatherFlow) { weatherData ->
                showData(weatherData)
            }

            observe(viewModel.isLoadingFiveDays) { isLoading ->
                progressFiveDays.isVisible = isLoading
            }
        }
    }


    fun showData(data: WeatherData?) {
        Timber.d("______showData: $data")
        data?.listWeather?.let { adapter.setData(it) }
    }
}


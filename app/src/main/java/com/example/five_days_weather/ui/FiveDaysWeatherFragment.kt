package com.example.five_days_weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.common.mvp.BaseMvpFragment
import com.example.five_days_weather.api.WeatherApi
import com.example.five_days_weather.interactor.WeatherInteractor
import com.example.five_days_weather.model.WeatherData
import com.example.five_days_weather.repository.WeatherRemoteRepository
import com.example.five_days_weather.ui.adapter.FiveDaysWeatherAdapter
import com.example.utils.Arguments.CITY_NAME
import com.example.utils.Client
import com.example.utils.extensions.args
import com.example.utils.extensions.withArgs
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentFiveDaysWeatherBinding
import com.example.workingweather.databinding.ItemWeatherBinding
import timber.log.Timber


class FiveDaysWeatherFragment :
    BaseMvpFragment<FiveDaysWeatherContract.View, FiveDaysWeatherContract.Presenter>(R.layout.fragment_five_days_weather),
    FiveDaysWeatherContract.View {



    companion object {
        fun newInstance(cityName: String) = FiveDaysWeatherFragment()
            .withArgs(CITY_NAME to cityName)
    }

    private val cityName: String? by args(CITY_NAME)


    private val api: WeatherApi = Client.getClient().create(WeatherApi::class.java)
    private lateinit var key: String
    private val remoteRepository = WeatherRemoteRepository(api)
    private val interactor = WeatherInteractor(remoteRepository)
    override val presenter: FiveDaysWeatherPresenter = FiveDaysWeatherPresenter(interactor)
    private val adapter: FiveDaysWeatherAdapter = FiveDaysWeatherAdapter()

    private lateinit var binding: FragmentFiveDaysWeatherBinding

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
        key = getString(R.string.key)
        with(binding) {
            recyclerView.adapter = adapter
            cityName?.let { presenter.getData(key, it) }
        }

    }


    override fun showData(data: WeatherData) {
        Timber.d("______showData: $data")
        adapter.setData(data.listWeather)
    }

    override fun showLoading(isVisible: Boolean) {
        binding.progressFiveDays.isVisible = isVisible
    }

    override fun showError(e: String) {
        Timber.d("_____FIVE DAIS FRAGMENT WEATHER ERROR====>>>> $e")
    }

}

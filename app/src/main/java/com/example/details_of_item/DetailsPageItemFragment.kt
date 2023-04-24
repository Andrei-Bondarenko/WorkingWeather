package com.example.details_of_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.mvp.BaseFragment
import com.example.five_days_weather.model.ListWeather
import com.example.utils.Arguments
import com.example.utils.extensions.args
import com.example.utils.extensions.withArgs
import com.example.workingweather.R
import com.example.workingweather.databinding.FragmentWeatherDetailedPageBinding

class DetailsPageItemFragment: BaseFragment(R.layout.fragment_weather_detailed_page) {

    private lateinit var binding: FragmentWeatherDetailedPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailedPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(fiveDaysItemData: ListWeather) = DetailsPageItemFragment()
            .withArgs(Arguments.FIVE_DAYS_WEATHER_DETAILS to fiveDaysItemData)
    }

    private val fiveDaysItemData: String? by args(Arguments.CITY_NAME)




}
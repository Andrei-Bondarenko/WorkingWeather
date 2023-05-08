package com.example.five_days_weather.di

import com.example.common.di.InjectionModule
import com.example.five_days_weather.repository.FiveDaysRemoteRepository
import com.example.five_days_weather.repository.FiveDaysRepository
import com.example.five_days_weather.interactor.FiveDaysInteractor
import com.example.five_days_weather.ui.FiveDaysWeatherViewModel
import com.example.five_days_weather.api.WeatherApi
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit


object FiveDaysWeatherModule : InjectionModule {

    override fun create() = module {
        single { FiveDaysRemoteRepository(get()) } bind FiveDaysRepository::class
        single { get<Retrofit>().create(WeatherApi::class.java) }
        factoryOf(::FiveDaysInteractor)

        viewModelOf(::FiveDaysWeatherViewModel)
    }
}


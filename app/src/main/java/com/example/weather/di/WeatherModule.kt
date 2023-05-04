package com.example.weather.di

import com.example.common.di.InjectionModule
import com.example.weather.api.WeatherApi
import com.example.weather.interactor.WeatherInteractor
import com.example.weather.repository.WeatherRemoteRepository
import com.example.weather.repository.WeatherRepository
import com.example.weather.ui.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object WeatherModule : InjectionModule {

    override fun create() = module {
        single { get<Retrofit>().create(WeatherApi::class.java) }
        single { WeatherRemoteRepository(get()) } bind WeatherRepository::class
        factoryOf(::WeatherInteractor)

        viewModelOf(::WeatherViewModel)
    }
}


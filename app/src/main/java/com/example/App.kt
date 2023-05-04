package com.example

import android.app.Application
import com.example.common.di.NetworkModule
import com.example.weather.di.WeatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setupKoin() {
        stopKoin()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    NetworkModule.create(),
                    WeatherModule.create(),
//                    FiveDaysModule.create()
                )
            )
        }
    }
}
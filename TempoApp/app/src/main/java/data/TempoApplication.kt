package com.example.tempo

import android.app.Application
import com.example.tempo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TempoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@TempoApplication)
            modules(appModule)
        }
    }
}

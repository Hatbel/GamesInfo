package com.hatbel.gamesinfo

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.hatbel.gamesinfo.domain.di.appModule
import com.hatbel.gamesinfo.domain.di.mappersModule
import com.hatbel.gamesinfo.domain.di.networkModule
import com.hatbel.gamesinfo.domain.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()

        //start Koin context
        startKoin {
            androidContext(this@App)
            modules(mutableListOf(networkModule, appModule, useCaseModule, mappersModule))
        }
    }
}
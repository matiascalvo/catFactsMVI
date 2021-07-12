package com.matiascalvo.catfacts

import android.app.Application
import com.matiascalvo.catfacts.di.appModule
import com.matiascalvo.catfacts.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }
}

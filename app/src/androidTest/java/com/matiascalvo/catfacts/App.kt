package com.matiascalvo.catfacts

import android.app.Application
import com.matiascalvo.catfacts.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule))
        }
    }
}

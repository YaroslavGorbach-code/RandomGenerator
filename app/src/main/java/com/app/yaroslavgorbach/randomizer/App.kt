package com.app.yaroslavgorbach.randomizer

import android.app.Application
import com.app.yaroslavgorbach.randomizer.di.AppComponent
import com.app.yaroslavgorbach.randomizer.di.DaggerAppComponent

open class App: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
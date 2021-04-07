package com.example.yaroslavgorbach.randomizer

import android.app.Application
import com.example.yaroslavgorbach.randomizer.di.AppComponent
import com.example.yaroslavgorbach.randomizer.di.DaggerAppComponent

open class MyApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
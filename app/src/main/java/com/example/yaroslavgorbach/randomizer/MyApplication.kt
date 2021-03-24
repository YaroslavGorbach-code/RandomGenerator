package com.example.yaroslavgorbach.randomizer

import android.app.Application
import com.example.yaroslavgorbach.randomizer.di.AppComponent
import com.example.yaroslavgorbach.randomizer.di.DaggerAppComponent

open class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }
}
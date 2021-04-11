package com.example.yaroslavgorbach.randomizer.di

import android.content.Context
import com.example.yaroslavgorbach.randomizer.component.SoundManager
import com.example.yaroslavgorbach.randomizer.data.SoundPref.SoundPreferences
import dagger.Module
import dagger.Provides

@Module(includes = [AppBinds::class])
class AppModule {
    @Provides
    fun provideSoundManager(context: Context, soundPreferences: SoundPreferences): SoundManager {
       return SoundManager(context, soundPreferences)
    }
}

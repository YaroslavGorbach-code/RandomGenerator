package com.example.yaroslavgorbach.randomizer.di

import android.content.Context
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.local.soundPref.SoundPrefs
import dagger.Module
import dagger.Provides

@Module(includes = [AppBinds::class])
class AppModule {
    @Provides
    fun provideSoundManager(context: Context, soundPrefs: SoundPrefs): SoundManager {
       return SoundManager(context, soundPrefs)
    }
}

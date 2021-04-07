package com.example.yaroslavgorbach.randomizer.di

import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferences
import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferencesStorage
import dagger.Binds
import dagger.Module

@Module
abstract class AppBinds {
    @Binds
    abstract fun bindSoundPreferences(preferencesStorage: SoundPreferencesStorage): SoundPreferences
}
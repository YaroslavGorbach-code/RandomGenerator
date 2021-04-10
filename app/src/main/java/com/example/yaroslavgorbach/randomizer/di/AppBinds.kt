package com.example.yaroslavgorbach.randomizer.di

import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferences
import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferencesImp
import dagger.Binds
import dagger.Module

@Module
abstract class AppBinds {
    @Binds
    abstract fun bindSoundPreferences(preferencesImp: SoundPreferencesImp): SoundPreferences
}
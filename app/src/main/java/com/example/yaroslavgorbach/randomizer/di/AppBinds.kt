package com.example.yaroslavgorbach.randomizer.di

import com.example.yaroslavgorbach.randomizer.data.local.soundPref.SoundPrefs
import com.example.yaroslavgorbach.randomizer.data.local.soundPref.SoundPrefsImp
import dagger.Binds
import dagger.Module

@Module
abstract class AppBinds {
    @Binds
    abstract fun bindSoundPreferences(preferencesImp: SoundPrefsImp): SoundPrefs
}
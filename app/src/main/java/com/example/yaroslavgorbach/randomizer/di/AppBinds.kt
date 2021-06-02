package com.example.yaroslavgorbach.randomizer.di


import com.example.yaroslavgorbach.randomizer.data.local.commonpref.CommonPref
import com.example.yaroslavgorbach.randomizer.data.local.commonpref.CommonPrefImp
import com.example.yaroslavgorbach.randomizer.data.local.soundpref.SoundPrefs
import com.example.yaroslavgorbach.randomizer.data.local.soundpref.SoundPrefsImp
import dagger.Binds
import dagger.Module

@Module
abstract class AppBinds {
    @Binds
    abstract fun bindSoundPreferences(preferencesImp: SoundPrefsImp): SoundPrefs
    @Binds
    abstract fun bindCommonPreferences(preferencesImp: CommonPrefImp): CommonPref
}
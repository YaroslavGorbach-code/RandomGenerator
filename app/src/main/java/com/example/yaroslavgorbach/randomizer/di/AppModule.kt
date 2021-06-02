package com.example.yaroslavgorbach.randomizer.di

import android.content.Context
import com.example.yaroslavgorbach.randomizer.data.local.Repo
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.local.soundpref.SoundPrefs
import dagger.Module
import dagger.Provides

@Module(includes = [AppBinds::class])
class AppModule {
    @Provides
    fun provideSoundManager(context: Context, repo: Repo): SoundManager {
       return SoundManager(context, repo)
    }
}

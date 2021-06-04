package com.app.yaroslavgorbach.randomizer.di

import android.content.Context
import com.app.yaroslavgorbach.randomizer.data.local.Repo
import com.app.yaroslavgorbach.randomizer.feature.SoundManager
import dagger.Module
import dagger.Provides

@Module(includes = [AppBinds::class])
class AppModule {
    @Provides
    fun provideSoundManager(context: Context, repo: Repo): SoundManager {
       return SoundManager(context, repo)
    }
}

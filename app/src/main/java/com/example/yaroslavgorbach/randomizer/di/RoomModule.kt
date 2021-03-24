package com.example.yaroslavgorbach.randomizer.di

import android.content.Context
import com.example.yaroslavgorbach.randomizer.list.Database.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database{
        return Database.getInstance(context)
    }
}
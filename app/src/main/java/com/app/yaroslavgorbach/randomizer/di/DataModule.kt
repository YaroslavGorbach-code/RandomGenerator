package com.app.yaroslavgorbach.randomizer.di

import android.content.Context
import androidx.room.Room
import com.app.yaroslavgorbach.randomizer.data.local.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java, "list.db"
        ).build()
    }
}

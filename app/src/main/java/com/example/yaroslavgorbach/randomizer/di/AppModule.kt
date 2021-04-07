package com.example.yaroslavgorbach.randomizer.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.yaroslavgorbach.randomizer.list.Database.Dao
import com.example.yaroslavgorbach.randomizer.list.Database.Database
import com.example.yaroslavgorbach.randomizer.list.Database.ListItemEntity
import com.example.yaroslavgorbach.randomizer.sounds.SoundManager
import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferences
import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferencesStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppBinds::class])
class AppModule {
    @Provides
    fun provideSoundManager(context: Context, soundPreferences: SoundPreferences): SoundManager {
       return SoundManager(context, soundPreferences)
    }
}

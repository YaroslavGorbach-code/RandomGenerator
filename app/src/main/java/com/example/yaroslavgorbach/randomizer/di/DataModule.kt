package com.example.yaroslavgorbach.randomizer.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.yaroslavgorbach.randomizer.data.database.Database
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
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val cv = ContentValues()
                    cv.put("text", "item 1")
                    cv.put("title", "TestList_1")
                    db.insert("ListItemEntity", SQLiteDatabase.CONFLICT_REPLACE, cv)
                    cv.put("text", "item 2")
                    cv.put("title", "TestList_1")
                    db.insert("ListItemEntity", SQLiteDatabase.CONFLICT_REPLACE, cv)
                    cv.put("text", "item 3")
                    cv.put("title", "TestList_1")
                    db.insert("ListItemEntity", SQLiteDatabase.CONFLICT_REPLACE, cv)
                }
            }).build()
    }
}

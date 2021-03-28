package com.example.yaroslavgorbach.randomizer.list.Database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

@androidx.room.Database(entities = [ListItemEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}
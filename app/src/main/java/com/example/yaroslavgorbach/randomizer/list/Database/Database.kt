package com.example.yaroslavgorbach.randomizer.list.Database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

@androidx.room.Database(entities = [ListItemEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        private var sInstance: Database? = null

        @Synchronized
        fun getInstance(context: Context): Database {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java, "list.db"
                )
                    .allowMainThreadQueries()
                    .addCallback(rdc)
                    .build()
            }
            return sInstance as Database
        }

        private val rdc: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                Thread {
                    val dao: Dao = sInstance!!.dao()
                    dao.insert(
                        ListItemEntity(null, "item 1", "TestList_1"),
                        ListItemEntity(null, "item 2", "TestList_1"),
                        ListItemEntity(null, "item 3", "TestList_1"),
                        ListItemEntity(null, "item 1", "TestList_2"),
                        ListItemEntity(null, "item 2", "TestList_2"),
                        ListItemEntity(null, "item 3", "TestList_2"),
                        ListItemEntity(null, "item 1", "TestList_3"),
                        ListItemEntity(null, "item 2", "TestList_3"),
                        ListItemEntity(null, "item 3", "TestList_3"))
                }.start()
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                // do something every time database is open
            }
        }

    }

}
package com.example.yaroslavgorbach.randomizer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity
data class ListItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    var text: String?,
    var title: String)

@androidx.room.Database(entities = [ListItemEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}

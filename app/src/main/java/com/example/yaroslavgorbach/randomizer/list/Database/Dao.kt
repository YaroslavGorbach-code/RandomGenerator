package com.example.yaroslavgorbach.randomizer.list.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    fun insert(vararg item: ListItemEntity)

    @Delete
    fun delete(item: ListItemEntity)

    @Update
    fun update(user: ListItemEntity)

    @Query("SELECT * FROM ListItemEntity WHERE title LIKE :title")
    fun getItemsByTitle(title: String):LiveData<List<ListItemEntity>>
}
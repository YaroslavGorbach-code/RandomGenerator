package com.example.yaroslavgorbach.randomizer.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    suspend fun insert(vararg item: ListItemEntity)

    @Delete
    suspend fun delete(item: ListItemEntity)

    @Update
    suspend fun update(user: ListItemEntity)

    @Query("SELECT DISTINCT title FROM ListItemEntity ORDER BY id DESC")
    fun getTitles():LiveData<List<String>>

    @Query("SELECT text FROM ListItemEntity WHERE title LIKE :title")
    suspend fun getItemsByTitle(title: String): List<String>

    @Query("SELECT * FROM ListItemEntity WHERE text LIKE :text LIMIT 1")
    suspend fun getItemByText(text: String): ListItemEntity

    @Query("UPDATE ListItemEntity SET title = :newTitle WHERE title == :oldTitle")
    suspend fun changeTitle(oldTitle: String, newTitle: String)

    @Query("DELETE FROM ListItemEntity WHERE title == :title")
    suspend fun deleteListByTitle(title: String)
}
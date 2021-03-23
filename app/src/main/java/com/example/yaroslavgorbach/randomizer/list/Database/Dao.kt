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

    @Query("SELECT DISTINCT title FROM ListItemEntity ORDER BY id DESC")
    fun getTitles():LiveData<List<String>>

    @Query("SELECT text FROM ListItemEntity WHERE title LIKE :title")
    fun getItemsByTitle(title: String): MutableList<String>

    @Query("SELECT * FROM ListItemEntity WHERE text LIKE :text LIMIT 1")
    fun getItemByText(text: String): ListItemEntity

    @Query("UPDATE ListItemEntity SET title = :newTitle WHERE title == :oldTitle")
    fun changeTitle(oldTitle: String, newTitle: String)

    @Query("DELETE FROM ListItemEntity WHERE title == :title")
    fun deleteListByTitle(title: String)
}
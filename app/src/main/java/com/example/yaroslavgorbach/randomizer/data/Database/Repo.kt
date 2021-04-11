package com.example.yaroslavgorbach.randomizer.data.Database

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(database: Database) {
    private val dao: Dao = database.dao()

    fun getTitles():LiveData<List<String>>{
        return dao.getTitles()
    }

    suspend fun addItem(listItemEntity: ListItemEntity){
        dao.insert(listItemEntity)
    }

    suspend fun getItemsByTitle(title: String): MutableList<String> {
        return dao.getItemsByTitle(title)
    }

    suspend fun changeTitle(oldTitle: String, newTitle: String) {
        dao.changeTitle(oldTitle, newTitle)
    }

    suspend fun deleteItemsByTitle(title: String) {
        dao.deleteListByTitle(title)
    }

    suspend  fun deleteItem(item: ListItemEntity) {
        dao.delete(item)
    }

    suspend fun getItemByText(it: String): ListItemEntity {
        return dao.getItemByText(it)
    }
}
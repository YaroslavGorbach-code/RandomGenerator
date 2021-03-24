package com.example.yaroslavgorbach.randomizer.list.Database

import android.content.Context
import androidx.lifecycle.LiveData
import javax.inject.Inject

class Repo @Inject constructor(database: Database) {
    private val dao: Dao = database.dao()

    fun getTitles():LiveData<List<String>>{
        return dao.getTitles()
    }

    fun addItem(listItemEntity: ListItemEntity){
        dao.insert(listItemEntity)
    }

    fun getItemsByTitle(title: String): MutableList<String> {
        return dao.getItemsByTitle(title)
    }

    fun changeTitle(oldTitle: String, newTitle: String) {
        dao.changeTitle(oldTitle, newTitle)
    }

    fun deleteItemsByTitle(title: String) {
        dao.deleteListByTitle(title)
    }

    fun deleteItem(item: ListItemEntity) {
        dao.delete(item)
    }

    fun getItemByText(it: String): ListItemEntity {
        return dao.getItemByText(it)
    }
}
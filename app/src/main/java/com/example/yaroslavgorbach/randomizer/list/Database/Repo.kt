package com.example.yaroslavgorbach.randomizer.list.Database

import android.content.Context
import androidx.lifecycle.LiveData

class Repo(context: Context) {
    private val database: Database = Database.getInstance(context)
    private val dao: Dao = database.dao()

    fun getTitles():LiveData<List<String>>{
        return dao.getTitles()
    }

    fun addItem(listItemEntity: ListItemEntity){
        dao.insert(listItemEntity)
    }
}
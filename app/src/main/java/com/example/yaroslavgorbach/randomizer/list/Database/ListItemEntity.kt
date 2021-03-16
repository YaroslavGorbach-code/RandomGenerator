package com.example.yaroslavgorbach.randomizer.list.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListItemEntity(@PrimaryKey(autoGenerate = true) val id: Long?, var text: String?, var title: String)

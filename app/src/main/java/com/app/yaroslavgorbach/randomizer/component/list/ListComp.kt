package com.app.yaroslavgorbach.randomizer.component.list

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred

interface ListComp {
    fun getSoundIsAllow(): LiveData<Boolean>
    fun disallowSound()
    fun allowSound()
    val words: Deferred<List<String>>
}
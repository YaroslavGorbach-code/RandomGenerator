package com.example.yaroslavgorbach.randomizer.component.matches

import androidx.lifecycle.LiveData

interface Matches {
    fun getSoundIsAllow(): LiveData<Boolean>
    fun disallowSound()
    fun allowSound()
}
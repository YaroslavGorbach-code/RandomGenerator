package com.example.yaroslavgorbach.randomizer.component.dice

import androidx.lifecycle.LiveData

interface Dice {
    fun getSoundIsAllow(): LiveData<Boolean>
    fun disallowSound()
    fun allowSound()
}
package com.example.yaroslavgorbach.randomizer.component.number

import androidx.lifecycle.LiveData

interface NumberCom {
    val value: LiveData<String>
    fun getSoundIsAllow(): LiveData<Boolean>
    fun disallowSound()
    fun allowSound()
    fun onGenerateValue()
}
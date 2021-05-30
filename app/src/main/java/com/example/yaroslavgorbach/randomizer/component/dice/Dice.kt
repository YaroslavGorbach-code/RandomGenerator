package com.example.yaroslavgorbach.randomizer.component.dice

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface Dice {
    val sum: LiveData<Int>
    fun animate(dice: DiceImp.DiceModel)
    fun animateButton(view: View)
    fun animateAll()
    fun onDiceInflated(dice: DiceImp.DiceModel)
    fun getSoundIsAllow(): LiveData<Boolean>
    fun disallowSound()
    fun allowSound()
}
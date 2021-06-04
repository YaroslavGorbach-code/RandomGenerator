package com.app.yaroslavgorbach.randomizer.component.dice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.yaroslavgorbach.randomizer.data.local.Repo

class DiceImp(private val repo: Repo) : Dice {

    private val diceSound = MutableLiveData(repo.getDiceSoundIsAllow())

    override fun getSoundIsAllow(): LiveData<Boolean> = diceSound

    override fun disallowSound() {
        repo.setDiceSoundIsAllow(false)
        diceSound.value = false
    }

    override fun allowSound() {
        repo.setDiceSoundIsAllow(true)
        diceSound.value = true
    }


}


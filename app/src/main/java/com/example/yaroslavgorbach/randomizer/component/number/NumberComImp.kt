package com.example.yaroslavgorbach.randomizer.component.number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.data.local.Repo

class NumberComImp(
    private val repo: Repo,
    private val max: Long,
    private val min: Long,
    private val numberResults: Long
) : NumberCom {
    private val sound = MutableLiveData(repo.getNumberSoundIsAllow())
    private val mValue = MutableLiveData<String>()
    override val value: LiveData<String>
        get() = mValue

    override fun getSoundIsAllow(): LiveData<Boolean> {
        return sound
    }

     override  fun disallowSound() {
        repo.setNumberSoundIsAllow(false)
        sound.value = false
    }

    override fun allowSound() {
        repo.setNumberSoundIsAllow(true)
        sound.value = true
    }

    override fun onGenerateValue() {
        var value: String = (min..max).random().toString()
        for (i in 2..numberResults) {
            value += ", ${(min..max).random()}"
        }
        mValue.value = value
    }
}
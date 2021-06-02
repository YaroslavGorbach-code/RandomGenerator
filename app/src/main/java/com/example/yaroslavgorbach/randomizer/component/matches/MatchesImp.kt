package com.example.yaroslavgorbach.randomizer.component.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.data.local.Repo

class MatchesImp(private val repo: Repo) : Matches {
    private val sound = MutableLiveData(repo.getMatchesSoundIsAllow())

    override fun getSoundIsAllow(): LiveData<Boolean> {
        return sound
    }

    override fun disallowSound() {
        repo.setMatchesSoundIsAllow(false)
        sound.value = false
    }

    override fun allowSound() {
        repo.setMatchesSoundIsAllow(true)
        sound.value = true
    }
}
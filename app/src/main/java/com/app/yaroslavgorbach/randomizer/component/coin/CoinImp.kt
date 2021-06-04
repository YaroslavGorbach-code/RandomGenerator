package com.app.yaroslavgorbach.randomizer.component.coin

import androidx.lifecycle.MutableLiveData
import com.app.yaroslavgorbach.randomizer.data.local.Repo
import com.app.yaroslavgorbach.randomizer.feature.SoundManager

class CoinImp(private val soundManager: SoundManager, private val repo: Repo) : Coin {
    private val coinSound = MutableLiveData(repo.getCoinSoundIsAllow())

    override fun getSoundIsAllow() = coinSound
    override fun disallowSound() {
        repo.setCoinSoundIsAllow(false)
        coinSound.value = false
    }

    override fun allowSound() {
        repo.setCoinSoundIsAllow(true)
        coinSound.value = true
    }

}
package com.example.yaroslavgorbach.randomizer.component.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class ListCompImp(
    private val repo: Repo,
    private val title: String,
    private val scope: CoroutineScope
) : ListComp {
    private val diceSound = MutableLiveData(repo.getListSoundIsAllow())

    override fun getSoundIsAllow(): LiveData<Boolean> {
        return diceSound
    }

    override fun disallowSound() {
        repo.setListSoundIsAllow(false)
        diceSound.value = false
    }

    override fun allowSound() {
        repo.setListSoundIsAllow(true)
        diceSound.value = true
    }

    override val words: Deferred<List<String>>
        get() = scope.async {
            repo.getItemsByTitle(title)
        }

}


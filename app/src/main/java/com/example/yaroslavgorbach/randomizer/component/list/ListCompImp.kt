package com.example.yaroslavgorbach.randomizer.component.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.data.local.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class ListCompImp(
    private val repo: Repo,
    private val title: String,
    private val scope: CoroutineScope
) : ListComp {
    private val sound = MutableLiveData(repo.getListSoundIsAllow())

    override fun getSoundIsAllow(): LiveData<Boolean> {
        return sound
    }

    override fun disallowSound() {
        repo.setListSoundIsAllow(false)
        sound.value = false
    }

    override fun allowSound() {
        repo.setListSoundIsAllow(true)
        sound.value = true
    }

    override val words: Deferred<List<String>>
        get() = scope.async {
            repo.getItemsByTitle(title)
        }

}


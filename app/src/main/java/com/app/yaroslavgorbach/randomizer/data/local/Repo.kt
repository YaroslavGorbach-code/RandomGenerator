package com.app.yaroslavgorbach.randomizer.data.local

import androidx.lifecycle.LiveData
import com.app.yaroslavgorbach.randomizer.data.local.commonpref.CommonPref
import com.app.yaroslavgorbach.randomizer.data.local.soundpref.SoundPrefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    database: Database,
    private val soundPrefs: SoundPrefs,
    private val commonPref: CommonPref
) {
    private val mListDao: Dao = database.dao()

    fun getTitles(): LiveData<List<String>> {
        return mListDao.getTitles()
    }

    suspend fun addItem(listItemEntity: ListItemEntity) {
        mListDao.insert(listItemEntity)
    }

    suspend fun getItemsByTitle(title: String): List<String> {
        return mListDao.getItemsByTitle(title)
    }

    suspend fun changeTitle(oldTitle: String, newTitle: String) {
        mListDao.changeTitle(oldTitle, newTitle)
    }

    suspend fun deleteItemsByTitle(title: String) {
        mListDao.deleteListByTitle(title)
    }

    suspend fun deleteItem(item: ListItemEntity) {
        mListDao.delete(item)
    }

    suspend fun getItemByText(it: String): ListItemEntity {
        return mListDao.getItemByText(it)
    }

    fun getCoinSoundIsAllow(): Boolean {
        return soundPrefs.getState(SoundPrefs.COIN_SOUND_KEY)
    }

    fun setCoinSoundIsAllow(b: Boolean) {
        if (b) {
            soundPrefs.allow(SoundPrefs.COIN_SOUND_KEY)
        } else {
            soundPrefs.disallow(SoundPrefs.COIN_SOUND_KEY)
        }
    }

    fun getDiceSoundIsAllow(): Boolean {
        return soundPrefs.getState(SoundPrefs.DICE_SOUND_KEY)
    }

    fun setDiceSoundIsAllow(b: Boolean) {
        if (b) {
            soundPrefs.allow(SoundPrefs.DICE_SOUND_KEY)
        } else {
            soundPrefs.disallow(SoundPrefs.DICE_SOUND_KEY)
        }
    }

    fun getListSoundIsAllow(): Boolean {
        return soundPrefs.getState(SoundPrefs.LIST_SOUND_KEY)
    }

    fun setListSoundIsAllow(b: Boolean) {
        if (b) {
            soundPrefs.allow(SoundPrefs.LIST_SOUND_KEY)
        } else {
            soundPrefs.disallow(SoundPrefs.LIST_SOUND_KEY)
        }
    }

    fun setMatchesSoundIsAllow(b: Boolean) {
        if (b) {
            soundPrefs.allow(SoundPrefs.MATCHES_SOUND_KEY)
        } else {
            soundPrefs.disallow(SoundPrefs.MATCHES_SOUND_KEY)
        }
    }

    fun getMatchesSoundIsAllow(): Boolean {
        return soundPrefs.getState(SoundPrefs.MATCHES_SOUND_KEY)
    }

    fun setNumberSoundIsAllow(b: Boolean) {
        if (b) {
            soundPrefs.allow(SoundPrefs.NUMBER_SOUND_KEY)
        } else {
            soundPrefs.disallow(SoundPrefs.NUMBER_SOUND_KEY)
        }
    }

    fun getNumberSoundIsAllow(): Boolean {
        return soundPrefs.getState(SoundPrefs.NUMBER_SOUND_KEY)
    }

    fun getIsFirsOpen(): Boolean {
        return commonPref.getIsFirsOpen()
    }

    fun setIsFirsOpenFalse() {
        return commonPref.setIsFirsOpenFalse()
    }
}
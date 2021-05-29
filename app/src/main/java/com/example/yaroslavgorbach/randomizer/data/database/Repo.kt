package com.example.yaroslavgorbach.randomizer.data.database

import androidx.lifecycle.LiveData
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPrefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(database: Database, soundPrefs: SoundPrefs) {
    private val mListDao: Dao = database.dao()
    private val mSoundPrefs: SoundPrefs = soundPrefs;

    fun getTitles():LiveData<List<String>>{
        return mListDao.getTitles()
    }

    suspend fun addItem(listItemEntity: ListItemEntity){
        mListDao.insert(listItemEntity)
    }

    suspend fun getItemsByTitle(title: String): MutableList<String> {
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
        return mSoundPrefs.getState(SoundPrefs.COIN_SOUND_KEY)
    }

    fun setCoinSoundIsAllow(b: Boolean) {
        if (b){
            mSoundPrefs.allow(SoundPrefs.COIN_SOUND_KEY)
        }else{
            mSoundPrefs.disallow(SoundPrefs.COIN_SOUND_KEY)
        }
    }
}
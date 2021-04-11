package com.example.yaroslavgorbach.randomizer.data.SoundPref

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class SoundPreferencesImp @Inject constructor(context: Context): SoundPreferences {
    private val mSharedPreferences = context.getSharedPreferences("SOUND_PREF_KEY", Context.MODE_PRIVATE)


    override fun allowSound(key: String) {
        mSharedPreferences.edit {
            putBoolean(key, true)
        }
    }

    override fun disallowSound(key: String) {
        mSharedPreferences.edit {
            putBoolean(key, false)
        }
    }

    override fun getState(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, true)
    }
}
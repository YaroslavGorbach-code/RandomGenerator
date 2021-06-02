package com.example.yaroslavgorbach.randomizer.data.local.soundpref

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class SoundPrefsImp @Inject constructor(context: Context): SoundPrefs {
    private val mSharedPreferences
    = context.getSharedPreferences("com.example.yaroslavgorbach.randomizer", Context.MODE_PRIVATE)


    override fun allow(key: String) {
        mSharedPreferences.edit {
            putBoolean(key, true)
        }
    }

    override fun disallow(key: String) {
        mSharedPreferences.edit {
            putBoolean(key, false)
        }
    }

    override fun getState(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, true)
    }
}
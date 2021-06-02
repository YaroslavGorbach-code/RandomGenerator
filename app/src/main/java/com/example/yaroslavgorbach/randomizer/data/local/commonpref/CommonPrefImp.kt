package com.example.yaroslavgorbach.randomizer.data.local.commonpref

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class CommonPrefImp @Inject constructor(context: Context): CommonPref {
    private val mSharedPreferences
            = context.getSharedPreferences("com.example.yaroslavgorbach.randomizer", Context.MODE_PRIVATE)

    override fun getIsFirsOpen(): Boolean {
       return mSharedPreferences.getBoolean("firs_open", true)
    }

    override fun setIsFirsOpenFalse() {
        mSharedPreferences.edit {
            putBoolean("firs_open", false)
        }
    }
}
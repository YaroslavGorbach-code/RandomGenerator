package com.example.yaroslavgorbach.randomizer.themes.themeStorage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.themes.themeStorage.ThemeStorage

class AppThemeStorage(context: Context): ThemeStorage {
    private val mContext: Context = context
    private val mSharedPreferences = context.getSharedPreferences(
        "THEME_PREF_KEY",
        Context.MODE_PRIVATE
    )


    override fun changeTheme(color: Int) {
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_standart)
            }
        }
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_red)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_red)
            }
        }
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_orange)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_orange)
            }
        }
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_blue)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_blue)
            }
        }
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_yellow)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_yellow)
            }
        }
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_purple)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_purple)
            }
        }
        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_blue_l)) {
            mSharedPreferences.edit {
                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_blue_l)
            }
        }
    }

    override fun getTheme(): Int {
       return mSharedPreferences.getInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_standart)
    }

    override fun getNightMode(): Boolean {
        return mSharedPreferences.getBoolean("NIGHT_MODE_VALUE_KEY", false)
    }

    override fun changeNightMode(boolean: Boolean) {
        mSharedPreferences.edit {
            putBoolean("NIGHT_MODE_VALUE_KEY", boolean)
        }
    }

}
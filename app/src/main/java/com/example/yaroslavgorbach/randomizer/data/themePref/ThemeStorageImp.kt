package com.example.yaroslavgorbach.randomizer.data.themePref

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.yaroslavgorbach.randomizer.R

class ThemeStorageImp(context: Context): ThemeStorage {
    private val mContext: Context = context
    private val mSharedPreferences = context.getSharedPreferences(
        "com.example.yaroslavgorbach.randomizer",
        Context.MODE_PRIVATE
    )

    override fun changeTheme(color: Int) {
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_standart)
//            }
//        }
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_red)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_red)
//            }
//        }
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_orange)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_orange)
//            }
//        }
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_blue)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_blue)
//            }
//        }
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_yellow)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_yellow)
//            }
//        }
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_purple)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_purple)
//            }
//        }
//        if (color == ContextCompat.getColor(mContext, R.color.colorAccent_blue_l)) {
//            mSharedPreferences.edit {
//                putInt("THEME_VALUE_KEY", R.style.Theme_Randomizer_blue_l)
//            }
//        }
    }

    override fun getTheme(): Int {
       return mSharedPreferences.getInt("theme", R.style.AppTheme)
    }

}
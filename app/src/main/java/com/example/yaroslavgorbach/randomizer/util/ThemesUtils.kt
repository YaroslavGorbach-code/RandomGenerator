package com.example.yaroslavgorbach.randomizer.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import com.example.yaroslavgorbach.randomizer.data.themePref.ThemeStorageImp
import com.example.yaroslavgorbach.randomizer.R

class ThemesUtils {

    companion object{
        fun getCurrentAccentColor(context: Context): Int {
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
            return typedValue.data
        }

        fun animateThemeChange(activity: Activity) {
            activity.finish()
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            activity.startActivity(Intent(activity, activity.javaClass))
        }
    }
}
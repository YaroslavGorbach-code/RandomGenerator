package com.example.yaroslavgorbach.randomizer.util

import android.content.Context
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.example.yaroslavgorbach.randomizer.R
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.toLong(): Long =
    text.toString().toLong()

fun TextInputEditText.toInt(): Int =
    text.toString().toInt()

fun Toolbar.setIconMusicOn() {
    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_music_note, null)
}

fun Toolbar.setIconMusicOff() {
    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_music_off, null)
}

fun Context.getScreenHeight() = resources.displayMetrics.heightPixels

fun Window.showKeyBoard() = setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)


package com.example.yaroslavgorbach.randomizer

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat

fun Toolbar.setIconMusicOn(){
    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_music_note, null)
}

fun Toolbar.setIconMusicOff(){
    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_music_off, null)
}

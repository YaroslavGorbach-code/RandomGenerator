package com.example.yaroslavgorbach.randomizer.util

import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.example.yaroslavgorbach.randomizer.R

fun Toolbar.setIconMusicOn(){
    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_music_note, null)
}

fun Toolbar.setIconMusicOff(){
    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_music_off, null)
}

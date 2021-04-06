package com.example.yaroslavgorbach.randomizer.themes.colorPicker

import android.content.Context

interface ColorPicker {
    fun setColor(color: Int)
    fun getColor(context: Context):Int
}
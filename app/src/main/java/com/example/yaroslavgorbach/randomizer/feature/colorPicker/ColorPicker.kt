package com.example.yaroslavgorbach.randomizer.feature.colorPicker

import android.content.Context

interface ColorPicker {
    fun setColor(color: Int)
    fun getColor(context: Context):Int
}
package com.example.yaroslavgorbach.randomizer.component.colorPicker

import android.content.Context

interface ColorPicker {
    fun setColor(color: Int)
    fun getColor(context: Context):Int
}
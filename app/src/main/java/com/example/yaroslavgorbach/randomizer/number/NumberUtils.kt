package com.example.yaroslavgorbach.randomizer.number

import android.content.Context

class NumberUtils {
    companion object{
         fun getScreenHeight(context: Context): Int {
             val display  = context.resources.displayMetrics
             return display.heightPixels
         }
    }
}